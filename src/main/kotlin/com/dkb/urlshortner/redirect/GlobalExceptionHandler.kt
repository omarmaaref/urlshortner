package com.dkb.urlshortner.exception

import com.dkb.urlshortner.redirect.UrlRedirectService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(UrlRedirectService.UrlNotFoundException::class)
    fun handleUrlNotFound(ex: UrlRedirectService.UrlNotFoundException): ResponseEntity<ErrorResponse> {
        val body = ErrorResponse(
            error = "Not Found",
            message = ex.message ?: "The provided code does not exist"
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body)
    }
}

data class ErrorResponse(
    val error: String,
    val message: String
)
