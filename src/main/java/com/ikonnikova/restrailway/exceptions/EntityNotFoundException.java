package com.ikonnikova.restrailway.exceptions;

/**
 * Исключение, выбрасываемое при отсутствии сущности.
 */
public class EntityNotFoundException extends RuntimeException {

    /**
     * Конструктор с параметром.
     * @param message сообщение об ошибке
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}
