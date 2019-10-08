package com.onekin.featurecloud.exceptions;

public class CoreAssetNotFoundException extends Exception {
    public CoreAssetNotFoundException() {
    }

    public CoreAssetNotFoundException(String message) {
        super(message);
    }

    public CoreAssetNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoreAssetNotFoundException(Throwable cause) {
        super(cause);
    }
}
