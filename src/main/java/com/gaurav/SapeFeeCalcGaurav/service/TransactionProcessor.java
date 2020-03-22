package com.gaurav.SapeFeeCalcGaurav.service;

import com.gaurav.SapeFeeCalcGaurav.dto.Transaction;
import com.gaurav.SapeFeeCalcGaurav.dto.TransactionSummary;

import java.util.List;

/**
 * Provides funtionality to parse data and provide transactions and their summary.
 *
 * @param <T>
 */
public interface TransactionProcessor<T extends Transaction> {

    List<T> parseTransactionFile(String fileName, String filePath) throws Exception;

    List<TransactionSummary> processTransactions(List<Transaction> transactions);

    TransactionSummary mapTransactionToSummary(Transaction transaction, double fee);


}
