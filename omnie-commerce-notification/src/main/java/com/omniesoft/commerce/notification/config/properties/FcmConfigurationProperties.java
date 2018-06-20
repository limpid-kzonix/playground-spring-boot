package com.omniesoft.commerce.notification.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.Resource;

@Setter
@Getter
public class FcmConfigurationProperties {
    public Resource adminSdkSecretFile;
    public Resource databaseUrl;
}
