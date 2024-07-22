package com.patika.emlakburadapurchaseservice.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessages {
    
    public static final String LISTING_NOT_FOUND = "Listing not found. Please check your credentials and try again.";
    public static final String USER_NOT_FOUND = "User not found. Please check your credentials and try again.";
    public static final String PACKAGE_NOT_FOUND = "Package not found. Please check your credentials and try again.";
    public static final String PAYMENT_DECLINED = "PAYMENT DECLINED.";

}
