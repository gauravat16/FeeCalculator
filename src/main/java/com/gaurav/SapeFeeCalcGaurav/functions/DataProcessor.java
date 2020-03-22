package com.gaurav.SapeFeeCalcGaurav.functions;


/**
 * Processes string data and returns an object.
 *
 * @param <R>
 */
@FunctionalInterface
public interface DataProcessor<R> {

    /**
     * Process data and return an output.
     *
     * @param data
     * @return
     */
    R process(String data);

}
