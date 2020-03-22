package com.gaurav.SapeFeeCalcGaurav.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gaurav.SapeFeeCalcGaurav.constant.TransactionType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * This dto represents a transaction.
 *
 * @author gaurav
 */
@Data
@Builder
public class Transaction {

    private TransactionType txnType;

    private String extTxnId;

    private String clientId;

    private String securityId;

    @JsonFormat(pattern = "MM-dd-yy")
    private LocalDate txnDate;

    private double marketValue;

    private boolean isHighPriority;
}
