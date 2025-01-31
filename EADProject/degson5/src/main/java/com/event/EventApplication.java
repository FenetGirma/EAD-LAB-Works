package com.event;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventApplication {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME", "root"));  // Default: root
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD", ""));  
        System.setProperty("MAIL_USERNAME", dotenv.get("MAIL_USERNAME", ""));
        System.setProperty("MAIL_PASSWORD", dotenv.get("MAIL_PASSWORD", ""));
        System.setProperty("GOOGLE_CLIENT_ID", dotenv.get("GOOGLE_CLIENT_ID", ""));
        System.setProperty("GOOGLE_CLIENT_SECRET", dotenv.get("GOOGLE_CLIENT_SECRET", ""));

        SpringApplication.run(EventApplication.class, args);
    }
}
