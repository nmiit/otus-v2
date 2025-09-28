package com.example.test_kuber.security

import com.example.test_kuber.security.manager.request_manager.CasAuthenticationManager
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean

class AuthenticationFilter(
    private val casAuthenticationManager: CasAuthenticationManager,
    private val mapper: ObjectMapper = ObjectMapper()
): GenericFilterBean() {

    private val publicPaths = listOf("/api/auth/login", "/api/auth/register", "/error")

    override fun doFilter(
        request: ServletRequest?, response: ServletResponse?, chain: FilterChain?
    ) {
        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse

        if (httpRequest.servletPath in publicPaths) {
            chain?.doFilter(httpRequest, httpResponse)
            return
        }

        try {
            httpRequest.getHeader(casAuthenticationManager.getAuthHeader())
                ?.let { casAuthenticationManager.authenticateByToken(it) }
                ?.also { SecurityContextHolder.getContext().authentication = it }
                ?.also { chain?.doFilter(httpRequest, httpResponse) }
                ?: throw AuthenticationServiceException(
                    "Token for header ${casAuthenticationManager.getAuthHeader()} is required for ${httpRequest.requestURI}"
                )
        } catch (internalException: InternalAuthenticationServiceException) {
            val mes = "Internal authentication service exception"
            LOG.warn(mes, internalException)
            httpResponse.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            httpResponse.outputStream.write(mapper.writeValueAsBytes("$mes: ${internalException.message}"))
        } catch (authenticationException: AuthenticationException) {
            val mes = "Authentication service exception"
            LOG.warn(mes, authenticationException)
            httpResponse.status = HttpServletResponse.SC_UNAUTHORIZED
            httpResponse.outputStream.write(mapper.writeValueAsBytes("$mes: ${authenticationException.message}"))
        } catch (other: Exception) {
            val mes = "Unknown exception occured"
            LOG.warn("$mes, $other")
            other.printStackTrace()
            httpResponse.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            httpResponse.outputStream.write(mapper.writeValueAsBytes("$mes: ${other.message}"))
        }
    }

    companion object {
        private val LOG = KotlinLogging.logger {}
    }
}