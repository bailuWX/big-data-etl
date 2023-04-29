package com.bigdata.omp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "savelog-properties")
@Data
public class SaveLogConfig {
    private String saveFileDirectory;

    private String path;


}
