package com.gaurav.SapeFeeCalcGaurav.util;

import com.gaurav.SapeFeeCalcGaurav.constant.TransactionFileType;
import com.gaurav.SapeFeeCalcGaurav.exception.FileNotFountException;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@UtilityClass
public class FileUtils {

    public static final String BOM = "\uFEFF";

    public boolean checkIfFileExists(String fileName, String filePath) {
        return Files.exists(getFilePath(fileName, filePath));
    }

    public Stream<String> getFileStream(String fileName, String filePath) throws IOException {
        boolean fileExists = FileUtils.checkIfFileExists(fileName, filePath);

        if (!fileExists) {
            throw new FileNotFountException("File - " + fileName + " not found!");
        }

        return Files.lines(getFilePath(fileName, filePath));
    }

    public TransactionFileType getTransactionFileType(String fileName, String filePath) {
        boolean fileExists = FileUtils.checkIfFileExists(fileName, filePath);

        if (!fileExists) {
            throw new FileNotFountException("File - " + fileName + " not found!");
        }

        String[] arr = fileName.split(Pattern.quote("."));
        if (arr.length == 2) {
            return TransactionFileType.getTransactionFileType(arr[1]);
        }
        return null;

    }

    public Path getFilePath(String fileName, String filePath) {
        return Paths.get(filePath + File.separator + fileName);
    }


    public String removeUTF8BOM(String s) {
        if (s.startsWith(BOM)) {
            s = s.substring(1);
        }
        return s;
    }
}
