package com.gaurav.SapeFeeCalcGaurav.tests;

import com.gaurav.SapeFeeCalcGaurav.constants.TestFileName;
import com.gaurav.SapeFeeCalcGaurav.dto.Transaction;
import com.gaurav.SapeFeeCalcGaurav.exception.DataProcessorException;
import com.gaurav.SapeFeeCalcGaurav.exception.FileNotFountException;
import com.gaurav.SapeFeeCalcGaurav.service.TransactionFileProcessor;
import com.gaurav.SapeFeeCalcGaurav.service.TransactionProcessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class FileTests {

    @Autowired
    private TransactionFileProcessor<Transaction> processor;

    @Autowired
    private TransactionProcessor<Transaction> transactionProcessor;

    private final static ClassLoader classLoader = ClassLoader.getSystemClassLoader();

    @Test
    public void throwExceptionWhen_FileNotPresent() {
        Assertions.assertThrows(FileNotFountException.class, () -> {
            processor.processFile("test", "test");
        });
    }

    @Test
    public void throwExceptionWhen_VerificationFails() {

        Assertions.assertThrows(DataProcessorException.class, () -> {
            String path = getPathForFile(TestFileName.INVALID_FILE);
            processor.processFile(TestFileName.INVALID_FILE, path);
        });
    }


    @Test
    public void throwExceptionWhen_dataIsIncomplete() {

        Assertions.assertThrows(DataProcessorException.class, () -> {
            String path = getPathForFile(TestFileName.INVALID_DATA_FILE);
            processor.processFile(TestFileName.INVALID_DATA_FILE, path);
        });
    }

    @Test
    public void throwExceptionWhen_WrongFileTypeIsUsed() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            String path = getPathForFile(TestFileName.WRONG_FILE);
            transactionProcessor.parseTransactionFile(TestFileName.WRONG_FILE, path);
        });
    }

    @Test
    public void throwExceptionWhen_UnsupportedFileTypeIsUsed() {

        Assertions.assertThrows(DataProcessorException.class, () -> {
            String path = getPathForFile(TestFileName.UNSUPPORTED_FILES);
            transactionProcessor.parseTransactionFile(TestFileName.UNSUPPORTED_FILES, path);
        });
    }

    @Test
    public void test_processingAllTransactions() throws Exception {
        String path = getPathForFile(TestFileName.VALID_FILE);
        Assertions.assertTrue(processor.processFile(TestFileName.VALID_FILE, path).size() == 20);
    }

    @Test
    public void test_processingTransactionsFile() throws Exception {
        String path = getPathForFile(TestFileName.VALID_FILE);
        Assertions.assertTrue(transactionProcessor.parseTransactionFile(TestFileName.VALID_FILE, path).size() == 20);
    }

    @Test
    public void test_processingAllTransactions_summary() throws Exception {
        String path = getPathForFile(TestFileName.VALID_FILE);
        List<Transaction> transactions = transactionProcessor.parseTransactionFile(TestFileName.VALID_FILE, path);
        Assertions.assertTrue(transactionProcessor.processTransactions(transactions).size() == 20);
    }

    private String getPathForFile(String fileName) {
        String path = classLoader.getResource(fileName).getPath();
        final String dir = path.substring(0, path.lastIndexOf("/"));
        return dir;
    }
}
