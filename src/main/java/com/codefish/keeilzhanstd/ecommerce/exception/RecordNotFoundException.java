package com.codefish.keeilzhanstd.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(String s) {
        super(s);
    }
}
