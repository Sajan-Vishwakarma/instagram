package com.instagram.exception;

public class PostActivityException extends Exception{
    private static final long serialVersionUID = 1L;

    public PostActivityException(String message) {
       super(message);
    }
}