package com.code.fitbase.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.code.fitbase.model.CyclingData;
import com.code.fitbase.model.RunData;
import com.code.fitbase.model.WalkData;
import com.code.fitbase.util.ConsumerCompat;
import com.code.fitbase.util.HiError;
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
 * Provides way to access history of activities such as walk, run and cycling (ride) in give time frame.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class SingleActivitiesRepository {
    private final String DATE = "date";
    private final String AVERAGE_HEART_RATE = "average_heart_rate";
    private final String AVERAGE_STEP_RATE = "average_step_rate";
    private final String TOTAL_CALORIES = "total_calories";
    private final String STEP_DISTANCE = "step_distance";
    private final String TOTAL_ALTITUDE = "total_altitude";
    private final String TOTAL_DISTANCE = "total_distance";
    private final String STEP = "step";
    private final String TOTAL_DESCENT = "total_descent";
    private final String TOTAL_TIME = "total_time";
    private final String AVERAGE_PACE = "average_pace";
    private final String AVERAGE_SPEED = "average_speed";

    private final Application application;

    public SingleActivitiesRepository(Application application) {
        this.application = application;
    }

    /**
     * Method with default parameters, for description see {@link SingleActivitiesRepository#getWalkData(Date, Date, ConsumerCompat, ConsumerCompat, int)}
     *
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     */
    public void getWalkData(@NonNull ConsumerCompat<List<WalkData>> consumer) {
        getWalkData(new Date(0), new Date(), consumer);
    }

    /**
     * Method with default parameters, for description see {@link SingleActivitiesRepository#getWalkData(Date, Date, ConsumerCompat, ConsumerCompat, int)}
     *
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     * @param onError  the callback invoked when an error occurs. Called on a background thread.
     */
    public void getWalkData(@NonNull ConsumerCompat<List<WalkData>> consumer, ConsumerCompat<HiError> onError) {
        getWalkData(new Date(0), new Date(), consumer, onError);
    }

    /**
     * Method with default parameters, for description see {@link SingleActivitiesRepository#getWalkData(Date, Date, ConsumerCompat, ConsumerCompat, int)}
     *
     * @param since    the start date for the range searched for the data.
     * @param till     the end date for the range searched for the data.
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     */
    public void getWalkData(@NonNull Date since, @NonNull Date till, @NonNull ConsumerCompat<List<WalkData>> consumer) {
        getWalkData(since, till, consumer, null);
    }

    /**
     * Method with default parameters, for description see {@link SingleActivitiesRepository#getWalkData(Date, Date, ConsumerCompat, ConsumerCompat, int)}
     *
     * @param since    the start date for the range searched for the data.
     * @param till     the end date for the range searched for the data.
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     * @param onError  the callback invoked when an error occurs. Called on a background thread.
     */
    public void getWalkData(@NonNull Date since, @NonNull Date till, @NonNull ConsumerCompat<List<WalkData>> consumer, ConsumerCompat<HiError> onError) {
        getWalkData(since, till, consumer, onError, DEFAULT_TIMEOUT);
    }


    /**
     * This method is used to retrieve history of walking events in given time frame.
     *
     * @param since    the start date for the range searched for the data.
     * @param till     the end date for the range searched for the data.
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     * @param onError  the callback invoked when an error occurs. Called on a background thread.
     * @param timeout  currently unused by SDK, it is recommended to pass 0 until SDK will start to support it
     */
    public void getWalkData(@NonNull Date since, @NonNull Date till, @NonNull ConsumerCompat<List<WalkData>> consumer, ConsumerCompat<HiError> onError, int timeout) {
        long endTime = till.getTime();
        long startTime = since.getTime();
        HiHealthDataQuery query = new HiHealthDataQuery(HiHealthSetType.DATA_SET_WALK_METADATA, startTime, endTime, new HiHealthDataQueryOption());

        HiHealthDataStore.execQuery(application, query, timeout, (resultCode, data) -> {
            Log.i(LOGS_TAG, "SingleActivitiesRepository.getWalkData() resultCode is " + resultCode);
            if (resultCode != HiHealthSetType.DATA_SET_WALK_METADATA) {
                Log.e(LOGS_TAG, "SingleActivitiesRepository.getWalkData() unexpected result code");
                if (onError != null) {
                    onError.accept(new HiError(resultCode, HiError.HiErrorCause.UNEXPECTED_RESULT_CODE));
                }
                return;
            }
            if (data == null) {
                Log.e(LOGS_TAG, "SingleActivitiesRepository.getWalkData() data is null");
                if (onError != null) {
                    onError.accept(new HiError(resultCode, HiError.HiErrorCause.DATA_OBJECT_WAS_NULL));
                }
                return;
            }
            try {
                List<HiHealthSetData> hiHealthSetData = (List<HiHealthSetData>) data;
                List<WalkData> out = getWalkDataFromMap(hiHealthSetData);
                consumer.accept(out);
            } catch (ClassCastException e) {
                Log.e(LOGS_TAG, "SingleActivitiesRepository.getWalkData() could not cast data to proper type. ", e);
                if (onError != null) {
                    onError.accept(new HiError(resultCode, HiError.HiErrorCause.UNEXPECTED_DATA_TYPE_RETURNED));
                }
            }
        });
    }

    /**
     * Method with default parameters, for description see {@link SingleActivitiesRepository#getRunData(Date, Date, ConsumerCompat, ConsumerCompat, int)}
     *
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     */
    public void getRunData(@NonNull ConsumerCompat<List<RunData>> consumer) {
        getRunData(new Date(0), new Date(), consumer);
    }

    /**
     * Method with default parameters, for description see {@link SingleActivitiesRepository#getRunData(Date, Date, ConsumerCompat, ConsumerCompat, int)}
     *
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     * @param onError  the callback invoked when an error occurs. Called on a background thread.
     */
    public void getRunData(@NonNull ConsumerCompat<List<RunData>> consumer, ConsumerCompat<HiError> onError) {
        getRunData(new Date(0), new Date(), consumer, onError);
    }

    /**
     * Method with default parameters, for description see {@link SingleActivitiesRepository#getRunData(Date, Date, ConsumerCompat, ConsumerCompat, int)}
     *
     * @param since    the start date for the range searched for the data.
     * @param till     the end date for the range searched for the data.
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     */
    public void getRunData(@NonNull Date since, @NonNull Date till, @NonNull ConsumerCompat<List<RunData>> consumer) {
        getRunData(since, till, consumer, null);
    }

    /**
     * Method with default parameters, for description see {@link SingleActivitiesRepository#getRunData(Date, Date, ConsumerCompat, ConsumerCompat, int)}
     *
     * @param since    the start date for the range searched for the data.
     * @param till     the end date for the range searched for the data.
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     * @param onError  the callback invoked when an error occurs. Called on a background thread.
     */
    public void getRunData(@NonNull Date since, @NonNull Date till, @NonNull ConsumerCompat<List<RunData>> consumer, ConsumerCompat<HiError> onError) {
        getRunData(since, till, consumer, onError, DEFAULT_TIMEOUT);
    }

    /**
     * This method is used to retrieve history of running events in given time frame.
     *
     * @param since    the start date for the range searched for the data.
     * @param till     the end date for the range searched for the data.
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     * @param onError  the callback invoked when an error occurs. Called on a background thread.
     * @param timeout  currently unused by SDK, it is recommended to pass 0 until SDK will start to support it
     */
    public void getRunData(@NonNull Date since, @NonNull Date till, @NonNull ConsumerCompat<List<RunData>> consumer, ConsumerCompat<HiError> onError, int timeout) {
        long endTime = till.getTime();
        long startTime = since.getTime();
        HiHealthDataQuery query = new HiHealthDataQuery(HiHealthSetType.DATA_SET_RUN_METADATA, startTime, endTime, new HiHealthDataQueryOption());

        HiHealthDataStore.execQuery(application, query, timeout, (resultCode, data) -> {
            Log.i(LOGS_TAG, "SingleActivitiesRepository.getRunData() resultCode is " + resultCode);
            if (resultCode != HiHealthSetType.DATA_SET_RUN_METADATA) {
                Log.e(LOGS_TAG, "SingleActivitiesRepository.getRunData() unexpected result code");
                if (onError != null) {
                    onError.accept(new HiError(resultCode, HiError.HiErrorCause.UNEXPECTED_RESULT_CODE));
                }
                return;
            }
            if (data == null) {
                Log.e(LOGS_TAG, "SingleActivitiesRepository.getRunData() data is null");
                if (onError != null) {
                    onError.accept(new HiError(resultCode, HiError.HiErrorCause.DATA_OBJECT_WAS_NULL));
                }
                return;
            }
            try {
                List<HiHealthSetData> hiHealthSetData = (List<HiHealthSetData>) data;
                List<RunData> out = getRunDataFromMap(hiHealthSetData);
                consumer.accept(out);
            } catch (ClassCastException e) {
                Log.e(LOGS_TAG, "SingleActivitiesRepository.getRunData() could not cast data to proper type. ", e);
                if (onError != null) {
                    onError.accept(new HiError(resultCode, HiError.HiErrorCause.UNEXPECTED_DATA_TYPE_RETURNED));
                }
            }
        });
    }

    /**
     * Method with default parameters, for description see {@link SingleActivitiesRepository#getCyclingData(Date, Date, ConsumerCompat, ConsumerCompat, int)}
     *
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     */
    public void getCyclingData(@NonNull ConsumerCompat<List<CyclingData>> consumer) {
        getCyclingData(new Date(0), new Date(), consumer);
    }

    /**
     * Method with default parameters, for description see {@link SingleActivitiesRepository#getCyclingData(Date, Date, ConsumerCompat, ConsumerCompat, int)}
     *
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     * @param onError  the callback invoked when an error occurs. Called on a background thread.
     */
    public void getCyclingData(@NonNull ConsumerCompat<List<CyclingData>> consumer, ConsumerCompat<HiError> onError) {
        getCyclingData(new Date(0), new Date(), consumer, onError);
    }

    /**
     * Method with default parameters, for description see {@link SingleActivitiesRepository#getCyclingData(Date, Date, ConsumerCompat, ConsumerCompat, int)}
     *
     * @param since    the start date for the range searched for the data.
     * @param till     the end date for the range searched for the data.
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     */
    public void getCyclingData(@NonNull Date since, @NonNull Date till, @NonNull ConsumerCompat<List<CyclingData>> consumer) {
        getCyclingData(since, till, consumer, null);
    }

    /**
     * Method with default parameters, for description see {@link SingleActivitiesRepository#getCyclingData(Date, Date, ConsumerCompat, ConsumerCompat, int)}
     *
     * @param since    the start date for the range searched for the data.
     * @param till     the end date for the range searched for the data.
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     * @param onError  the callback invoked when an error occurs. Called on a background thread.
     */
    public void getCyclingData(@NonNull Date since, @NonNull Date till, @NonNull ConsumerCompat<List<CyclingData>> consumer, ConsumerCompat<HiError> onError) {
        getCyclingData(since, till, consumer, onError, DEFAULT_TIMEOUT);
    }

    /**
     * This method is used to retrieve history of cycling events in given time frame.
     *
     * @param since    the start date for the range searched for the data.
     * @param till     the end date for the range searched for the data.
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     * @param onError  the callback invoked when an error occurs. Called on a background thread.
     * @param timeout  currently unused by SDK, it is recommended to pass 0 until SDK will start to support it
     */
    public void getCyclingData(@NonNull Date since, @NonNull Date till, @NonNull ConsumerCompat<List<CyclingData>> consumer, ConsumerCompat<HiError> onError, int timeout) {
        long endTime = till.getTime();
        long startTime = since.getTime();
        HiHealthDataQuery query = new HiHealthDataQuery(HiHealthSetType.DATA_SET_RIDE_METADATA, startTime, endTime, new HiHealthDataQueryOption());

        HiHealthDataStore.execQuery(application, query, timeout, (resultCode, data) -> {
            Log.i(LOGS_TAG, "SingleActivitiesRepository.getCyclingData() resultCode is " + resultCode);
            if (resultCode != HiHealthSetType.DATA_SET_RIDE_METADATA) {
                Log.e(LOGS_TAG, "SingleActivitiesRepository.getCyclingData() unexpected result code");
                if (onError != null) {
                    onError.accept(new HiError(resultCode, HiError.HiErrorCause.UNEXPECTED_RESULT_CODE));
                }
                return;
            }
            if (data == null) {
                Log.e(LOGS_TAG, "SingleActivitiesRepository.getCyclingData() data is null");
                if (onError != null) {
                    onError.accept(new HiError(resultCode, HiError.HiErrorCause.DATA_OBJECT_WAS_NULL));
                }
                return;
            }
            try {
                List<HiHealthSetData> hiHealthSetData = (List<HiHealthSetData>) data;
                List<CyclingData> out = getCyclingDataFromMap(hiHealthSetData);
                consumer.accept(out);
            } catch (ClassCastException e) {
                Log.e(LOGS_TAG, "SingleActivitiesRepository.getCyclingData() could not cast data to proper type. ", e);
                if (onError != null) {
                    onError.accept(new HiError(resultCode, HiError.HiErrorCause.UNEXPECTED_DATA_TYPE_RETURNED));
                }
            }
        });
    }

    private List<WalkData> getWalkDataFromMap(List<HiHealthSetData> data) throws ClassCastException {
        List<WalkData> out = new ArrayList<>();
        for (HiHealthSetData datum : data) {
            Map map = datum.getMap();//Map<String,Integer||Long||Float||Double>
            int date = (int) map.get(DATE);
            int averageHearRate = (int) map.get(AVERAGE_HEART_RATE);
            int averageStepRate = (int) map.get(AVERAGE_STEP_RATE);
            int totalCalories = (int) map.get(TOTAL_CALORIES);

            // the below code handles the fact that HiHealthKit can return various types for the next parameter.
            Object stepDistanceObject = map.get(STEP_DISTANCE); // this alternates between Integer and Double
            double stepDistance;
            if (stepDistanceObject instanceof Integer) {
                stepDistance = (double) (int) stepDistanceObject;
            } else stepDistance = (double) stepDistanceObject;

            float totalAltitude = (float) map.get(TOTAL_ALTITUDE);
            int totalDistance = (int) map.get(TOTAL_DISTANCE);
            int step = (int) map.get(STEP);
            float totalDescent = (float) map.get(TOTAL_DESCENT);
            long totalTime = (long) map.get(TOTAL_TIME);
            float averagePace = (float) map.get(AVERAGE_PACE);
            double averageSpeed = (double) map.get(AVERAGE_SPEED);
            WalkData walkData = new WalkData(date, averageHearRate, averageStepRate, totalCalories, stepDistance, totalAltitude, totalDistance, step, totalDescent, totalTime, averagePace, averageSpeed);
            out.add(walkData);
        }
        return out;
    }

    private List<RunData> getRunDataFromMap(List<HiHealthSetData> data) throws ClassCastException {
        List<RunData> out = new ArrayList<>();
        for (HiHealthSetData datum : data) {
            Map map = datum.getMap();//Map<String,Integer||Long||Float||Double>
            int date = (int) map.get(DATE);
            int averageHearRate = (int) map.get(AVERAGE_HEART_RATE);
            int averageStepRate = (int) map.get(AVERAGE_STEP_RATE);
            int totalCalories = (int) map.get(TOTAL_CALORIES);

            // the below code handles the fact that HiHealthKit can return various types for the next parameter.
            Object stepDistanceObject = map.get(STEP_DISTANCE); // this alternates between Integer and Double
            double stepDistance;
            if (stepDistanceObject instanceof Integer) {
                stepDistance = (double) (int) stepDistanceObject;
            } else stepDistance = (double) stepDistanceObject;

            float totalAltitude = (float) map.get(TOTAL_ALTITUDE);
            int totalDistance = (int) map.get(TOTAL_DISTANCE);
            int step = (int) map.get(STEP);
            float totalDescent = (float) map.get(TOTAL_DESCENT);
            long totalTime = (long) map.get(TOTAL_TIME);
            float averagePace = (float) map.get(AVERAGE_PACE);
            double averageSpeed = (double) map.get(AVERAGE_SPEED);
            RunData runData = new RunData(date, averageHearRate, averageStepRate, totalCalories, stepDistance, totalAltitude, totalDistance, step, totalDescent, totalTime, averagePace, averageSpeed);
            out.add(runData);
        }
        return out;
    }

    private List<CyclingData> getCyclingDataFromMap(List<HiHealthSetData> data) throws ClassCastException {
        List<CyclingData> out = new ArrayList<>();
        for (HiHealthSetData datum : data) {
            Map map = datum.getMap();//Map<String,Integer||Long||Float||Double>
            int date = (int) map.get(DATE);
            int averageHearRate = (int) map.get(AVERAGE_HEART_RATE);
            int totalCalories = (int) map.get(TOTAL_CALORIES);
            int totalDistance = (int) map.get(TOTAL_DISTANCE);
            long totalTime = (long) map.get(TOTAL_TIME);
            float averagePace = (float) map.get(AVERAGE_PACE);
            double averageSpeed = (double) map.get(AVERAGE_SPEED);
            CyclingData cyclingData = new CyclingData(date, averageHearRate, totalCalories, totalDistance, totalTime, averagePace, averageSpeed);
            out.add(cyclingData);
        }
        return out;
    }
}
