package com.example.test_kuber.exception

import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException) = ErrorResponse(
        status = HttpStatus.BAD_REQUEST.value(),
        errorCode = 400,
        errorMessage = e.message ?: "Bad Request"
    )

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFountException::class)
    fun handleHttpMessageNotReadableException(e: UserNotFountException) = ErrorResponse(
        status = HttpStatus.NOT_FOUND.value(),
        errorCode = 404,
        errorMessage = e.message ?: "Bad Request"
    )

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RoleNotFountException::class)
    fun handleHttpMessageNotReadableException(e: RoleNotFountException) = ErrorResponse(
        status = HttpStatus.NOT_FOUND.value(),
        errorCode = 404,
        errorMessage = e.message ?: "Bad Request"
    )

    companion object {
        private val LOG = KotlinLogging.logger {}
    }
}