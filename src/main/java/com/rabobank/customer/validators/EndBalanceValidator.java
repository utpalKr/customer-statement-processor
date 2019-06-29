package com.rabobank.customer.validators;

import com.rabobank.customer.exception.ValidationException;
import com.rabobank.customer.model.Record;
import com.rabobank.customer.model.ValidationResult;


import java.util.List;
import java.util.stream.Collectors;

/**
 * Validator to check EndBalance.
 */
public class EndBalanceValidator implements IStatementRecordValidator {

    /**
     * This method validates the records for end balance.
     *
     * @param records - the list of {@link Record} records
     * @return list of {@link ValidationResult} validation results
     * @throws ValidationException
     */
    @Override
    public List<ValidationResult> validate(List<Record> records) throws ValidationException {

        if (null == records || records.isEmpty()) {
            throw new ValidationException("No records to validate. Check logs for more details");
        }
        //Based on the precision
        final float threshHold = .0001f;
        /*compare the floating number based on the range. If difference is more than threshhold then
         it is considered as not equal*/
        List<ValidationResult> endBalanceValidatioFailedList = records.stream().filter(record -> {
            Double sum = Double.sum(record.getStartBalance(), record.getMutation());
            Double diff = Math.abs(sum - record.getEndBalance());
            return (diff > threshHold);
        }).collect(Collectors.mapping(p -> new ValidationResult(p.getReference(),
                "EndBalance Validation Failed"), Collectors.toList()));

        return endBalanceValidatioFailedList;
    }
}
