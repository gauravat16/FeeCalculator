package com.gaurav.SapeFeeCalcGaurav.service.impl;

import com.gaurav.SapeFeeCalcGaurav.config.AppConfig;
import com.gaurav.SapeFeeCalcGaurav.dto.Transaction;
import com.gaurav.SapeFeeCalcGaurav.exception.DataProcessorException;
import com.gaurav.SapeFeeCalcGaurav.functions.DataProcessor;
import com.gaurav.SapeFeeCalcGaurav.functions.Processors;
import com.gaurav.SapeFeeCalcGaurav.service.TransactionFileProcessor;
import com.gaurav.SapeFeeCalcGaurav.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implementation of {@link TransactionFileProcessor} provides functions to process csv file.
 */
@Service("csv-txn-processor")
public class CSVTxnFileProcessor implements TransactionFileProcessor<Transaction> {

    @Autowired
    private AppConfig appConfig;

    @Override
    public List<Transaction> processFile(String fileName, String filePath) throws Exception {

        List<Transaction> transactions;
        String[] headers = appConfig.getTxnCsvHeaders();
        int headerCount = headers.length;
        DataProcessor<Transaction> csvTxnDataProcessor = Processors.csvTxnDataProcessor;

        try (Stream<String> dataStream = FileUtils.getFileStream(fileName, filePath)) {
            verifyData(fileName, filePath);

            /**
             * Skip the first element as that is the header.
             */
            transactions = dataStream.skip(1)
                    .filter(s -> s.split(",").length == headerCount)
                    .map(csvTxnDataProcessor::process)
                    .collect(Collectors.toList());
        }


        return transactions;
    }

    /**
     * Verifies data by matching headers available in application properties and in the file.
     *
     * @param fileName
     * @param filePath
     * @throws Exception
     */
    @Override
    public void verifyData(String fileName, String filePath) throws Exception {

        try (Stream<String> dataStream = FileUtils.getFileStream(fileName, filePath)) {

            Optional<String> fileHeaderOptional = dataStream.findFirst();
            if (!fileHeaderOptional.isPresent()) {
                throw new DataProcessorException("No data in file");
            }
            String data = fileHeaderOptional.get();

            data = FileUtils.removeUTF8BOM(data);
            String[] headers = appConfig.getTxnCsvHeaders();
            int headerCount = headers.length;

            String[] fileHeaders = data.split(",");
            if (fileHeaders.length != headerCount) {
                throw new DataProcessorException("Invalid headers");
            }
            for (int i = 0; i < headerCount; i++) {
                String h1 = headers[i].trim();
                String h2 = fileHeaders[i].trim();

                if (!h1.equals(h2)) {
                    throw new DataProcessorException("Invalid headers");
                }
            }
        }
    }
}
