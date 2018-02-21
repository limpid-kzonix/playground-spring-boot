package com.omniesoft;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.nio.charset.Charset;
import java.util.Random;

@Slf4j
@RunWith(Theories.class)
public class PasswordHashTest {

    @DataPoint
    public static String generatePassword() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
    }

    @Theory
    @Test
    public void encodePasswords(String password) {
        String s = generatePassword();
        log.info("Generated value {}", s);
        String encode = BCrypt.hashpw(s, BCrypt.gensalt());
        log.info("Encoded value {}", encode);

    }

    //    @Test
    public void generateRandomString() {
        for (int i = 0; i < 3; i++) {
            String format = String.format("%04d", RandomUtils.nextInt(1000000000, 999999999));
            Assert.assertEquals(4, format.length());
        }

    }

    @Test
    public void generateOmnieCard() {
        for (int i = 0; i < 3; i++) {
            System.out.println(("omnie#" + (int) ((System.nanoTime() / Math.PI) / (RandomUtils.nextInt(30000, 99999) * Math.E))));
        }

    }
}
