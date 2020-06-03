package com.proz.vault.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author yubraj.singh
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends RuntimeException  {
    public RecordNotFoundException(String exception) {
        super(exception);
    }
}
