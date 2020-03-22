package com.gaurav.SapeFeeCalcGaurav.constant;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Types of files types supported.
 */
@Getter
public enum TransactionFileType {

    CSV("csv"), XML("xml"), JSON("json");

    private static Map<String, TransactionFileType> mapping;

    static {
        mapping = new HashMap<>();
        for (TransactionFileType type : TransactionFileType.values()) {
            mapping.put(type.getExtension(), type);
        }
    }

    private String extension;

    TransactionFileType(String extension) {
        this.extension = extension;
    }

    public static TransactionFileType getTransactionFileType(String type) {
        return mapping.get(type);
    }
}
