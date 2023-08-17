package com.shoggoth.exceptions;

public class RepositoryException extends RuntimeException {
    public RepositoryException(Throwable throwable) {
        super(throwable);
    }
}
