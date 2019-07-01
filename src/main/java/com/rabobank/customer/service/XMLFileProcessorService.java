package com.rabobank.customer.service;

import com.rabobank.customer.model.Records;
import com.rabobank.customer.model.ValidationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.util.List;

/**
 * Service class for XML file processing.
 */
@Service
public class XMLFileProcessorService implements FileProcessorService {

    private static final Logger LOG = LoggerFactory.getLogger(XMLFileProcessorService.class);

    @Autowired
    private ValidationService validationService;

    @Autowired
    public XMLFileProcessorService(final ValidationService validationService) {
        this.validationService = validationService;
    }

    /**
     * This method processes a XML file and returns List of records.
     *
     * @param mFile - the {@link MultipartFile} to be processed.
     * @return
     * @throws Exception
     */
    @Override
    public List<ValidationResult> processStatement(final MultipartFile mFile) throws Exception {
        LOG.debug("Processing multipart file :" + mFile.getName());
        final JAXBContext jaxbContext = JAXBContext.newInstance(Records.class);
        final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Records records = (Records) jaxbUnmarshaller.unmarshal(mFile.getInputStream());

        List<ValidationResult> results = validationService.validate(records.getRecords());
        LOG.debug("There are " + results.size() + " XML validation failures.");

        return results;
    }
}
