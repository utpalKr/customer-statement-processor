package com.rabobank.customer.validators;

import com.rabobank.customer.exception.ValidationException;
import com.rabobank.customer.model.Record;
import com.rabobank.customer.model.ValidationResult;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UniqueTransactionReferenceValidatorTest {

    @InjectMocks
    UniqueTransactionReferenceValidator theValidator;

    List<ValidationResult> validationResults = new ArrayList<>();

    List<Record> records = new ArrayList<>();

    @Test
    public void shouldValidate() throws ValidationException {

        Record record = new Record();
        record.setAccountNumber("12345");
        record.setDescription("Test account");
        record.setReference(12345);
        record.setMutation(1000.f);
        record.setStartBalance(0.000f);
        record.setEndBalance(0.000f);
        records.add(record);

        Record record1 = new Record();
        record1.setAccountNumber("12345");
        record1.setDescription("Test account");
        record1.setReference(12345);
        record1.setMutation(1001.f);
        record1.setStartBalance(0.001f);
        record1.setEndBalance(0.001f);
        records.add(record1);
        validationResults = theValidator.validate(records);

        Assert.assertNotNull(validationResults);
        Assert.assertEquals(2, validationResults.size());
        Assert.assertEquals("Unique transaction Validation Failed", validationResults.get(0).getDescription());
        Assert.assertEquals("Unique transaction Validation Failed", validationResults.get(1).getDescription());

    }

    @Test
    public void shouldValidateWithNoFailures() throws ValidationException {

        //44.85,-22.24,22.61
        Record record = new Record();
        record.setAccountNumber("NL91RABO0315273637");
        record.setDescription("Clothes for Rik King");
        record.setReference(187997);
        record.setMutation(-22.24);
        record.setStartBalance(44.85);
        record.setEndBalance(22.61);
        records.add(record);

        //45.59,+48.18,93.77
        Record record1 = new Record();
        record1.setAccountNumber("NL56RABO0149876948");
        record1.setDescription("Candy for Peter de Vries");
        record1.setReference(187999);
        record1.setMutation(-429);
        record1.setStartBalance(5429);
        record1.setEndBalance(5000);
        records.add(record1);

        validationResults = theValidator.validate(records);

        Assert.assertNotNull(validationResults);
        Assert.assertEquals(0, validationResults.size());
    }

    @Test(expected = ValidationException.class)
    public void shouldValidateWithZeroRecord() throws ValidationException {
        validationResults = theValidator.validate(records);

    }
}
