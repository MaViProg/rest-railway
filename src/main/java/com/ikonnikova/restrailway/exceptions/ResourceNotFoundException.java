package com.ikonnikova.restrailway.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String stationModel, String id, Long stationModelId) {
    }
}
