package com.omniesoft.commerce.common.responce;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


/**
 * @author Vitalii Martynovskyi
 * @since 09.10.17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage {
    public static final ResponseMessage OK = new ResponseMessage("ok");
    private String message;


    /**
     * @author Vitalii Martynovskyi
     * @since 11.10.17
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Created {
        private UUID id;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Option<T> {
        private T value;
    }
}
