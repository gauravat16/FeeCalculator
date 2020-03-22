package com.gaurav.SapeFeeCalcGaurav.constant;

/**
 * A nominal fee is charged to process each transaction, this class holds these values
 */
public interface ProcessingFees {

    /**
     * $500 for a transaction with high priority (denoted by the priority field in the transaction) .
     */
    double NORMAL_HIGH_PRIORITY_TXN_FEE = 500;

    /**
     * $100 for a transaction with normal priority and Transaction Type is Sell and Withdraw
     */
    double NORMAL_SELL_WITHDRAW_TXN_FEE = 100;

    /**
     * $50 for a transaction with normal priority and Transaction Type Code is Buy and Deposit
     */
    double NORMAL_BUY_DEPOSIT_TXN_FEE = 50;

    /**
     * Each ‘intra-day transaction should be charged $10 for both the Buy & Sell legs.
     */
    double INTRA_DAY_TXN_FEE = 10;
}
