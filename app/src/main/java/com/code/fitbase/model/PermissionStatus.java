package com.code.fitbase.model;

public enum PermissionStatus {

    /**
     * No permission was requested before, this value indicates that requesting authorization should
     * be next step.
     */
    REQUEST_AUTHORIZATION(0),
    /**
     * This means permission was granted by the user. Be aware this only applies to write permission.
     * There is no way to ask about read permissions status.
     */
    GOT_PERMISSION(1),
    /**
     * This means permission was not granted by the user. Be aware this only applies to write permission.
     * There is no way to ask about read permissions status.
     */
    PERMISSION_NOT_GRANTED(2),
    /**
     * Unknown problem occurred.
     */
    UNKNOWN(-1);

    private int statusCode;

    PermissionStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public static PermissionStatus getPermissionStatus(int statusCode) {
        switch (statusCode) {
            case 0:
                return REQUEST_AUTHORIZATION;
            case 1:
                return GOT_PERMISSION;
            case 2:
                return PERMISSION_NOT_GRANTED;
            default:
                return UNKNOWN;
        }
    }

    public int getStatusCode() {
        return statusCode;
    }
}
