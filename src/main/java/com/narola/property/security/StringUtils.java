package com.narola.property.security;

public class StringUtils {
    public static boolean check(String...params) {
        for (int i = 0; i < params.length; i++) {
            if(!params[i].isBlank()) {
                return false;
            }
        }
        return true;
    }
}
