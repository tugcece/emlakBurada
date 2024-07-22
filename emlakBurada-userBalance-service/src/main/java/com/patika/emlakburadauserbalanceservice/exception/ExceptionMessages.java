package com.patika.emlakburadauserbalanceservice.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessages {
    
    public static final String BALANCE_NOT_FOUND = "User balance not found for user Id:.";
    public static final String USER_NOT_FOUND = "User not found.";
}
