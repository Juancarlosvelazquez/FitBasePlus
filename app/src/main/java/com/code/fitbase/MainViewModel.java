package com.code.fitbase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.code.fitbase.model.CyclingData;
import com.code.fitbase.model.DailyCaloriesData;
import com.code.fitbase.model.DailyStepsData;
import com.code.fitbase.model.Gender;
import com.code.fitbase.model.HeartBeatRate;
import com.code.fitbase.model.PermissionStatus;
import com.code.fitbase.model.RRIResponseData;
import com.code.fitbase.model.RealTimeSportData;
import com.code.fitbase.model.RunData;
import com.code.fitbase.model.SleepData;
import com.code.fitbase.model.WalkData;
import com.code.fitbase.model.WeightData;
import com.code.fitbase.repository.AuthorizationRepository;
import com.code.fitbase.repository.CaloriesRepository;
import com.code.fitbase.repository.HeartRateRepository;
import com.code.fitbase.repository.RealTimeSportsRepository;
import com.code.fitbase.repository.RriRepository;
import com.code.fitbase.repository.SingleActivitiesRepository;
import com.code.fitbase.repository.SleepDataRepository;
import com.code.fitbase.repository.StepsRepository;
import com.code.fitbase.repository.UserDataRepository;
import com.code.fitbase.repository.WeightDataRepository;

import java.util.Date;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class MainViewModel extends AndroidViewModel {

    private final WeightDataRepository weightDataRepository = new WeightDataRepository(getApplication());
    private final SleepDataRepository sleepDataRepository = new SleepDataRepository(getApplication());
    private final UserDataRepository userDataRepository = new UserDataRepository(getApplication());
    private final SingleActivitiesRepository singleActivitiesRepository = new SingleActivitiesRepository(getApplication());
    private final AuthorizationRepository authorizationRepository = new AuthorizationRepository(getApplication());
    private final StepsRepository stepsRepository = new StepsRepository(getApplication());
    private final CaloriesRepository caloriesRepository = new CaloriesRepository(getApplication());
    private final HeartRateRepository heartRateRepository = new HeartRateRepository(getApplication(), new Gson());
    private final RriRepository rriRepository = new RriRepository(getApplication(), new Gson());
    private final RealTimeSportsRepository realTimeSportsRepository = new RealTimeSportsRepository(getApplication());

    private final MutableLiveData<HeartBeatRate> heartRate = new MutableLiveData<>();
    private final MutableLiveData<Gender> gender = new MutableLiveData<>();
    private final MutableLiveData<Date> birthday = new MutableLiveData<>();
    private final MutableLiveData<String> height = new MutableLiveData<>();
    private final MutableLiveData<WeightData> weight = new MutableLiveData<>();
    private final MutableLiveData<RRIResponseData> rri = new MutableLiveData<>();
    private final MutableLiveData<List<DailyStepsData>> stepSum = new MutableLiveData<>();
    private final MutableLiveData<Integer> stepsChallengeDays = new MutableLiveData<>();
    private final MutableLiveData<List<DailyCaloriesData>> caloriesSum = new MutableLiveData<>();
    private final MutableLiveData<RealTimeSportData> realTimeSportData = new MutableLiveData<>();
    private final MutableLiveData<List<WalkData>> walkActivityData = new MutableLiveData<>();
    private final MutableLiveData<List<RunData>> runActivityData = new MutableLiveData<>();
    private final MutableLiveData<List<CyclingData>> cyclingActivityData = new MutableLiveData<>();
    private final MutableLiveData<List<SleepData>> sleepData = new MutableLiveData<>();
    private final MutableLiveData<PermissionStatus> authStatus = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void fetchHealthInfo() {
        userDataRepository.getGender(gender::postValue);
        userDataRepository.getBirthday(birthday::postValue);
        userDataRepository.getHeight(height::postValue);
        weightDataRepository.getLatestWeightData(weight::postValue);

        heartRateRepository.track(heartRate::postValue);
        realTimeSportsRepository.track(realTimeSportData::postValue);
    }

    public void requestAuthorization() {
        authorizationRepository.requestAllPermissions();
    }

    public void checkAuthorizationStatus() {
        authorizationRepository.getAuthorizationStatus(authStatus::postValue);
    }

    public void saveWeight(float weight) {
        WeightData data = new WeightData.Builder()
                .setWeight(weight)
                .build();
        weightDataRepository.saveWeightData(data, errorCode -> {
            if (errorCode == 0) fetchHealthInfo();
        });
    }

    @SuppressWarnings("unused")
    public void stopTrackingDynamicData() {
        heartRateRepository.stopTracking();
        realTimeSportsRepository.stopTracking();
    }

    public void trackRri() {
        rriRepository.track(rri::postValue);
    }

    public void stopTrackingRri() {
        rriRepository.stopTracking();
    }

    public void fetchStepsChallengeDays(Date since, Date till) {
        stepsRepository.getStepsChallengeDaysCompleted(since, till, stepsChallengeDays::postValue);
    }

    public void fetchStepSum(Date since, Date till) {
        stepsRepository.getStepsData(since, till, stepSum::postValue);
    }

    public void fetchCaloriesSum(Date since, Date till) {
        caloriesRepository.getCaloriesData(since, till, caloriesSum::postValue);
    }

    public void fetchSleepData(Date since, Date till) {
        sleepDataRepository.getSleepData(since, till, sleepData::postValue);
    }

    public void fetchWalkData(Date since, Date till) {
        singleActivitiesRepository.getWalkData(since, till, walkActivityData::postValue);
    }

    public void fetchRunData(Date since, Date till) {
        singleActivitiesRepository.getRunData(since, till, runActivityData::postValue);
    }

    public void fetchCyclingData(Date since, Date till) {
        singleActivitiesRepository.getCyclingData(since, till, cyclingActivityData::postValue);
    }

    public void deleteWeightData(Date since, Date till) {
        weightDataRepository.deleteWeightData(since, till, errorCode -> {
            if (errorCode == 0) fetchHealthInfo();
        });
    }

    //region LiveData getters

    public LiveData<HeartBeatRate> getHeartRate() {
        return heartRate;
    }

    public LiveData<Gender> getGender() {
        return gender;
    }

    public LiveData<Date> getBirthday() {
        return birthday;
    }

    public LiveData<String> getHeight() {
        return height;
    }

    public LiveData<WeightData> getWeight() {
        return weight;
    }

    public LiveData<RRIResponseData> getRri() {
        return rri;
    }

    public LiveData<List<DailyStepsData>> getStepSum() {
        return stepSum;
    }

    public LiveData<Integer> getStepsChallengeDays() {
        return stepsChallengeDays;
    }

    public LiveData<List<DailyCaloriesData>> getCaloriesSum() {
        return caloriesSum;
    }

    public LiveData<RealTimeSportData> getRealTimeSportData() {
        return realTimeSportData;
    }

    public LiveData<List<SleepData>> getSleepData() {
        return sleepData;
    }

    public LiveData<List<WalkData>> getWalkActivityData() {
        return walkActivityData;
    }

    public LiveData<List<RunData>> getRunActivityData() {
        return runActivityData;
    }

    public LiveData<List<CyclingData>> getCyclingActivityData() {
        return cyclingActivityData;
    }

    public LiveData<PermissionStatus> getAuthStatus() {
        return authStatus;
    }

    //endregion
}
