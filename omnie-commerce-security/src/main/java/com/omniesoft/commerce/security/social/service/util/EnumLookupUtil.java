package com.omniesoft.commerce.security.social.service.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnumLookupUtil {
    public static <E extends Enum<E>> E lookup(Class<E> enumType, String stringValue) throws Exception {
        try {
            E result = Enum.valueOf(enumType, stringValue);
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Invalid value for enum " + enumType.getSimpleName() + ": " + stringValue);
            throw new Exception();
        }
    }
}