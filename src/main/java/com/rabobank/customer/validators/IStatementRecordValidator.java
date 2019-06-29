package com.rabobank.customer.validators;

import com.rabobank.customer.exception.ValidationException;
import com.rabobank.customer.model.Record;
import com.rabobank.customer.model.ValidationResult;

import java.util.List;

public interface IStatementRecordValidator {

    List<ValidationResult> validate(List<Record> records) throws ValidationException;
}
