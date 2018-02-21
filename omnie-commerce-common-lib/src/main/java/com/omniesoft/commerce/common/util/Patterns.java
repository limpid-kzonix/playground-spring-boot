package com.omniesoft.commerce.common.util;

/**
 * @author Vitalii Martynovskyi
 * @since 09.10.17
 */
public interface Patterns {
    String EMAIL = "^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$";
    String LOGIN = "^(?=.*[a-z0-9]$)([a-z0-9][_.]?){3,50}$";
    String PHONE = "(^(\\+\\d{1,5})?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{2}[\\s.-]\\d{2}$)|(\\+[\\d\\s]{7,20})$";
}
