package com.tcs.exception;

import com.tcs.dto.ApiResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseDto> handleResourceNotFound(
            ResourceNotFoundException ex) {

        return new ResponseEntity<>(
                new ApiResponseDto(false, ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponseDto> handleBadRequest(
            BadRequestException ex) {

        return new ResponseEntity<>(
                new ApiResponseDto(false, ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ApiResponseDto> handleInsufficientBalance(
            InsufficientBalanceException ex) {

        return new ResponseEntity<>(
                new ApiResponseDto(false, ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponseDto> handleUnauthorized(
            UnauthorizedException ex) {

        return new ResponseEntity<>(
                new ApiResponseDto(false, ex.getMessage()),
                HttpStatus.UNAUTHORIZED
        );
    }

    /**
     * Generic exception handler
     * IMPORTANT: Swagger paths must be excluded
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        String path = request.getRequestURI();

        // 🔥 DO NOT INTERCEPT SWAGGER / OPENAPI
        if (path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-ui")) {

            // Let Spring / Swagger handle it
            throw new RuntimeException(ex);
        }

        // Log for debugging (recommended)
        ex.printStackTrace();

        return new ResponseEntity<>(
                new ApiResponseDto(false, "Internal server error"),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
