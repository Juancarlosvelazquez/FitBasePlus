package com.code.fitbase.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.code.fitbase.model.WeightData;
import com.code.fitbase.util.ConsumerCompat;
import com.code.fitbase.util.HiError;
import com.huawei.hihealth.error.HiHealthError;
import com.huawei.hihealthkit.HiHealthDataQuery;
import com.huawei.hihealthkit.HiHealthDataQueryOption;
import com.huawei.hihealthkit.auth.HiHealthOpenPermissionType;
import com.huawei.hihealthkit.data.HiHealthData;
import com.huawei.hihealthkit.data.HiHealthSetData;
import com.huawei.hihealthkit.data.store.HiHealthDataStore;
import com.huawei.hihealthkit.data.type.HiHealthPointType;
import com.huawei.hihealthkit.data.type.HiHealthSetType;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.code.fitbase.util.Constants.DEFAULT_TIMEOUT;
import static com.code.fitbase.util.Constants.LOGS_TAG;

/**
 * Provides weight-related data, like weight, BMI, body fat level, muscle mass etc.
 * Allows both for read and write operations.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class WeightDataRepository {
    private static final int DATA_POINT_WEIGHT_MISSING_CONSTANT_SKELETAL_MUSCLE_MASS = 2053;
    private final Application application;

    public WeightDataRepository(Application application) {
        this.application = application;
    }

    /**
     * Method with default parameters, for description see {@link WeightDataRepository#getLatestWeightData(Date, Date, ConsumerCompat, ConsumerCompat)}.
     */
    public void getLatestWeightData(@NonNull ConsumerCompat<WeightData> consumer) {
        getLatestWeightData(consumer, null);
    }

    /**
     * Method with default parameters, for description see {@link WeightDataRepository#getLatestWeightData(Date, Date, ConsumerCompat, ConsumerCompat)}.
     */
    public void getLatestWeightData(@NonNull ConsumerCompat<WeightData> consumer, ConsumerCompat<HiError> onError) {
        getLatestWeightData(new Date(0), new Date(), consumer, onError);
    }

    /**
     * Method with default parameters, for description see {@link WeightDataRepository#getLatestWeightData(Date, Date, ConsumerCompat, ConsumerCompat)}.
     */
    public void getLatestWeightData(@NonNull Date since, @NonNull Date till, @NonNull ConsumerCompat<WeightData> consumer) {
        getLatestWeightData(since, till, consumer, null);
    }

    /**
     * Initiates reading the latest saved weight-related data and invokes a callback provided
     * when the data is obtained successfully.
     *
     * @param since    the start date for the range searched for the data.
     * @param till     the end date for the range searched for the data.
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     * @param onError  the callback invoked when an error occurs. Called on a background thread.
     */
    public void getLatestWeightData(@NonNull Date since, @NonNull Date till, @NonNull ConsumerCompat<WeightData> consumer, ConsumerCompat<HiError> onError) {
        getWeightData(since, till, result -> {
            WeightData latest = null;
            if (result.size() > 0) latest = result.get(result.size() - 1);
            consumer.accept(latest);
        }, onError);
    }

    /**
     * Method with default parameters, for description see {@link WeightDataRepository#getWeightData(Date, Date, ConsumerCompat, ConsumerCompat, int)}.
     */
    public void getWeightData(@NonNull ConsumerCompat<List<WeightData>> consumer) {
        getWeightData(consumer, null);
    }

    /**
     * Method with default parameters, for description see {@link WeightDataRepository#getWeightData(Date, Date, ConsumerCompat, ConsumerCompat, int)}.
     */
    public void getWeightData(@NonNull ConsumerCompat<List<WeightData>> consumer, ConsumerCompat<HiError> onError) {
        getWeightData(new Date(0), new Date(), consumer, onError);
    }

    /**
     * Method with default parameters, for description see {@link WeightDataRepository#getWeightData(Date, Date, ConsumerCompat, ConsumerCompat, int)}.
     */
    public void getWeightData(@NonNull Date since, @NonNull Date till, @NonNull ConsumerCompat<List<WeightData>> consumer) {
        getWeightData(since, till, consumer, null);
    }

    /**
     * Method with default parameters, for description see {@link WeightDataRepository#getWeightData(Date, Date, ConsumerCompat, ConsumerCompat, int)}.
     */
    public void getWeightData(@NonNull Date since, @NonNull Date till, @NonNull ConsumerCompat<List<WeightData>> consumer, ConsumerCompat<HiError> onError) {
        getWeightData(since, till, consumer, onError, DEFAULT_TIMEOUT);
    }

    /**
     * Obtains a {@link List} of {@link WeightData} objects representing saved samples, like the ones
     * saved with the {@link WeightDataRepository#saveWeightData(WeightData, ConsumerCompat)}.
     *
     * @param since    the start date for the range searched for the data.
     * @param till     the end date for the range searched for the data.
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     * @param onError  the callback invoked when an error occurs. Called on a background thread.
     * @param timeout  currently unused by SDK, it is recommended to pass 0 until SDK will start to support it
     */
    public void getWeightData(@NonNull Date since, @NonNull Date till, @NonNull ConsumerCompat<List<WeightData>> consumer, ConsumerCompat<HiError> onError, int timeout) {
        long endTime = till.getTime();
        long startTime = since.getTime();

        HiHealthDataQuery hiHealthDataQuery = new HiHealthDataQuery(HiHealthSetType.DATA_SET_WEIGHT_EX, startTime,
                endTime, new HiHealthDataQueryOption());
        HiHealthDataStore.execQuery(application, hiHealthDataQuery, timeout, (resultCode, data) -> {
            Log.i(LOGS_TAG, "WeightDataRepository.getWeightData() resultCode is " + resultCode);
            if (resultCode != HiHealthError.SUCCESS) {
                Log.e(LOGS_TAG, "WeightDataRepository.getWeightData() unexpected result code");
                if (onError != null) {
                    onError.accept(new HiError(resultCode, HiError.HiErrorCause.UNEXPECTED_RESULT_CODE));
                }
                return;
            }
            if (data == null) {
                Log.e(LOGS_TAG, "WeightDataRepository.getWeightData() data is null");
                if (onError != null) {
                    onError.accept(new HiError(resultCode, HiError.HiErrorCause.DATA_OBJECT_WAS_NULL));
                }
                return;
            }
            try {
                ArrayList<HiHealthSetData> dataList = (ArrayList<HiHealthSetData>) data;

                List<WeightData> weightDataList = new ArrayList<>();
                for (HiHealthSetData log : dataList) {
                    Map<Integer, Object> map = log.getMap();

                    WeightData weightData = new WeightData(
                            (double) map.get(HiHealthPointType.DATA_POINT_WEIGHT),
                            (double) map.get(HiHealthPointType.DATA_POINT_WEIGHT_BMI),
                            (double) map.get(HiHealthPointType.DATA_POINT_WEIGHT_MUSCLES),
                            (double) map.get(HiHealthPointType.DATA_POINT_WEIGHT_BMR),
                            (double) map.get(HiHealthPointType.DATA_POINT_WEIGHT_MOISTURE),
                            (double) map.get(HiHealthPointType.DATA_POINT_WEIGHT_FATLEVEL),
                            (double) map.get(HiHealthPointType.DATA_POINT_WEIGHT_BONE_MINERAL),
                            (double) map.get(HiHealthPointType.DATA_POINT_WEIGHT_PROTEIN),
                            (double) map.get(HiHealthPointType.DATA_POINT_WEIGHT_BODYSCORE),
                            (double) map.get(HiHealthPointType.DATA_POINT_WEIGHT_BODYAGE),
                            (double) map.get(HiHealthPointType.DATA_POINT_WEIGHT_BODYFAT),
                            (double) map.get(HiHealthPointType.DATA_POINT_WEIGHT_IMPEDANCE),
                            (double) map.get(HiHealthPointType.DATA_POINT_WEIGHT_MOISTURERATE),
                            (double) map.get(DATA_POINT_WEIGHT_MISSING_CONSTANT_SKELETAL_MUSCLE_MASS)
                    );

                    weightDataList.add(weightData);
                }
                Log.i(LOGS_TAG, "WeightDataRepository.getWeightData() weight is " + weightDataList);
                consumer.accept(weightDataList);
            } catch (ClassCastException e) {
                Log.e(LOGS_TAG, "WeightDataRepository.getWeightData() could not cast data to proper type. ", e);
                if (onError != null) {
                    onError.accept(new HiError(resultCode, HiError.HiErrorCause.UNEXPECTED_DATA_TYPE_RETURNED));
                }
            }
        });
    }

    /**
     * Method with default parameters, for description see {@link WeightDataRepository#saveWeightData(WeightData, ConsumerCompat)}.
     */
    public void saveWeightData(@NonNull WeightData data) {
        saveWeightData(data, null);
    }

    /**
     * Saves weight-related data.
     *
     * @param data              the weight-related data to be saved.
     * @param errorCodeConsumer The callback with error code, 0 if successful. Called on a background thread.
     */
    public void saveWeightData(@NonNull WeightData data, ConsumerCompat<Integer> errorCodeConsumer) {
        Map<Integer, Double> map = new HashMap<>();
        map.put(HiHealthPointType.DATA_POINT_WEIGHT, data.getWeight());
        map.put(HiHealthPointType.DATA_POINT_WEIGHT_BMI, data.getBmi());
        map.put(HiHealthPointType.DATA_POINT_WEIGHT_MUSCLES, data.getMuscleMass());
        map.put(HiHealthPointType.DATA_POINT_WEIGHT_BMR, data.getBasalMetabolicRate());
        map.put(HiHealthPointType.DATA_POINT_WEIGHT_MOISTURE, data.getWaterContent());
        map.put(HiHealthPointType.DATA_POINT_WEIGHT_FATLEVEL, data.getBodyFatRate());
        map.put(HiHealthPointType.DATA_POINT_WEIGHT_BONE_MINERAL, data.getBoneMineralContent());
        map.put(HiHealthPointType.DATA_POINT_WEIGHT_PROTEIN, data.getProtein());
        map.put(HiHealthPointType.DATA_POINT_WEIGHT_BODYSCORE, 9D);
        map.put(HiHealthPointType.DATA_POINT_WEIGHT_BODYAGE, 10D);
        map.put(HiHealthPointType.DATA_POINT_WEIGHT_BODYFAT, 11D);
        map.put(HiHealthPointType.DATA_POINT_WEIGHT_IMPEDANCE, 12D);
        map.put(HiHealthPointType.DATA_POINT_WEIGHT_MOISTURERATE, 13D);
        final long endTime = System.currentTimeMillis();
        List<HiHealthData> hiHealthDataList = new ArrayList<>();
        HiHealthSetData hiHealthSetData = new HiHealthSetData(
                HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_WRITE_DATA_SET_WEIGHT, map, endTime, endTime);
        hiHealthDataList.add(hiHealthSetData);
        HiHealthDataStore.saveSamples(application, hiHealthDataList, (resultCode, dataObject) -> {
            Log.i(LOGS_TAG, "WeightDataRepository.saveWeightData() resultCode is " + resultCode);
            if (resultCode != HiHealthError.SUCCESS) {
                Log.e(LOGS_TAG, "WeightDataRepository.saveWeightData() failed to save weightData, resultCode is " + resultCode);
            }
            if (errorCodeConsumer != null) errorCodeConsumer.accept(resultCode);
        });
    }

    /**
     * Method with default parameters, for description see {@link WeightDataRepository#deleteWeightData(Date, Date, ConsumerCompat)}.
     */
    public void deleteWeightData(Date since, Date till) {
        deleteWeightData(since, till, null);
    }

    /**
     * Deletes weight data withing provided date-range.
     *
     * Note: while the method can delete weight saved with methods like {@link WeightDataRepository#saveWeightData(WeightData)}
     * it will not be able to delete data saved directly via the Huawei Health app.
     *
     * @param since             the start date for the range searched for the data.
     * @param till              the end date for the range searched for the data.
     * @param errorCodeConsumer The callback with error code, 0 if successful. Called on a background thread.
     */
    public void deleteWeightData(Date since, Date till, ConsumerCompat<Integer> errorCodeConsumer) {
        long startTime = since.getTime();
        long endTime = till.getTime();
        HiHealthSetData hiHealthSetData = new HiHealthSetData(HiHealthSetType.DATA_SET_WEIGHT_EX, new HashMap(), startTime, endTime);
        HiHealthDataStore.deleteSample(application, hiHealthSetData, (resultCode, data) -> {
            Log.i(LOGS_TAG, "WeightDataRepository.deleteWeightData() resultCode is " + resultCode);
            Log.i(LOGS_TAG, "WeightDataRepository.deleteWeightData() data is " + data);
            if (resultCode != HiHealthError.SUCCESS) {
                Log.e(LOGS_TAG, "WeightDataRepository.deleteWeightData() failed to delete weightData, resultCode is " + resultCode);
            }
            if (errorCodeConsumer != null) errorCodeConsumer.accept(resultCode);
        });
    }
}
