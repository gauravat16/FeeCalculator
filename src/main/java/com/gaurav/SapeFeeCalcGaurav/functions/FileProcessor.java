package com.gaurav.SapeFeeCalcGaurav.functions;

/**
 * Functional interface to process a file and return its contents.
 *
 * @param <R>
 */
@FunctionalInterface
public interface FileProcessor<R> {

    R processFile(String fileName, String filePath) throws Exception;

}
