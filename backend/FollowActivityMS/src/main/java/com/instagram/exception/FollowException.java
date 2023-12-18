package com.instagram.exception;

public class FollowException extends Exception{
    private static final long serialVersionUID = 1L;

    public FollowException(String message) {
       super(message);
    }
}
