package net.liplum.lib.utils;

import org.jetbrains.annotations.NotNull;

public class StringUtil {
    public static String stripTrailingZero(@NotNull String value) {
        int dotIndex = value.indexOf('.');
        if (dotIndex < 0) {
            //The string has been already striped or it isn't a number.
            return value;
        }
        int currentIndex = value.length() - 1;
        while (currentIndex > dotIndex) {
            if (value.charAt(currentIndex) != '0') {
                break;
            }
            currentIndex--;
        }
        int finalIndex = currentIndex;
        if (finalIndex == dotIndex) {
            finalIndex--;
        }
        return value.substring(0, finalIndex + 1);
    }
}
