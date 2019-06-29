package com.rabobank.customer.service;

import com.rabobank.customer.model.ValidationResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.bind.UnmarshalException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;

@RunWith(MockitoJUnitRunner.class)
public class XMLFileProcessorServiceTest {

    @InjectMocks
    XMLFileProcessorService theService;

    MultipartFile multipartFile;

    @Mock
    ValidationService validationService;

    List<ValidationResult> validationResults = new ArrayList<>();

    @Before
    public void init() throws IOException {

        multipartFile = new MockMultipartFile("records.xml",
                "records.xml", "application/xml",
                new FileInputStream(new File("src/test/resources/records.xml")));

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

    @Test(expected = UnmarshalException.class)
    public void shouldProcessWrongFormatStatement() throws Exception {

        multipartFile = new MockMultipartFile("records_wrong_format.xml",
                "records_wrong_format.xml", "application/xml",
                new FileInputStream(new File("src/test/resources/records_wrong_format.xml")));

        theService.processStatement(multipartFile);
    }
}
