package com.edme.salespoint.exception;
//для ручной обработки исключений в сервисах
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
