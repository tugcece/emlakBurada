package com.patika.emlakburadauserservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HashUtil {
    public static String generate(String password) {
        /* We used BCrypt hashpw to encrypt the password.
         * This method does encryption using the text we
         * give and the salt value it produces with gensalt.
         */
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}