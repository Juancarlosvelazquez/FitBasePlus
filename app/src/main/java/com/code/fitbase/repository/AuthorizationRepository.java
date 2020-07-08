package com.code.fitbase.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.code.fitbase.model.PermissionStatus;
import com.code.fitbase.util.ConsumerCompat;
import com.huawei.hihealth.error.HiHealthError;
import com.huawei.hihealthkit.auth.HiHealthAuth;
import com.huawei.hihealthkit.auth.HiHealthOpenPermissionType;

import java.util.List;

import static com.code.fitbase.util.Constants.LOGS_TAG;

/**
 * In order to use HiHealthKit, you need to first acquire proper permissions.
 * This class helps you do that in a simple way. One way to set it up is to
 * call {@link AuthorizationRepository#getAuthorizationStatus(ConsumerCompat)}
 * in onResume() of your activity, which allows you to capture the user's choices
 * made outside your app, on the permissions' screen, and react to them when users
 * come back to your app. It also runs when the user first comes in, which is
 * convenient. Once you've obtained the permissions status, and the result is
 * {@link PermissionStatus#PERMISSION_NOT_GRANTED}, call
 * {@link AuthorizationRepository#requestAllPermissions()}
 */
public class AuthorizationRepository {
    private final Application application;

    public AuthorizationRepository(Application application) {
        this.application = application;
    }

    /**
     * This method request all permissions via call to
     * {@link AuthorizationRepository#requestAuthorization(int[], int[])}
     */
    public void requestAllPermissions() {
        int[] read = new int[]{
                HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_READ_USER_PROFILE_INFORMATION,
                HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_READ_USER_PROFILE_FEATURE,
                HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_READ_DATA_SET_HEART,
                HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_READ_DATA_SET_WALK_METADATA,
                HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_READ_DATA_SET_RUN_METADATA,
                HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_READ_DATA_SET_RIDE_METADATA,
                HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_READ_DATA_SET_WEIGHT,
                HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_READ_DATA_SET_CORE_SLEEP,
                HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_READ_DATA_POINT_STEP_SUM,
                HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_READ_DATA_POINT_DISTANCE_SUM,
                HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_READ_DATA_POINT_INTENSITY,
                HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_READ_DATA_POINT_CALORIES_SUM,
                HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_READ_REALTIME_HEARTRATE,
                HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_READ_DATA_REAL_TIME_SPORT
        };
        int[] write = new int[]{HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_WRITE_DATA_SET_WEIGHT};
        requestAuthorization(read, write);
    }

    /**
     * This method request authorization for permissions provided in parameters.
     * It will open new screen where user will be able to grant provided permissions
     *
     * @param read  array of read permissions to request
     * @param write array of write permissions to request
     */
    public void requestAuthorization(int[] read, int[] write) {
        HiHealthAuth.requestAuthorization(application, write, read, (resultCode, resultDesc) -> {
            Log.i(LOGS_TAG, "AuthorizationRepository.requestAuthorization() resultCode = " + resultCode);
            Log.i(LOGS_TAG, "AuthorizationRepository.requestAuthorization() resultDesc = " + resultDesc);
        });
    }

    /**
     * This is asynchronous method, it which will check if requested write permission was granted by user.
     * It is not possible to check status of read permissions.
     *
     * @param consumer the callback used for handling the returned data in case of success. Called on a background thread.
     */
    public void getAuthorizationStatus(@NonNull ConsumerCompat<PermissionStatus> consumer) {
        //this is only write permission which is currently supported
        int write = HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_WRITE_DATA_SET_WEIGHT;
        HiHealthAuth.getDataAuthStatus(application, write, (resultCode, permission) -> {
            if (resultCode != HiHealthError.SUCCESS) {
                Log.e(LOGS_TAG, "AuthorizationRepository.getAuthorizationStatus() call to check status failed");
                consumer.accept(PermissionStatus.UNKNOWN);
                return;
            }
            try {
                Log.i(LOGS_TAG, "AuthorizationRepository.getAuthorizationStatus() permission = " + permission);
                int status = ((List<Integer>) permission).get(0);
                PermissionStatus permissionStatus = PermissionStatus.getPermissionStatus(status);
                consumer.accept(permissionStatus);
                Log.i(LOGS_TAG, "AuthorizationRepository.getAuthorizationStatus() status = " + permissionStatus);
            } catch (ClassCastException e) {
                Log.e(LOGS_TAG, "AuthorizationRepository.getAuthorizationStatus() could not cast data to proper type. ", e);
                consumer.accept(PermissionStatus.UNKNOWN);
            }
        });
    }
}

