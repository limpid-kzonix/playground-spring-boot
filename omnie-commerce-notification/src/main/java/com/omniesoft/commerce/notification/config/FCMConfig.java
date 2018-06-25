package com.omniesoft.commerce.notification.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.omniesoft.commerce.notification.config.properties.YAMLAppConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;

@Slf4j
@Configuration
public class FCMConfig {
    @Autowired
    private YAMLAppConfigProperties props;

    @PostConstruct
    public void initFirebaseApp() throws Exception {
        if (!FirebaseApp.getApps().isEmpty()) {
            log.info("FCM was configured previously");
            return;
        }

        FileInputStream serviceAccount = new FileInputStream(props.firebase.adminSdkSecretFile.getFile());

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl(props.firebase.databaseUrl.toString())
                .build();

        FirebaseApp.initializeApp(options);
        log.info("FCM successfully configured for: {}", props.firebase.databaseUrl);
    }
}
