package com.patika.emlakburadalistingservice.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessages {
    
    public static final String LISTING_NOT_FOUND = "Listing not found. Please check your credentials and try again.";
    public static final String USER_NOT_FOUND = "User not found. Please check your credentials and try again.";
    public static final String BALANCE_ERROR = "No user balance was found. Please check the credentials or purchase a package.";
    public static final String PHOTO_NOT_FOUND ="Photo not found for listing";

}
