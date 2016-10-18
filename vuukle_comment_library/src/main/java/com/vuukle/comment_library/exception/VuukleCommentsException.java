package com.vuukle.comment_library.exception;


public class VuukleCommentsException extends Exception {

    public VuukleCommentsException(String detailMessage) {
        super(detailMessage + " is not initialized or invalid");
    }
}
