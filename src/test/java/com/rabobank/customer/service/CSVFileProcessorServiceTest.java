package com.rabobank.customer.service;

import com.rabobank.customer.model.ValidationResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;

@RunWith(MockitoJUnitRunner.class)
public class CSVFileProcessorServiceTest {

    @InjectMocks
    CSVFileProcessorService theService;

    MultipartFile multipartFile;

    @Mock
    ValidationService validationService;

    List<ValidationResult> validationResults = new ArrayList<>();

    @Before
    public void init() throws IOException {

        multipartFile = new MockMultipartFile("records.csv",
                "records.csv", "text/csv",
                new FileInputStream(new File("src/test/resources/records.csv")));

        ValidationResult result = new ValidationResult(12345, "EndBalance validation failed");
        validationResults.add(result);
    }

    @Test
    public void shouldProcessStatement() throws Exception {

        Mockito.when(validationService.validate(anyList())).thenReturn(validationResults);

        List<ValidationResult> validationResultList = theService.processStatement(multipartFile);

        Assert.assertNotNull(validationResultList);
        Assert.assertEquals(1, validationResultList.size());
    }

    @Test
    public void shouldProcessWrongFormatStatement() throws Exception {

        multipartFile = new MockMultipartFile("records_wrong_format.csv",
                "records_wrong_format.csv", "text/csv",
                new FileInputStream(new File("src/test/resources/records_wrong_format.csv")));

        List<ValidationResult> validationResultList = theService.processStatement(multipartFile);
        Assert.assertNotNull(validationResultList);
        Assert.assertEquals(0, validationResultList.size());

    }
}
