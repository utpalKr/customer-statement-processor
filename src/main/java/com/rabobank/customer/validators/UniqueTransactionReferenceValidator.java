package com.rabobank.customer.validators;

import com.rabobank.customer.exception.ValidationException;
import com.rabobank.customer.model.Record;
import com.rabobank.customer.model.ValidationResult;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * Validator for Unique Transaction reference.
 */
public class UniqueTransactionReferenceValidator implements IStatementRecordValidator {

    /**
     * This method validates the records for unique transactions.
     * @param records - the list of {@link Record} records
     * @return list of {@link ValidationResult} validation results
     * @throws ValidationException
     */
    @Override
    public List<ValidationResult> validate(List<Record> records) throws ValidationException {

        if (null == records || records.isEmpty()) {
            throw new ValidationException("No records to validate. Check logs for more details");
        }
        final List<ValidationResult> failedRecords =  records
                .stream()
                .collect(groupingBy(Record::getReference))
                .values()
                .stream()
                .filter(refCount -> refCount.size() > 1)
                .flatMap(Collection::stream)
                .collect(Collectors.mapping(p -> new ValidationResult(p.getReference(),
                        "Unique transaction Validation Failed"),Collectors.toList()));

        return failedRecords;
    }
}
