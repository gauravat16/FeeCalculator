package com.gaurav.SapeFeeCalcGaurav.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gaurav.SapeFeeCalcGaurav.constant.TransactionType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * This dto represents transaction summary.
 */
@Data
@Builder
public class TransactionSummary {

    private TransactionType txnType;

    private String clientId;

    @JsonFormat(pattern = "MM-dd-yy")
    private LocalDate txnDate;

    private double processingFee;

    private boolean isHighPriority;
}
