package com.rabobank.customer.service;

import com.rabobank.customer.model.Record;
import com.rabobank.customer.model.ValidationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Service class for CSV File Processing.
 */
@Service
public class CSVFileProcessorService implements FileProcessorService {

    private static final Logger LOG = LoggerFactory.getLogger(CSVFileProcessorService.class);

    @Autowired
    private ValidationService validationService;

    @Autowired
    public CSVFileProcessorService(final ValidationService validationService) {
        this.validationService = validationService;
    }

    /**
     * This method processes a CSV file and returns List of error records.
     *
     * @param file - the {@link File} to be processed.
     * @return
     * @throws Exception
     */
    @Override
    public List<ValidationResult> processStatement(final MultipartFile file) throws Exception {

        LOG.debug("Processing file: " + file.getName());

        final InputStream inputStream = file.getInputStream();
        final BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        final List<Record> records = br.lines()
                .skip(1)
                .map(line -> populateRecord(Arrays.asList(line.split(","))))
                .collect(toList());

        final List<ValidationResult> failedResults = validationService.validate(records);
        LOG.debug("There are " + failedResults.size() + " CSV validation failures.");
        return failedResults;
    }

    /**
     * Populates a record from the fields in csv file.
     *
     * @param fields - the list of {@link String} fields.
     * @return a record
     */
    private Record populateRecord(final List<String> fields) {

        LOG.debug("Populating records form the fields.");
        Record record = new Record(Integer.parseInt(fields.get(0)), fields.get(1),
                fields.get(2),
                Double.parseDouble(fields.get(3)),
                Double.parseDouble(fields.get(4)),
                Double.parseDouble(fields.get(5)));
        return record;
    }

}
