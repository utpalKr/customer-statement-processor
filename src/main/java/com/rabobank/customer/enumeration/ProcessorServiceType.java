package com.rabobank.customer.enumeration;

import com.rabobank.customer.service.CSVFileProcessorService;
import com.rabobank.customer.service.FileProcessorService;
import com.rabobank.customer.service.XMLFileProcessorService;

/**
 * File Processor service type xml or csv.
 */

public enum ProcessorServiceType {

    CSV_PROCESSOR_SERVICE(CSVFileProcessorService.class),
    XML_PROCESSOR_SERVICE(XMLFileProcessorService.class);

    private Class<? extends FileProcessorService> serviceClazz;

    /**
     * Arg-Constructor.
     *
     * @param serviceClazz the {@link Class} to set.
     */
    ProcessorServiceType(final Class<? extends  FileProcessorService> serviceClazz) {
        this.serviceClazz = serviceClazz;
    }

    /**
     * Getter for returning service class associated to the given Appointment service.
     * @return the {@link Class} associated.
     */
    public Class<? extends  FileProcessorService> getServiceClazz() {
        return this.serviceClazz;
    }

}
