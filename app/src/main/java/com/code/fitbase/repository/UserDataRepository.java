package com.code.fitbase.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.code.fitbase.model.Gender;
import com.code.fitbase.util.ConsumerCompat;
import com.code.fitbase.util.HiError;
import com.huawei.hihealth.error.HiHealthError;
import com.huawei.hihealthkit.data.store.HiHealthDataStore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.code.fitbase.util.Constants.LOGS_TAG;

/**
 * Provides access to general information about the user, like gender, weight, height and date of birth.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class UserDataRepository {
    private final Application application;

    public UserDataRepository(Application application) {
        this.application = application;
    }

    /**
     * Method with default parameters, for description see {@link UserDataRepository#getGender(ConsumerCompat, ConsumerCompat)}.
     */
    public void getGender(@NonNull ConsumerCompat<Gender> consumer) {
        getGender(consumer, null);
    }

    /**
     * Obtains user's gender
     *
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     * @param onError  the callback invoked when an error occurs. Called on a background thread.
     */
    public void getGender(@NonNull ConsumerCompat<Gender> consumer, ConsumerCompat<HiError> onError) {
        HiHealthDataStore.getGender(application, (resultCode, gender) -> {
            Log.i(LOGS_TAG, "UserDataRepository.getGender() resultCode is " + resultCode);
            Log.i(LOGS_TAG, "UserDataRepository.getGender() gender is " + gender);
            if (resultCode != HiHealthError.SUCCESS) {
                Log.e(LOGS_TAG, "UserDataRepository.getGender() unexpected result code");
                if (onError != null) {
                    onError.accept(new HiError(resultCode, HiError.HiErrorCause.UNEXPECTED_RESULT_CODE));
                }
                return;
            }
            if (gender != null) {
                String result = gender.toString();
                Gender resolvedGender = Gender.UNKNOWN;
                switch (result) {
                    case "0":
                        resolvedGender = Gender.FEMALE;
                        break;
                    case "1":
                        resolvedGender = Gender.MALE;
                }
                consumer.accept(resolvedGender);
            } else {
                Log.e(LOGS_TAG, "UserDataRepository.getGender() data is null");
                if (onError != null) {
                    onError.accept(new HiError(resultCode, HiError.HiErrorCause.DATA_OBJECT_WAS_NULL));
                }
            }
        });
    }

    /**
     * Method with default parameters, for description see {@link UserDataRepository#getBirthday(ConsumerCompat, ConsumerCompat)}.
     */
    public void getBirthday(@NonNull ConsumerCompat<Date> consumer) {
        getBirthday(consumer, null);
    }

    /**
     * Obtains user's birthday.
     *
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     * @param onError  the callback invoked when an error occurs. Called on a background thread.
     */
    public void getBirthday(@NonNull ConsumerCompat<Date> consumer, ConsumerCompat<HiError> onError) {
        HiHealthDataStore.getBirthday(application, (resultCode, birthday) -> {
            Log.i(LOGS_TAG, "UserDataRepository.getBirthday() resultCode is " + resultCode);
            Log.i(LOGS_TAG, "UserDataRepository.getBirthday() birthday is " + birthday);
            if (resultCode != HiHealthError.SUCCESS) {
                Log.e(LOGS_TAG, "UserDataRepository.getBirthday() unexpected result code");
                if (onError != null) {
                    onError.accept(new HiError(resultCode, HiError.HiErrorCause.UNEXPECTED_RESULT_CODE));
                }
                return;
            }
            if (birthday != null) {
                SimpleDateFormat in = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                try {
                    Date result = in.parse(birthday.toString());
                    consumer.accept(result);
                } catch (ParseException e) {
                    onError.accept(new HiError(resultCode, HiError.HiErrorCause.MALFORMED_DATA_RETURNED));
                }
            } else {
                Log.e(LOGS_TAG, "UserDataRepository.getBirthday() data is null");
                if (onError != null) {
                    onError.accept(new HiError(resultCode, HiError.HiErrorCause.DATA_OBJECT_WAS_NULL));
                }
            }
        });
    }

    /**
     * Method with default parameters, for description see {@link UserDataRepository#getHeight(ConsumerCompat, ConsumerCompat)}.
     */
    public void getHeight(@NonNull ConsumerCompat<String> consumer) {
        getHeight(consumer, null);
    }

    /**
     * Obtains user's height, in centimeters.
     *
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     * @param onError  the callback invoked when an error occurs. Called on a background thread.
     */
    public void getHeight(@NonNull ConsumerCompat<String> consumer, ConsumerCompat<HiError> onError) {
        HiHealthDataStore.getHeight(application, (resultCode, height) -> {
            Log.i(LOGS_TAG, "UserDataRepository.getHeight() resultCode is " + resultCode);
            Log.i(LOGS_TAG, "UserDataRepository.getHeight() height is " + height);
            if (resultCode != HiHealthError.SUCCESS) {
                Log.e(LOGS_TAG, "UserDataRepository.getHeight() unexpected result code");
                if (onError != null) {
                    onError.accept(new HiError(resultCode, HiError.HiErrorCause.UNEXPECTED_RESULT_CODE));
                }
                return;
            }
            if (height != null) {
                String result = height.toString() + " cm";
                consumer.accept(result);
            } else {
                Log.e(LOGS_TAG, "UserDataRepository.getHeight() data is null");
                if (onError != null) {
                    onError.accept(new HiError(resultCode, HiError.HiErrorCause.DATA_OBJECT_WAS_NULL));
                }
            }
        });
    }

    /**
     * Method with default parameters, for description see {@link UserDataRepository#getWeight(ConsumerCompat, ConsumerCompat)}.
     */
    public void getWeight(@NonNull ConsumerCompat<Double> consumer) {
        getWeight(consumer, null);
    }

    /**
     * Obtains user's weight. The value available via this method is updated less frequently than
     * the real-time weightData available with {@link WeightDataRepository#getLatestWeightData(ConsumerCompat)}
     *
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     * @param onError  the callback invoked when an error occurs. Called on a background thread.
     */
    public void getWeight(@NonNull ConsumerCompat<Double> consumer, ConsumerCompat<HiError> onError) {
        HiHealthDataStore.getWeight(application, (resultCode, weight) -> {
            Log.i(LOGS_TAG, "UserDataRepository.getWeight() resultCode is " + resultCode);
            Log.i(LOGS_TAG, "UserDataRepository.getWeight() weight is " + weight);
            if (resultCode != HiHealthError.SUCCESS) {
                Log.e(LOGS_TAG, "UserDataRepository.getWeight() unexpected result code");
                if (onError != null) {
                    onError.accept(new HiError(resultCode, HiError.HiErrorCause.UNEXPECTED_RESULT_CODE));
                }
                return;
            }
            if (weight != null) {
                Double result = (Double) weight;
                consumer.accept(result);
            } else {
                Log.e(LOGS_TAG, "UserDataRepository.getWeight() data is null");
                if (onError != null) {
                    onError.accept(new HiError(resultCode, HiError.HiErrorCause.DATA_OBJECT_WAS_NULL));
                }
            }
        });
    }
}
