package com.omniesoft.commerce.notification.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.*;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.IOException;

public class FCMConfig {

    public static void initFirebaseApp() throws IOException {
        FileInputStream serviceAccount = new FileInputStream(
                new ClassPathResource("omni-141514-firebase-adminsdk-kik26-5aeb41c496.json").getFile()
        );

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://omni-141514.firebaseio.com/")
                .build();

        FirebaseApp.initializeApp(options);

    }

    private static void listOfUsers() throws FirebaseAuthException {
        FirebaseApp instance = FirebaseApp.getInstance();
        ListUsersPage page = null;
        page = FirebaseAuth.getInstance(instance).listUsers(null);

        while (page != null) {
            for (ExportedUserRecord user : page.getValues()) {
                System.out.println("User: " + user.getUid());
                System.out.println(user);
            }
            page = page.getNextPage();
        }
    }

    public static void main(String[] args) {
        try {
            initFirebaseApp();

//            createUser();

            listOfUsers();

        } catch (IOException | FirebaseAuthException e) {
            e.printStackTrace();
        }
    }

    private static void createUser() throws FirebaseAuthException {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail("user@example.com")
                .setEmailVerified(false)
                .setPassword("secretPassword")
                .setPhoneNumber("+11234567890")
                .setDisplayName("John Doe")
                .setPhotoUrl("http://www.example.com/12345678/photo.png")
                .setDisabled(false);

        UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
        System.out.println("Successfully created new user: " + userRecord.getUid());
    }
}
