package com.code.fitbase.util;

/**
 * Contains error data including library's own resultCode and a convenient
 * translation ({@link HiError#cause})
 */
public class HiError {
    /**
     * Original resultCode returned by HiHealthKit library.
     */
    private final int resultCode;

    /**
     * A general cause of the error. For the precise error code see {@link HiError#resultCode}
     */
    private final HiErrorCause cause;

    public HiError(int resultCode, HiErrorCause cause) {
        this.resultCode = resultCode;
        this.cause = cause;
    }

    public int getResultCode() {
        return resultCode;
    }

    public HiErrorCause getCause() {
        return cause;
    }

    public enum HiErrorCause {
        /**
         * This indicates that the attempt to obtain data has failed before any data were expected yet,
         * that is during the initialization and preparation for the data-carrying callbacks.
         */
        SETTING_UP_FAILED,

        /**
         * This indicates that a callback from the HiHealthKit expected to bring a piece of data
         * was in fact called but the result code was not what was expected. In most cases
         * result code 0 is expected, although in some cases other constants are used by
         * HiHealthKit to indicate success, so should 0 not be expected, this error is going
         * to come back to you on onError callback.
         */
        UNEXPECTED_RESULT_CODE,

        /**
         * This indicates that HiHealthKit returned a null instead of the expected instance
         * carrying data requested.
         */
        DATA_OBJECT_WAS_NULL,

        /**
         * This indicates that returned object carrying data was one of unexpected type.
         */
        UNEXPECTED_DATA_TYPE_RETURNED,

        /**
         * This indicates that some data was returned, but in the process of decoding it
         * an error occurred most likely due to an unexpected format of the data.
         */
        MALFORMED_DATA_RETURNED
    }
}


