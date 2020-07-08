package com.code.fitbase.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.code.fitbase.model.RRIResponseData;
import com.code.fitbase.util.ConsumerCompat;
import com.code.fitbase.util.HiError;
import com.huawei.hihealth.error.HiHealthError;
import com.huawei.hihealthkit.data.store.HiHealthDataStore;
import com.huawei.hihealthkit.data.store.HiRealTimeListener;

import static com.code.fitbase.util.Constants.LOGS_TAG;

/**
 * Allows you to access RRI readings in real time.
 * Notice that HiHealthKit might not be able to provide you with RRI readings
 * while real-time sports activity is tracked as the same time. However,
 * in such situations, onError callback won't be called. The symptoms
 * expected are just a lack of consumer calls, so no RRI data ever arrives.
 */
@SuppressWarnings("WeakerAccess")
public class RriRepository {
    private final Application application;
    private final Gson gson;

    public RriRepository(Application application, Gson gson) {
        this.application = application;
        this.gson = gson;
    }

    /**
     * Method with default parameters, for description see {@link RriRepository#track(ConsumerCompat, ConsumerCompat)}
     *
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     */
    public void track(@NonNull ConsumerCompat<RRIResponseData> consumer) {
        track(consumer, null);
    }

    /**
     * Connect to RRI readings and register callback parameter which will be invoked periodically
     * with newest readings.
     *
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     * @param onError  the callback invoked when an error occurs. Called on a background thread.
     */
    public void track(@NonNull ConsumerCompat<RRIResponseData> consumer, ConsumerCompat<HiError> onError) {
        HiHealthDataStore.startReadingRri(application, new HiRealTimeListener() {
            @Override
            public void onResult(int resultCode) {
                Log.i(LOGS_TAG, "RriRepository.track() resultCode is " + resultCode);
                if (resultCode != HiHealthError.SUCCESS) {
                    Log.e(LOGS_TAG, "RriRepository.track() failed to set tracking up, resultCode is " + resultCode);
                    if (onError != null) {
                        onError.accept(new HiError(resultCode, HiError.HiErrorCause.SETTING_UP_FAILED));
                    }
                }
            }

            @Override
            public void onChange(int resultCode, String data) {
                Log.i(LOGS_TAG, "RriRepository.track() value is " + data);
                if (resultCode != HiHealthError.SUCCESS) {
                    Log.e(LOGS_TAG, "RriRepository.track() unexpected result code");
                    if (onError != null) {
                        onError.accept(new HiError(resultCode, HiError.HiErrorCause.UNEXPECTED_RESULT_CODE));
                    }
                    return;
                }
                if (data == null) {
                    Log.e(LOGS_TAG, "RriRepository.track() data is null");
                    if (onError != null) {
                        onError.accept(new HiError(resultCode, HiError.HiErrorCause.DATA_OBJECT_WAS_NULL));
                    }
                    return;
                }
                try {
                    RRIResponseData rriResponseData = gson.fromJson(data, RRIResponseData.class);
                    consumer.accept(rriResponseData);
                } catch (JsonSyntaxException e) {
                    Log.e(LOGS_TAG, "RriRepository.track() malformed data received");
                    if (onError != null) {
                        onError.accept(new HiError(resultCode, HiError.HiErrorCause.MALFORMED_DATA_RETURNED));
                    }
                }
            }
        });
    }

    /**
     * Unregister listener for RRI readings.
     */
    public void stopTracking() {
        HiHealthDataStore.stopReadingRri(application, new HiRealTimeListener() {
            @Override
            public void onResult(int resultCode) {
                Log.i(LOGS_TAG, "RriRepository.stopTracking() resultCode is " + resultCode);
            }

            @Override
            public void onChange(int resultCode, String data) {
                Log.i(LOGS_TAG, "RriRepository.stopTracking() resultCode is " + resultCode);
                Log.i(LOGS_TAG, "RriRepository.stopTracking() value is " + data);
            }
        });
    }

}