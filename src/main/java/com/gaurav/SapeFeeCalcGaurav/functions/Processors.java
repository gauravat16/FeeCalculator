package com.gaurav.SapeFeeCalcGaurav.functions;

import com.gaurav.SapeFeeCalcGaurav.constant.TransactionType;
import com.gaurav.SapeFeeCalcGaurav.dto.Transaction;
import com.gaurav.SapeFeeCalcGaurav.exception.DataProcessorException;
import com.gaurav.SapeFeeCalcGaurav.util.DateUtils;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

@UtilityClass
public class Processors {

    /**
     * This object holds the lambda for processing csv data.
     */
    public DataProcessor<Transaction> csvTxnDataProcessor = s -> {
        if (StringUtils.isEmpty(s)) {
            throw new DataProcessorException("empty string!");
        }

        String[] elements = s.split(",");

        int index = 0;

        return Transaction.builder()
                .extTxnId(elements[index++])
                .clientId(elements[index++])
                .securityId(elements[index++])
                .txnType(TransactionType.getTransactionType(elements[index++]))
                .txnDate(DateUtils.getLocaldateForString(elements[index++]))
                .marketValue(Double.parseDouble(elements[index++]))
                .isHighPriority("Y".equals(elements[index]))
                .build();

    };

}
