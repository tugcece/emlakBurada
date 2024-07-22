package com.patika.emlakburadaauthenticationservice.exceptions;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessages {
    
    public static final String EMAIL_ALREADY_EXIST = "Username already exists. Please change your credentials and try again.";
    public static final String INVALID_CREDENTIAL = "Invalid username or password. Please change your credentials and try again.";

}
