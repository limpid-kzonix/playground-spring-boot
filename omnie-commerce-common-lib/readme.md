# Base error message structure 

## ApiError
```java
public class ApiError {
    private HttpStatus status;
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;
    private List<ApiSubError> subErrors;
    private Integer detailedCode;
}
```

## ApiValidationError - ApiSubError implementation 

```java
public class ApiValidationError implements ApiSubError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;
}

```