package com.cofisweak.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {
    public static boolean isFieldNotFilled(String field) {
        return field == null || field.trim().isEmpty();
    }

    public static String getScoreString(int points) {
        switch (points) {
            case 0 -> {
                return "0";
            }
            case 1 -> {
                return "15";
            }
            case 2 -> {
                return "30";
            }
            default -> {
                return "40";
            }
        }
    }
}
