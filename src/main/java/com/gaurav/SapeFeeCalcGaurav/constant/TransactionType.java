package com.gaurav.SapeFeeCalcGaurav.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the various possible transaction types.
 *
 * @author gaurav
 */
public enum TransactionType {

    BUY, SELL, DEPOSIT, WITHDRAW;

    private static Map<String, TransactionType> mapping;

    static {
        mapping = new HashMap<>();
        for (TransactionType type : TransactionType.values()) {
            mapping.put(type.name(), type);
        }
    }

    public static TransactionType getTransactionType(String type) {
        return mapping.get(type);
    }
}
