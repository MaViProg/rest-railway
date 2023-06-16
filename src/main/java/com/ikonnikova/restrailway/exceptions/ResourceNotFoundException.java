package com.ikonnikova.restrailway.exceptions;

/**
 * Исключение, выбрасываемое при отсутствии ресурса.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Конструктор с параметром.
     *
     * @param message сообщение об ошибке
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Конструктор с параметрами.
     *
     * @param stationModel   модель станции
     * @param id             идентификатор
     * @param stationModelId идентификатор модели станции
     */
    public ResourceNotFoundException(String stationModel, String id, Long stationModelId) {
    }
}
