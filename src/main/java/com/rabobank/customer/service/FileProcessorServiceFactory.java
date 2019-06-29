package com.rabobank.customer.service;

import com.rabobank.customer.enumeration.ProcessorServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Factory class for File Processor Services.
 */
@Service
public class FileProcessorServiceFactory {

    @Autowired
    private final List<FileProcessorService> serviceList;

    @Autowired
    public FileProcessorServiceFactory(final List<FileProcessorService> serviceList) {
        this.serviceList = serviceList;
    }

    /**
     * This method returns the desired service class.
     *
     * @param serviceType - the {@link ProcessorServiceType} object.
     * @return - the required service {@link FileProcessorService}
     */
    public FileProcessorService getProcessorService(final ProcessorServiceType serviceType) {
        return serviceList.stream()
                .filter(service -> serviceType.getServiceClazz().isInstance(service))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Unable to find service class from given service type."));
    }
}