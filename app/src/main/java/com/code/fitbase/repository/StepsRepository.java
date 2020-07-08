package com.code.fitbase.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.code.fitbase.model.DailyStepsData;
import com.code.fitbase.util.ConsumerCompat;
import com.code.fitbase.util.HiError;
import com.huawei.hihealthkit.HiHealthDataQuery;
import com.huawei.hihealthkit.HiHealthDataQueryOption;
import com.huawei.hihealthkit.data.HiHealthPointData;
import com.huawei.hihealthkit.data.store.HiHealthDataStore;
import com.huawei.hihealthkit.data.type.HiHealthPointType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.code.fitbase.util.Constants.DEFAULT_TIMEOUT;
import static com.code.fitbase.util.Constants.LOGS_TAG;

/**
 * Provides access to tracked steps data, like steps count in a given period, or a number of
 * days on which user had met their daily steps challenge's required level.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class StepsRepository {
    private final Application application;

    public StepsRepository(Application application) {
        this.application = application;
    }

    /**
     * Method with default parameters, for description see {@link StepsRepository#getStepsData(Date, Date, ConsumerCompat, ConsumerCompat, int)}.
     */
    public void getStepsData(@NonNull ConsumerCompat<List<DailyStepsData>> consumer) {
        getStepsData(new Date(0), new Date(), consumer);
    }

    /**
     * Method with default parameters, for description see {@link StepsRepository#getStepsData(Date, Date, ConsumerCompat, ConsumerCompat, int)}.
     */
    public void getStepsData(@NonNull ConsumerCompat<List<DailyStepsData>> consumer, ConsumerCompat<HiError> onError) {
        getStepsData(new Date(0), new Date(), consumer, onError);
    }

    /**
     * Method with default parameters, for description see {@link StepsRepository#getStepsData(Date, Date, ConsumerCompat, ConsumerCompat, int)}.
     */
    public void getStepsData(@NonNull Date since, @NonNull Date till, @NonNull ConsumerCompat<List<DailyStepsData>> consumer) {
        getStepsData(since, till, consumer, null);
    }

    /**
     * Method with default parameters, for description see {@link StepsRepository#getStepsData(Date, Date, ConsumerCompat, ConsumerCompat, int)}.
     */
    public void getStepsData(@NonNull Date since, @NonNull Date till, @NonNull ConsumerCompat<List<DailyStepsData>> consumer, ConsumerCompat<HiError> onError) {
        getStepsData(since, till, consumer, onError, DEFAULT_TIMEOUT);
    }

    /**
     * Obtains a {@link List} of daily steps counts along with time stamps of the days the steps counts
     * were recorded.
     *
     * @param since    the start date for the range searched for the data.
     * @param till     the end date for the range searched for the data.
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     * @param onError  the callback invoked when an error occurs. Called on a background thread.
     * @param timeout  currently unused by SDK, it is recommended to pass 0 until SDK will start to support it
     */
    public void getStepsData(@NonNull Date since, @NonNull Date till, @NonNull ConsumerCompat<List<DailyStepsData>> consumer, ConsumerCompat<HiError> onError, int timeout) {
        long endTime = till.getTime();
        long startTime = since.getTime();

        // Get HiHealthPointType, like steps, distance, calories, exercise intensity per day.
        // Statistic data returned as an ArrayList where each element represents the value of one day
        HiHealthDataQuery hiHealthDataQuery = new HiHealthDataQuery(HiHealthPointType.DATA_POINT_STEP_SUM, startTime,
                endTime, new HiHealthDataQueryOption());
        Log.i(LOGS_TAG, "StepsRepository.getStepsData() sampleType = " + hiHealthDataQuery.getSampleType());
        HiHealthDataStore.execQuery(application, hiHealthDataQuery, timeout, (resultCode, data) -> {
            Log.i(LOGS_TAG, "StepsRepository.getStepsData() resultCode is " + resultCode);
            Log.i(LOGS_TAG, "StepsRepository.getStepsData() data is " + data);
            if (resultCode != HiHealthPointType.DATA_POINT_STEP_SUM) {
                Log.e(LOGS_TAG, "StepsRepository.getStepsData() unexpected result code");
                if (onError != null) {
                    onError.accept(new HiError(resultCode, HiError.HiErrorCause.UNEXPECTED_RESULT_CODE));
                }
                return;
            }
            if (data == null) {
                Log.e(LOGS_TAG, "StepsRepository.getStepsData() data is null");
                if (onError != null) {
                    onError.accept(new HiError(resultCode, HiError.HiErrorCause.DATA_OBJECT_WAS_NULL));
                }
                return;
            }
            try {
                ArrayList dataList = (ArrayList) data;
                List<DailyStepsData> dailyData = new ArrayList<>();

                for (Object obj : dataList) {
                    HiHealthPointData datum = ((HiHealthPointData) obj);
                    int steps = datum.getValue();
                    long timeStamp = datum.getStartTime();
                    dailyData.add(new DailyStepsData(new Date(timeStamp), steps));
                }

                consumer.accept(dailyData);
                Log.i(LOGS_TAG, "StepsRepository.getStepsData() resultCode is " + resultCode + " data: " + dailyData);
            } catch (ClassCastException e) {
                Log.e(LOGS_TAG, "StepsRepository.getStepsData() could not cast data to proper type. ", e);
                if (onError != null) {
                    onError.accept(new HiError(resultCode, HiError.HiErrorCause.UNEXPECTED_DATA_TYPE_RETURNED));
                }
            }
        });
    }

    /**
     * Method with default parameters, for description see {@link StepsRepository#getStepsChallengeDaysCompleted(Date, Date, ConsumerCompat, ConsumerCompat)}.
     */
    public void getStepsChallengeDaysCompleted(@NonNull ConsumerCompat<Integer> consumer) {
        getStepsChallengeDaysCompleted(new Date(0), new Date(), consumer);
    }

    /**
     * Method with default parameters, for description see {@link StepsRepository#getStepsChallengeDaysCompleted(Date, Date, ConsumerCompat, ConsumerCompat)}.
     */
    public void getStepsChallengeDaysCompleted(@NonNull ConsumerCompat<Integer> consumer, ConsumerCompat<HiError> onError) {
        getStepsChallengeDaysCompleted(new Date(0), new Date(), consumer, onError);
    }

    /**
     * Method with default parameters, for description see {@link StepsRepository#getStepsChallengeDaysCompleted(Date, Date, ConsumerCompat, ConsumerCompat)}.
     */
    public void getStepsChallengeDaysCompleted(@NonNull Date since, @NonNull Date till, @NonNull ConsumerCompat<Integer> consumer) {
        getStepsChallengeDaysCompleted(since, till, consumer, null);
    }

    /**
     * Obtains the number of days on which user had met their daily steps challenge.
     *
     * @param since    the start date for the range searched for the data.
     * @param till     the end date for the range searched for the data.
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     * @param onError  the callback invoked when an error occurs. Called on a background thread.
     */
    public void getStepsChallengeDaysCompleted(@NonNull Date since, @NonNull Date till, @NonNull ConsumerCompat<Integer> consumer, ConsumerCompat<HiError> onError) {
        long endTime = till.getTime();
        long startTime = since.getTime();

        HiHealthDataQuery hiHealthDataQuery = new HiHealthDataQuery(HiHealthPointType.DATA_POINT_STEP_SUM, startTime,
                endTime, new HiHealthDataQueryOption());
        Log.i(LOGS_TAG, "StepsRepository.getStepsChallengeDaysData() sample type = " + hiHealthDataQuery.getSampleType());
        HiHealthDataStore.getCount(application, hiHealthDataQuery, (resultCode, data) -> {
            Log.i(LOGS_TAG, "StepsRepository.getStepsChallengeDaysData() resultCode is " + resultCode);
            Log.i(LOGS_TAG, "StepsRepository.getStepsChallengeDaysData() data is " + data);
            if (resultCode != HiHealthPointType.DATA_POINT_STEP_SUM) {
                Log.e(LOGS_TAG, "StepsRepository.getStepsData() unexpected result code");
                if (onError != null) {
                    onError.accept(new HiError(resultCode, HiError.HiErrorCause.UNEXPECTED_RESULT_CODE));
                }
                return;
            }
            if (data == null) {
                Log.e(LOGS_TAG, "StepsRepository.getStepsChallengeDaysData() data is null");
                if (onError != null) {
                    onError.accept(new HiError(resultCode, HiError.HiErrorCause.DATA_OBJECT_WAS_NULL));
                }
                return;
            }
            try {
                int daysCompleted = (int) data;
                consumer.accept(daysCompleted);
            } catch (ClassCastException e) {
                Log.e(LOGS_TAG, "SleepDataRepository.getSleepData() could not cast data to proper type. ", e);
                if (onError != null) {
                    onError.accept(new HiError(resultCode, HiError.HiErrorCause.UNEXPECTED_DATA_TYPE_RETURNED));
                }
            }
        });
    }
}
