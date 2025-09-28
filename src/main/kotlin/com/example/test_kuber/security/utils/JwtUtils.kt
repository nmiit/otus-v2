package com.example.test_kuber.security.utils

import com.example.test_kuber.service.CasService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.Base64
import java.util.Date

@Profile("!security_disabled")
@Component
class JwtUtils(
    private val casService: CasService,
    @Value("\${jwt.expiration}") private val jwtExpiration: Long
) {
    private val privateKey: PrivateKey by lazy { loadPrivateKey("keys/private-key-pkcs8.pem") }

    @Transactional
    fun generateToken(username: String): String {
        val cas = casService.findByUsername(username)
            ?: throw UsernameNotFoundException("User $username not found")

        return Jwts.builder()
            .setSubject(username)
            .setIssuer("myapp-issuer")
            .claim("casId", cas.id)                  // ← ID из БД
            .claim("roles", cas.roles.map { it.roleName })
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtExpiration))
            .signWith(privateKey, SignatureAlgorithm.RS256)
            .compact()
    }

    private fun loadPrivateKey(resourcePath: String): PrivateKey {
        val content = javaClass.classLoader.getResourceAsStream(resourcePath)
            ?.bufferedReader()
            ?.readText()
            ?: throw IllegalArgumentException("Private key file not found: $resourcePath")

        val pemContent = content
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replace("-----END PRIVATE KEY-----", "")
            .replace("\\s".toRegex(), "")

        val decoded = Base64.getDecoder().decode(pemContent)
        val keySpec = PKCS8EncodedKeySpec(decoded)
        return KeyFactory.getInstance("RSA").generatePrivate(keySpec)
    }

    fun convertKeyToRSAPublicKey(jwtKeyString: String): PublicKey {
        val cleaned = jwtKeyString
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace("-----END PUBLIC KEY-----", "")
            .replace(Regex("\\s+"), "") // удаляем ВСЕ пробельные символы

        val decodedBytes = Base64.getDecoder().decode(cleaned)

        val keySpec = X509EncodedKeySpec(decodedBytes)
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePublic(keySpec)
    }
}