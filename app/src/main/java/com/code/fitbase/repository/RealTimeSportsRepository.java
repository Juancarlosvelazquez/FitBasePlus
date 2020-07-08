package com.code.fitbase.repository;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.code.fitbase.model.RealTimeSportData;
import com.code.fitbase.util.ConsumerCompat;
import com.code.fitbase.util.HiError;
import com.huawei.hihealth.error.HiHealthError;
import com.huawei.hihealthkit.data.store.HiHealthDataStore;
import com.huawei.hihealthkit.data.store.HiSportDataCallback;

import static com.code.fitbase.util.Constants.LOGS_TAG;

/**
 * Provides access to continuous updates of real time sports activities.
 * <p>
 * Please note it only works for workout started via Huawei Health App. Workout started on watches will
 * not be observable by this class.
 */
@SuppressWarnings("WeakerAccess")
public class RealTimeSportsRepository {
    private final Application application;

    public RealTimeSportsRepository(Application application) {
        this.application = application;
    }

    /**
     * Method with default parameters, for description see {@link RealTimeSportsRepository#track(ConsumerCompat, ConsumerCompat)}
     *
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     */
    public void track(@NonNull ConsumerCompat<RealTimeSportData> consumer) {
        track(consumer, null);
    }

    /**
     * Connect to real time sports data readings and register callback parameter which will be invoked periodically
     * with newest readings.
     *
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     * @param onError  the callback invoked when an error occurs. Called on a background thread.
     */
    public void track(@NonNull ConsumerCompat<RealTimeSportData> consumer, ConsumerCompat<HiError> onError) {
        HiHealthDataStore.startRealTimeSportData(application, new HiSportDataCallback() {
            @Override
            public void onResult(int resultCode) {
                Log.i(LOGS_TAG, "RealTimeSportsRepository.track() resultCode is " + resultCode);
                if (resultCode != HiHealthError.SUCCESS) {
                    Log.e(LOGS_TAG, "RealTimeSportsRepository.track() failed to set tracking up, resultCode is " + resultCode);
                    if (onError != null) {
                        onError.accept(new HiError(resultCode, HiError.HiErrorCause.SETTING_UP_FAILED));
                    }
                }
            }

            @Override
            public void onDataChanged(int sportState, Bundle bundle) {
                Log.i(LOGS_TAG, "RealTimeSportsRepository.track() sportState is " + sportState);
                Log.i(LOGS_TAG, "RealTimeSportsRepository.track() bundle is " + bundle);

                if (bundle == null) {
                    Log.e(LOGS_TAG, "RealTimeSportsRepository.track() data is null");
                    if (onError != null) {
                        // result code is not returned in the callback, so 0 is inferred
                        onError.accept(new HiError(0, HiError.HiErrorCause.DATA_OBJECT_WAS_NULL));
                    }
                    return;
                }
                try {
                    RealTimeSportData data = RealTimeSportData.createFromBundle(bundle);
                    consumer.accept(data);
                } catch (Exception e) {
                    Log.e(LOGS_TAG, "RealTimeSportsRepository.track() malformed data received");
                    if (onError != null) {
                        // result code is not returned in the callback, so 0 is inferred
                        onError.accept(new HiError(0, HiError.HiErrorCause.MALFORMED_DATA_RETURNED));
                    }
                }

            }
        });
    }

    /**
     * Unregister listener for real time sports readings.
     */
    public void stopTracking() {
        HiHealthDataStore.stopRealTimeSportData(application, new HiSportDataCallback() {
            @Override
            public void onResult(int resultCode) {
                Log.i(LOGS_TAG, "RealTimeSportsRepository.stopTracking() resultCode is " + resultCode);
            }

            @Override
            public void onDataChanged(int sportState, Bundle bundle) {
                Log.i(LOGS_TAG, "RealTimeSportsRepository.stopTracking() sportState is " + sportState);
                Log.i(LOGS_TAG, "RealTimeSportsRepository.stopTracking() bundle is " + bundle);
            }
        });
    }
}
