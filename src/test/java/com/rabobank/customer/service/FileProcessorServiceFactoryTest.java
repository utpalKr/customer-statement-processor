package com.rabobank.customer.service;

import com.rabobank.customer.enumeration.ProcessorServiceType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class FileProcessorServiceFactoryTest {

    @InjectMocks
    FileProcessorServiceFactory factory;

    @Mock
    CSVFileProcessorService csvFileProcessorService;

    @Mock
    XMLFileProcessorService xmlFileProcessorService;

    @Before
    public void init() {
        List<FileProcessorService> fileProcessorServiceList = new ArrayList<>();
        fileProcessorServiceList.add(csvFileProcessorService);
        fileProcessorServiceList.add(xmlFileProcessorService);
        factory = new FileProcessorServiceFactory(fileProcessorServiceList);
    }

    @Test
    public void shouldGetXMLProcessorService() {
        FileProcessorService fileProcessorService = factory.
                getProcessorService(ProcessorServiceType.XML_PROCESSOR_SERVICE);

        Assert.assertEquals(xmlFileProcessorService,fileProcessorService);
    }


    @Test
    public void shouldGetCSVProcessorService() {
        FileProcessorService fileProcessorService = factory.
                getProcessorService(ProcessorServiceType.CSV_PROCESSOR_SERVICE);

        Assert.assertEquals(csvFileProcessorService,fileProcessorService);
    }

}
