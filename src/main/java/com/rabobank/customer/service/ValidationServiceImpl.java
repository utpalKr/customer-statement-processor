package com.rabobank.customer.service;

import com.rabobank.customer.exception.ValidationException;
import com.rabobank.customer.model.Record;
import com.rabobank.customer.model.ValidationResult;
import com.rabobank.customer.validators.EndBalanceValidator;
import com.rabobank.customer.validators.IStatementRecordValidator;
import com.rabobank.customer.validators.UniqueTransactionReferenceValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Validation service implementation class.
 */
@Service
public class ValidationServiceImpl implements ValidationService {

    private static final Logger LOG = LoggerFactory.getLogger(ValidationServiceImpl.class);

    @Override
    public List<ValidationResult> validate(final List<Record> recordList) throws ValidationException {

        final List<IStatementRecordValidator> validators = new ArrayList<>();
        validators.add(new EndBalanceValidator());
        validators.add(new UniqueTransactionReferenceValidator());
        List<ValidationResult> failedRecords = new ArrayList<>();
        for (IStatementRecordValidator validator : validators) {
                failedRecords.addAll(validator.validate(recordList));

        }
        LOG.debug(failedRecords.size() + " failed records in the file.");
        return failedRecords;
    }
}
