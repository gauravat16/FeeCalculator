package com.gaurav.SapeFeeCalcGaurav.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "app")
@Component
@Data
public class AppConfig {

    private String[] txnCsvHeaders;
}
