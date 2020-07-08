package com.code.fitbase.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.code.fitbase.model.DailyCaloriesData;
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
 * Provides access to tracked calories burned in given time range
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class CaloriesRepository {
    private final Application application;

    public CaloriesRepository(Application application) {
        this.application = application;
    }

    /**
     * Method with default parameters, for description see {@link CaloriesRepository#getCaloriesData(Date, Date, ConsumerCompat, ConsumerCompat, int)}
     *
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     */
    public void getCaloriesData(@NonNull ConsumerCompat<List<DailyCaloriesData>> consumer) {
        getCaloriesData(consumer, null);
    }

    /**
     * Method with default parameters, for description see {@link CaloriesRepository#getCaloriesData(Date, Date, ConsumerCompat, ConsumerCompat, int)}
     *
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     * @param onError  the callback invoked when an error occurs. Called on a background thread.
     */
    public void getCaloriesData(@NonNull ConsumerCompat<List<DailyCaloriesData>> consumer, ConsumerCompat<HiError> onError) {
        getCaloriesData(new Date(0), new Date(), consumer, onError);
    }

    /**
     * Method with default parameters, for description see {@link CaloriesRepository#getCaloriesData(Date, Date, ConsumerCompat, ConsumerCompat, int)}
     *
     * @param since    the start date for the range searched for the data.
     * @param till     the end date for the range searched for the data.
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     */
    public void getCaloriesData(@NonNull Date since, @NonNull Date till, @NonNull ConsumerCompat<List<DailyCaloriesData>> consumer) {
        getCaloriesData(since, till, consumer, null);
    }

    /**
     * Method with default parameters, for description see {@link CaloriesRepository#getCaloriesData(Date, Date, ConsumerCompat, ConsumerCompat, int)}
     *
     * @param since    the start date for the range searched for the data.
     * @param till     the end date for the range searched for the data.
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     * @param onError  the callback invoked when an error occurs. Called on a background thread.
     */
    public void getCaloriesData(@NonNull Date since, @NonNull Date till, @NonNull ConsumerCompat<List<DailyCaloriesData>> consumer, ConsumerCompat<HiError> onError) {
        getCaloriesData(since, till, consumer, onError, DEFAULT_TIMEOUT);
    }

    /**
     * Obtains a {@link List} of daily calories counts along with time stamps of the days the calories counts
     * were recorded.
     *
     * @param since    the start date for the range searched for the data.
     * @param till     the end date for the range searched for the data.
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     * @param onError  the callback invoked when an error occurs. Called on a background thread.
     * @param timeout  currently unused by SDK, it is recommended to pass 0 until SDK will start to support it
     */
    public void getCaloriesData(@NonNull Date since, @NonNull Date till, @NonNull ConsumerCompat<List<DailyCaloriesData>> consumer, ConsumerCompat<HiError> onError, int timeout) {
        long endTime = till.getTime();
        long startTime = since.getTime();

        // Statistic data returned as an ArrayList where each element represents the value of one day
        HiHealthDataQuery hiHealthDataQuery = new HiHealthDataQuery(HiHealthPointType.DATA_POINT_CALORIES_SUM, startTime,
                endTime, new HiHealthDataQueryOption());
        Log.i(LOGS_TAG, "CaloriesRepository.getCaloriesData() sampleType = " + hiHealthDataQuery.getSampleType());
        HiHealthDataStore.execQuery(application, hiHealthDataQuery, timeout, (resultCode, data) -> {
            Log.i(LOGS_TAG, "CaloriesRepository.getCaloriesData() resultCode is " + resultCode);
            Log.i(LOGS_TAG, "CaloriesRepository.getCaloriesData() data is " + data);
            if (resultCode != HiHealthPointType.DATA_POINT_CALORIES_SUM) {
                Log.e(LOGS_TAG, "CaloriesRepository.getCaloriesData() unexpected result code");
                if (onError != null) {
                    onError.accept(new HiError(resultCode, HiError.HiErrorCause.UNEXPECTED_RESULT_CODE));
                }
                return;
            }
            if (data == null) {
                Log.e(LOGS_TAG, "CaloriesRepository.getCaloriesData() data is null");
                if (onError != null) {
                    onError.accept(new HiError(resultCode, HiError.HiErrorCause.DATA_OBJECT_WAS_NULL));
                }
                return;
            }
            try {
                ArrayList dataList = (ArrayList) data;
                List<DailyCaloriesData> dailyData = new ArrayList<>();

                for (Object obj : dataList) {
                    HiHealthPointData datum = ((HiHealthPointData) obj);
                    int calories = datum.getValue();
                    long timeStamp = datum.getStartTime();
                    dailyData.add(new DailyCaloriesData(new Date(timeStamp), calories));
                }

                consumer.accept(dailyData);
                Log.i(LOGS_TAG, "CaloriesRepository.getCaloriesData() resultCode is " + resultCode + " data: " + dataList);
            } catch (ClassCastException e) {
                Log.e(LOGS_TAG, "CaloriesRepository.getCaloriesData() could not cast data to proper type. ", e);
                if (onError != null) {
                    onError.accept(new HiError(resultCode, HiError.HiErrorCause.UNEXPECTED_DATA_TYPE_RETURNED));
                }
            }
        });
    }
}