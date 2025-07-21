package uz.kruz.util;

import uz.kruz.util.exceptions.ServiceException;

import java.math.BigDecimal;

public class Validator {

    public static void validateId(Integer id) {
        if (id == null || id <= 0) {
            throw new ServiceException("Invalid ID: must be a positive integer");
        }
    }

    public static void validateString(String field, String fieldName) {
        if (field == null || field.trim().isEmpty() || !field.trim().equals(field)) {
            throw new ServiceException(String.format("%s must not be null or empty", fieldName));
        }
    }
    public static void validateBigDecimal(BigDecimal  value, String fieldName) {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ServiceException(String.format("%s must not be null or empty", fieldName));
        }
    }
    public static void validateInteger(Integer value, String fieldName) {
        if (value == null || value <= 0) {
            throw new ServiceException(String.format("%s must not be null or empty", fieldName));
        }
    }

}
