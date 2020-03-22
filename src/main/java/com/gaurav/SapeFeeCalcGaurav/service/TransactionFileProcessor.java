package com.gaurav.SapeFeeCalcGaurav.service;

import com.gaurav.SapeFeeCalcGaurav.dto.Transaction;
import com.gaurav.SapeFeeCalcGaurav.functions.FileProcessor;

import java.util.List;

/**
 * Extension to FileProcessor adds method to verify data.
 *
 * @author gaurav
 */
public interface TransactionFileProcessor<T extends Transaction> extends FileProcessor<List<T>> {

    void verifyData(String fileName, String filePath) throws Exception;

}
