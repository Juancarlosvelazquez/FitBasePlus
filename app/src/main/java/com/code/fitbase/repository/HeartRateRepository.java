package com.code.fitbase.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.code.fitbase.model.HeartBeatRate;
import com.code.fitbase.util.ConsumerCompat;
import com.code.fitbase.util.HiError;
import com.huawei.hihealth.error.HiHealthError;
import com.huawei.hihealthkit.data.store.HiHealthDataStore;
import com.huawei.hihealthkit.data.store.HiRealTimeListener;

import static com.code.fitbase.util.Constants.LOGS_TAG;

/**
 * Provides access to continuous heart beat rate readings.
 */
@SuppressWarnings("WeakerAccess")
public class HeartRateRepository {
    private final Application application;
    private final Gson gson;

    public HeartRateRepository(Application application, Gson gson) {
        this.application = application;
        this.gson = gson;
    }

    /**
     * Method with default parameters, for description see {@link HeartRateRepository#track(ConsumerCompat, ConsumerCompat)}
     *
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     */
    public void track(@NonNull ConsumerCompat<HeartBeatRate> consumer) {
        track(consumer, null);
    }

    /**
     * Connect to heart beat rate readings and register callback parameter which will be invoked periodically
     * with newest readings.
     * <p>
     * I current version of SDK if watch fail to read heart beat it reports 255 beats per second.
     *
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     * @param onError  the callback invoked when an error occurs. Called on a background thread.
     */
    public void track(@NonNull ConsumerCompat<HeartBeatRate> consumer, ConsumerCompat<HiError> onError) {
        HiHealthDataStore.startReadingHeartRate(application, new HiRealTimeListener() {
            @Override
            public void onResult(int resultCode) {
                Log.i(LOGS_TAG, "HeartRateRepository.track() HEART RATE resultCode is " + resultCode);
                if (resultCode != HiHealthError.SUCCESS) {
                    Log.e(LOGS_TAG, "HeartRateRepository.track() failed to set tracking up, resultCode is " + resultCode);
                    if (onError != null) {
                        onError.accept(new HiError(resultCode, HiError.HiErrorCause.SETTING_UP_FAILED));
                    }
                }
            }

            @Override
            public void onChange(int resultCode, String data) {
                Log.i(LOGS_TAG, "HeartRateRepository.track() HEART RATE value is " + data + ", result code = " + resultCode);
                if (resultCode != HiHealthError.SUCCESS) {
                    Log.e(LOGS_TAG, "HeartRateRepository.track() unexpected result code");
                    if (onError != null) {
                        onError.accept(new HiError(resultCode, HiError.HiErrorCause.UNEXPECTED_RESULT_CODE));
                    }
                    return;
                }
                if (data == null) {
                    Log.e(LOGS_TAG, "HeartRateRepository.track() data is null");
                    if (onError != null) {
                        onError.accept(new HiError(resultCode, HiError.HiErrorCause.DATA_OBJECT_WAS_NULL));
                    }
                    return;
                }
                try {
                    HeartBeatRate heartBeatRate = gson.fromJson(data, HeartBeatRate.class);
                    consumer.accept(heartBeatRate);
                } catch (JsonSyntaxException e) {
                    Log.e(LOGS_TAG, "HeartRateRepository.track() malformed data received");
                    if (onError != null) {
                        onError.accept(new HiError(resultCode, HiError.HiErrorCause.MALFORMED_DATA_RETURNED));
                    }
                }
            }
        });
    }

    /**
     * Unregister listener for heart beat rate readings.
     */
    public void stopTracking() {
        HiHealthDataStore.stopReadingHeartRate(application, new HiRealTimeListener() {
            @Override
            public void onResult(int resultCode) {
                Log.i(LOGS_TAG, "HeartRateRepository.stopTracking() resultCode is " + resultCode);
            }

            @Override
            public void onChange(int resultCode, String data) {
                Log.i(LOGS_TAG, "HeartRateRepository.stopTracking() resultCode is " + resultCode);
                Log.i(LOGS_TAG, "HeartRateRepository.stopTracking() value is " + data);
            }
        });
    }

}
