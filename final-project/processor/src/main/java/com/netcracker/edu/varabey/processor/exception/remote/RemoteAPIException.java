package com.netcracker.edu.varabey.processor.exception.remote;

import com.netcracker.edu.varabey.processor.exception.ProcessorException;
import org.springframework.http.HttpStatus;

public class RemoteAPIException extends ProcessorException {
    public RemoteAPIException(String message, HttpStatus status) {
        super("Exception from remote API: \"" + message + "\";", status);
    }
}
