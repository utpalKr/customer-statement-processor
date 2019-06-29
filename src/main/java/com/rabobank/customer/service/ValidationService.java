package com.rabobank.customer.service;

import com.rabobank.customer.exception.ValidationException;
import com.rabobank.customer.model.Record;
import com.rabobank.customer.model.ValidationResult;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Validation service interface.
 */
@Service
public interface ValidationService {

    /**
     * The main method which needs to be implemented.
     * @param recordList - the list of {@link Record} records.
     * @return a list of {@link ValidationResult} results
     * @throws ValidationException
     */
    List<ValidationResult> validate(List<Record> recordList) throws ValidationException;
}
