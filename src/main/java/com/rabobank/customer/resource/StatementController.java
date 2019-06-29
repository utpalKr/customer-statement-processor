package com.rabobank.customer.resource;

import com.rabobank.customer.enumeration.FileType;
import com.rabobank.customer.enumeration.ProcessorServiceType;
import com.rabobank.customer.model.StatementProcessResponse;
import com.rabobank.customer.model.ValidationResult;
import com.rabobank.customer.service.FileProcessorService;
import com.rabobank.customer.service.FileProcessorServiceFactory;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@Api(value = "Statement Controller for statement processing.")
@CrossOrigin
public class StatementController {

    @Autowired
    private FileProcessorServiceFactory fileProcessorServiceFactory;

    @Autowired
    public StatementController(final FileProcessorServiceFactory fileProcessorServiceFactory) {
        this.fileProcessorServiceFactory = fileProcessorServiceFactory;
    }

    /**
     * This method processes(extract and validates) an input file and returns error results.
     *
     * @param statementFile - the {@link MultipartFile} file.
     * @return - the {@link ResponseEntity} response
     */
    @PostMapping("/statement")
    public ResponseEntity<StatementProcessResponse>
    processFile(@RequestParam("statementFile") final MultipartFile statementFile) throws Exception {

        List<ValidationResult> validationResults = null;
        if (!statementFile.isEmpty()) {
            if (statementFile.getContentType().equalsIgnoreCase(FileType.CSV.getValue())) {
                final FileProcessorService service = fileProcessorServiceFactory
                        .getProcessorService(ProcessorServiceType.CSV_PROCESSOR_SERVICE);
                validationResults = service.processStatement(statementFile);

            } else if (statementFile.getContentType().equalsIgnoreCase(FileType.TXT_XML.getValue())
                    || statementFile.getContentType().equalsIgnoreCase(FileType.XML.getValue())) {
                final FileProcessorService service = fileProcessorServiceFactory
                        .getProcessorService(ProcessorServiceType.XML_PROCESSOR_SERVICE);
                validationResults = service.processStatement(statementFile);
            } else {
                throw new IllegalArgumentException("The format of the statement file is invalid.");
            }
        } else {
            StatementProcessResponse response = StatementProcessResponse.builder()
                    .message("File not found or empty.").build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        StatementProcessResponse response = StatementProcessResponse.builder()
                .message("No Validation Error").build();
        if (null != validationResults && !validationResults.isEmpty()) {
            response = StatementProcessResponse.builder()
                    .message("There are " + validationResults.size() + " errors.")
                    .validationResults(validationResults).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}