package com.patika.emlakburadauserservice.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessages {
    public static final String EMAIL_ALREADY_EXIST = "There are users registered with this email.";
    public static final String USER_NOT_FOUND = "User not found.";
    public static final String PHOTO_NOT_FOUND ="Photo not found for user";
    public static final String UPLOAD_SUCCESS =  "Image uploaded successfully";
    public static final String USER_BALANCE_FAILED = "Failed to create user balance.";
}
