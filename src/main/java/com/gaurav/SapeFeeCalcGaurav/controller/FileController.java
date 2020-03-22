package com.gaurav.SapeFeeCalcGaurav.controller;

import com.gaurav.SapeFeeCalcGaurav.dto.Transaction;
import com.gaurav.SapeFeeCalcGaurav.dto.TransactionSummary;
import com.gaurav.SapeFeeCalcGaurav.service.TransactionProcessor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/file")
@Api(value = "File controller")
public class FileController {


    private final TransactionProcessor<Transaction> transactionProcessor;

    public FileController(TransactionProcessor<Transaction> transactionProcessor) {
        this.transactionProcessor = transactionProcessor;
    }

    @GetMapping("/process/get/transactions")
    @ApiOperation(value = "Processes provided file and returns List of transactions")
    public List<Transaction> processFileTransactions(@RequestParam("filename") String fileName, @RequestParam("filePath") String filePath) throws Exception {
        return transactionProcessor.parseTransactionFile(fileName, filePath);
    }

    @GetMapping("/process/get/transaction-summary")
    @ApiOperation(value = "Processes provided file and returns List of transaction summary")
    public List<TransactionSummary> processTransactions(@RequestParam("filename") String fileName, @RequestParam("filePath") String filePath) throws Exception {
        List<TransactionSummary> summaries = transactionProcessor.processTransactions(transactionProcessor.parseTransactionFile(fileName, filePath));

        return summaries;
    }
}
