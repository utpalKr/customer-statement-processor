package com.rabobank.customer.service;

import com.rabobank.customer.model.Record;
import com.rabobank.customer.model.ValidationResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Statement Extractor Interface provides template for extracting statements.
 */
@Service
public interface FileProcessorService {

    /**
     * Processes a file by extracting the data and creates a list of records out of it.
     *
     * @param file - the {@link MultipartFile} to be processed.
     * @return {@link List} of {@link Record} records
     * @throws Exception
     */
    List<ValidationResult> processStatement(final MultipartFile file) throws Exception;

}
