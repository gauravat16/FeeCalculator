package com.gaurav.SapeFeeCalcGaurav.service.impl;

import com.gaurav.SapeFeeCalcGaurav.constant.ProcessingFees;
import com.gaurav.SapeFeeCalcGaurav.constant.TransactionFileType;
import com.gaurav.SapeFeeCalcGaurav.constant.TransactionType;
import com.gaurav.SapeFeeCalcGaurav.dto.Transaction;
import com.gaurav.SapeFeeCalcGaurav.dto.TransactionSummary;
import com.gaurav.SapeFeeCalcGaurav.exception.DataProcessorException;
import com.gaurav.SapeFeeCalcGaurav.service.TransactionFileProcessor;
import com.gaurav.SapeFeeCalcGaurav.service.TransactionProcessor;
import com.gaurav.SapeFeeCalcGaurav.util.FileUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransactionProcessorImpl implements TransactionProcessor<Transaction> {

    private final TransactionFileProcessor<Transaction> csvTxnFileProcessor;

    public TransactionProcessorImpl(@Qualifier("csv-txn-processor") TransactionFileProcessor<Transaction> csvTxnFileProcessor) {
        this.csvTxnFileProcessor = csvTxnFileProcessor;
    }

    @Override
    public List<Transaction> parseTransactionFile(String fileName, String filePath) throws Exception {
        TransactionFileType transactionFileType = FileUtils.getTransactionFileType(fileName, filePath);

        Assert.notNull(transactionFileType, "Valid file not provided");

        switch (transactionFileType) {
            case CSV:
                return csvTxnFileProcessor.processFile(fileName, filePath);
            case XML:
            case JSON:
                throw new DataProcessorException("File not supported yet");
            default:
                throw new DataProcessorException("Invalid file!");

        }
    }

    @Override
    public List<TransactionSummary> processTransactions(List<Transaction> transactions) {

        Map<String, Transaction> txnIdMap = transactions.stream().collect(Collectors.toMap(Transaction::getExtTxnId, t -> t));

        List<TransactionSummary> summaries = new ArrayList<>();

        summaries.addAll(processIntraDayTransactions(txnIdMap));
        summaries.addAll(processNormalTransactions(txnIdMap));
        summaries = condenseSummary(summaries);
        sortSummaries(summaries);
        return summaries;
    }

    /**
     * Processes the transactions for calculating the intra day txn fees.
     *
     * @param txnIdMap
     * @return List of intraday txns
     */
    private List<TransactionSummary> processIntraDayTransactions(Map<String, Transaction> txnIdMap) {
        Map<String, Transaction> transactionMap = new HashMap<>();
        List<TransactionSummary> summaries = new ArrayList<>();

        for (Transaction transaction : txnIdMap.values()) {
            String s = transaction.getClientId() + transaction.getSecurityId() + transaction.getTxnDate() + transaction.getTxnType().name();
            transactionMap.put(s, transaction);
        }

        for (Map.Entry<String, Transaction> e : transactionMap.entrySet()) {
            Transaction transaction = e.getValue();
            if (transaction.getTxnType() == TransactionType.BUY) {
                String s = transaction.getClientId() + transaction.getSecurityId() + transaction.getTxnDate() + TransactionType.SELL.name();

                if (transactionMap.containsKey(s)) {
                    summaries.add(mapTransactionToSummary(transaction, ProcessingFees.INTRA_DAY_TXN_FEE));
                    summaries.add(mapTransactionToSummary(transactionMap.get(s), ProcessingFees.INTRA_DAY_TXN_FEE));
                    txnIdMap.remove(transaction.getExtTxnId());
                    txnIdMap.remove(transactionMap.get(s).getExtTxnId());
                }
            }

        }

        return summaries;
    }

    /**
     * Processes the transactions for calculating the normal txn fees.
     *
     * @param txnIdMap
     * @return List of normal txns
     */
    private List<TransactionSummary> processNormalTransactions(Map<String, Transaction> txnIdMap) {
        List<TransactionSummary> summaries = new ArrayList<>();

        for (Map.Entry<String, Transaction> e : txnIdMap.entrySet()) {
            Transaction transaction = e.getValue();
            TransactionType transactionType = transaction.getTxnType();
            if (transaction.isHighPriority()) {
                summaries.add(mapTransactionToSummary(transaction, ProcessingFees.NORMAL_HIGH_PRIORITY_TXN_FEE));
            } else if (transactionType == TransactionType.SELL || transactionType == TransactionType.WITHDRAW) {
                summaries.add(mapTransactionToSummary(transaction, ProcessingFees.NORMAL_SELL_WITHDRAW_TXN_FEE));
            } else if (transactionType == TransactionType.BUY || transactionType == TransactionType.DEPOSIT) {
                summaries.add(mapTransactionToSummary(transaction, ProcessingFees.NORMAL_BUY_DEPOSIT_TXN_FEE));
            }
        }

        return summaries;
    }

    private void sortSummaries(List<TransactionSummary> transactionSummaries) {
        Collections.sort(transactionSummaries, Comparator.comparing(TransactionSummary::getClientId)
                .thenComparing(TransactionSummary::getTxnType)
                .thenComparing(TransactionSummary::getTxnDate)
                .thenComparing(TransactionSummary::isHighPriority));
    }

    private List<TransactionSummary> condenseSummary(List<TransactionSummary> transactionSummaries) {
        Map<String, List<TransactionSummary>> summaryMap = new HashMap<>();

        for (TransactionSummary transactionSummary : transactionSummaries) {
            String key = transactionSummary.getClientId() + transactionSummary.getTxnType()
                    + transactionSummary.getTxnDate() + (transactionSummary.isHighPriority() ? 'Y' : 'N');
            List<TransactionSummary> list = summaryMap.getOrDefault(key, new ArrayList<>());
            list.add(transactionSummary);
            summaryMap.put(key, list);
        }

        List<TransactionSummary> summaries = new ArrayList<>();

        for (List<TransactionSummary> summary : summaryMap.values()) {
            double fee = summary.stream().mapToDouble(TransactionSummary::getProcessingFee).sum();
            summary.get(0).setProcessingFee(fee);
            summaries.add(summary.get(0));
        }

        return summaries;
    }


    public TransactionSummary mapTransactionToSummary(Transaction transaction, double fee) {
        return TransactionSummary.builder()
                .clientId(transaction.getClientId())
                .isHighPriority(transaction.isHighPriority())
                .txnDate(transaction.getTxnDate())
                .txnType(transaction.getTxnType())
                .processingFee(fee)
                .build();
    }


}
