package com.rabobank.customer.resource;

import com.rabobank.customer.enumeration.ProcessorServiceType;
import com.rabobank.customer.model.StatementProcessResponse;
import com.rabobank.customer.service.CSVFileProcessorService;
import com.rabobank.customer.service.FileProcessorServiceFactory;
import com.rabobank.customer.service.XMLFileProcessorService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

@RunWith(MockitoJUnitRunner.class)
public class StatementControllerTest {

    @InjectMocks
    StatementController theController;

    @Mock
    FileProcessorServiceFactory factory;

    @Mock
    CSVFileProcessorService csvFileProcessorService;

    @Mock
    XMLFileProcessorService xmlFileProcessorService;

    @Before
    public void init() {
        Mockito.when(factory.getProcessorService(ProcessorServiceType.CSV_PROCESSOR_SERVICE)).thenReturn(csvFileProcessorService);
        Mockito.when(factory.getProcessorService(ProcessorServiceType.XML_PROCESSOR_SERVICE)).thenReturn(xmlFileProcessorService);
    }

    @Test
    public void shouldProcessXMLFile() throws Exception {

        MultipartFile multipartFile = new MockMultipartFile("records.xml",
                "records.xml", "application/xml",
                new FileInputStream(new File("src/test/resources/records.xml")));

        ResponseEntity<StatementProcessResponse> response =  theController.processFile(multipartFile);

        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
    }

    @Test
    public void shouldProcessCSVFile() throws Exception {

        MultipartFile multipartFile = new MockMultipartFile("records.csv",
                "records.csv", "text/csv",
                new FileInputStream(new File("src/test/resources/records.csv")));

        ResponseEntity<StatementProcessResponse> response =  theController.processFile(multipartFile);

        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldProcessWrongFormatFile() throws Exception {

        MultipartFile multipartFile = new MockMultipartFile("records.txt",
                "records.txt", "text",
                new FileInputStream(new File("src/test/resources/records.txt")));

        theController.processFile(multipartFile);
    }

    @Test
    public void shouldProcessEmptyCSVFile() throws Exception {

        MultipartFile multipartFile = new MockMultipartFile("records_empty.csv",
                "records_empty.csv", "text/csv",
                new FileInputStream(new File("src/test/resources/records_empty.csv")));

        ResponseEntity<StatementProcessResponse> reponse =  theController.processFile(multipartFile);

        Assert.assertNotNull(reponse);
        Assert.assertEquals(HttpStatus.NOT_FOUND.value(), reponse.getStatusCode().value());
    }

    @Test
    public void shouldProcessEmptyXMLFile() throws Exception {

        MultipartFile multipartFile = new MockMultipartFile("records_empty.xml",
                "records_empty.xml", "application/xml",
                new FileInputStream(new File("src/test/resources/records_empty.xml")));

        ResponseEntity<StatementProcessResponse> response =  theController.processFile(multipartFile);

        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode().value());

    }
}
