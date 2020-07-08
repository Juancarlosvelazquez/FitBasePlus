package com.code.fitbase.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.code.fitbase.model.SleepData;
import com.code.fitbase.util.ConsumerCompat;
import com.code.fitbase.util.HiError;
import com.huawei.hihealth.error.HiHealthError;
import com.huawei.hihealthkit.HiHealthDataQuery;
import com.huawei.hihealthkit.HiHealthDataQueryOption;
import com.huawei.hihealthkit.data.HiHealthSetData;
import com.huawei.hihealthkit.data.store.HiHealthDataStore;
import com.huawei.hihealthkit.data.type.HiHealthSetType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.code.fitbase.util.Constants.DEFAULT_TIMEOUT;
import static com.code.fitbase.util.Constants.LOGS_TAG;

/**
 * Provides access to history of sleep data recorded in given time date.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class SleepDataRepository {
    private static final int REM_SLEEP = 44101;//FLOAT
    private static final int DEEP_SLEEP = 44102;//FLOAT
    private static final int LIGHT_SLEEP = 44103;//FLOAT
    private static final int WHOLE_DAY_SLEEP_AMOUNT = 44105;//FLOAT
    private static final int DEEP_SLEEP_CONTINUITY = 44106;//FLOAT
    private static final int AWAKE_TIME = 44107;//FLOAT
    private static final int BED_TIME = 44201;//LONG
    private static final int RISE_TIME = 44202;//LONG
    private static final int SLEEP_SCORE = 44203;//LONG
    private static final int NIGHT_SLEEP_AMOUNT = 44209;//FLOAT

    private final Application application;

    public SleepDataRepository(Application application) {
        this.application = application;
    }

    /**
     * Method with default parameters, for description see {@link SleepDataRepository#getLatestSleepData(Date, Date, ConsumerCompat, ConsumerCompat)}
     *
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     */
    public void getLatestSleepData(@NonNull ConsumerCompat<SleepData> consumer) {
        getLatestSleepData(new Date(0), new Date(), consumer);
    }

    /**
     * Method with default parameters, for description see {@link SleepDataRepository#getLatestSleepData(Date, Date, ConsumerCompat, ConsumerCompat)}
     *
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     * @param onError  the callback invoked when an error occurs. Called on a background thread.
     */
    public void getLatestSleepData(@NonNull ConsumerCompat<SleepData> consumer, ConsumerCompat<HiError> onError) {
        getLatestSleepData(new Date(0), new Date(), consumer, onError);
    }

    /**
     * Method with default parameters, for description see {@link SleepDataRepository#getLatestSleepData(Date, Date, ConsumerCompat, ConsumerCompat)}
     *
     * @param since    the start date for the range searched for the data.
     * @param till     the end date for the range searched for the data.
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     */
    public void getLatestSleepData(@NonNull Date since, @NonNull Date till, @NonNull ConsumerCompat<SleepData> consumer) {
        getLatestSleepData(since, till, consumer, null);
    }

    /**
     * This method is used to retrieve last sleep data recorded in give time frame or null if there is none
     *
     * @param since    the start date for the range searched for the data.
     * @param till     the end date for the range searched for the data.
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     * @param onError  the callback invoked when an error occurs. Called on a background thread.
     */
    public void getLatestSleepData(@NonNull Date since, @NonNull Date till, @NonNull ConsumerCompat<SleepData> consumer, ConsumerCompat<HiError> onError) {
        getSleepData(since, till, result -> {
            SleepData latest = null;
            if (result.size() > 0) latest = result.get(result.size() - 1);
            consumer.accept(latest);
        }, onError);
    }

    /**
     * Method with default parameters, for description see {@link SleepDataRepository#getSleepData(Date, Date, ConsumerCompat, ConsumerCompat, int)}
     *
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     */
    public void getSleepData(@NonNull ConsumerCompat<List<SleepData>> consumer) {
        getSleepData(new Date(0), new Date(), consumer);
    }

    /**
     * Method with default parameters, for description see {@link SleepDataRepository#getSleepData(Date, Date, ConsumerCompat, ConsumerCompat, int)}
     *
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     * @param onError  the callback invoked when an error occurs. Called on a background thread.
     */
    public void getSleepData(@NonNull ConsumerCompat<List<SleepData>> consumer, ConsumerCompat<HiError> onError) {
        getSleepData(new Date(0), new Date(), consumer, onError);
    }

    /**
     * Method with default parameters, for description see {@link SleepDataRepository#getSleepData(Date, Date, ConsumerCompat, ConsumerCompat, int)}
     *
     * @param since    the start date for the range searched for the data.
     * @param till     the end date for the range searched for the data.
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     */
    public void getSleepData(@NonNull Date since, @NonNull Date till, @NonNull ConsumerCompat<List<SleepData>> consumer) {
        getSleepData(since, till, consumer, null);
    }

    /**
     * Method with default parameters, for description see {@link SleepDataRepository#getSleepData(Date, Date, ConsumerCompat, ConsumerCompat, int)}
     *
     * @param since    the start date for the range searched for the data.
     * @param till     the end date for the range searched for the data.
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     * @param onError  the callback invoked when an error occurs. Called on a background thread.
     */
    public void getSleepData(@NonNull Date since, @NonNull Date till, @NonNull ConsumerCompat<List<SleepData>> consumer, ConsumerCompat<HiError> onError) {
        getSleepData(since, till, consumer, onError, DEFAULT_TIMEOUT);
    }

    /**
     * This method is used to retrieve history of sleep data in given time frame.
     *
     * @param since    the start date for the range searched for the data.
     * @param till     the end date for the range searched for the data.
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     * @param onError  the callback invoked when an error occurs. Called on a background thread.
     * @param timeout  currently unused by SDK, it is recommended to pass 0 until SDK will start to support it
     */
    public void getSleepData(@NonNull Date since, @NonNull Date till, @NonNull ConsumerCompat<List<SleepData>> consumer, ConsumerCompat<HiError> onError, int timeout) {
        long endTime = till.getTime();
        long startTime = since.getTime();
        HiHealthDataQuery query = new HiHealthDataQuery(HiHealthSetType.DATA_SET_CORE_SLEEP, startTime, endTime, new HiHealthDataQueryOption());
        HiHealthDataStore.execQuery(application, query, timeout, (resultCode, data) -> {
            if (resultCode != HiHealthError.SUCCESS) {
                Log.e(LOGS_TAG, "SleepDataRepository.getSleepData() unexpected result code");
                if (onError != null) {
                    onError.accept(new HiError(resultCode, HiError.HiErrorCause.UNEXPECTED_RESULT_CODE));
                }
                return;
            }
            if (data == null) {
                Log.e(LOGS_TAG, "SleepDataRepository.getSleepData() data is null");
                if (onError != null) {
                    onError.accept(new HiError(resultCode, HiError.HiErrorCause.DATA_OBJECT_WAS_NULL));
                }
                return;
            }
            try {
                List<HiHealthSetData> hiHealthSetData = (List<HiHealthSetData>) data;
                List<SleepData> sleepDataList = new ArrayList<>();
                for (HiHealthSetData setData : hiHealthSetData) {
                    Map map = setData.getMap();// Map<Integer,Float||Long>
                    float remSleep = (float) map.get(REM_SLEEP);
                    float deepSleep = (float) map.get(DEEP_SLEEP);
                    float lightSleep = (float) map.get(LIGHT_SLEEP);

                    float wholeDaySleepAmount = (float) map.get(WHOLE_DAY_SLEEP_AMOUNT);
                    float nightSleepAmount = (float) map.get(NIGHT_SLEEP_AMOUNT);

                    float deepSleepContinuity = (float) map.get(DEEP_SLEEP_CONTINUITY);
                    float awakeTime = (float) map.get(AWAKE_TIME);

                    long bedTime = (long) map.get(BED_TIME);
                    long riseTime = (long) map.get(RISE_TIME);

                    long sleepScore = (long) map.get(SLEEP_SCORE);
                    SleepData sleepData = new SleepData(
                            remSleep, deepSleep, lightSleep,
                            wholeDaySleepAmount, nightSleepAmount,
                            deepSleepContinuity, awakeTime,
                            bedTime, riseTime,
                            sleepScore
                    );
                    sleepDataList.add(sleepData);
                }
                Log.i(LOGS_TAG, "SleepDataRepository.getSleepData() sleepData is " + sleepDataList);
                consumer.accept(sleepDataList);
            } catch (ClassCastException e) {
                Log.e(LOGS_TAG, "SleepDataRepository.getSleepData() could not cast data to proper type. ", e);
                if (onError != null) {
                    onError.accept(new HiError(resultCode, HiError.HiErrorCause.UNEXPECTED_DATA_TYPE_RETURNED));
                }
            }
        });
    }
}
