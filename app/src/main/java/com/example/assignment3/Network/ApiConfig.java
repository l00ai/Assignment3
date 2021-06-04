package com.example.assignment3.Network;

import android.app.Activity;
import android.widget.Toast;
import com.example.assignment3.Model.ApiResponse;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ApiConfig {

    private final MyCallBack callBack;
    private final ApiInterface apiService;
    private final Activity activity;
    private final Scheduler subscribeOn;
    private final Scheduler observeOn;


    public ApiConfig(Activity activity, boolean authorization, MyCallBack callBack) {
        this.activity = activity;
        this.callBack = callBack;
        apiService = ApiClient.getClient(authorization).create(ApiInterface.class);
        subscribeOn = Schedulers.io();
        observeOn = AndroidSchedulers.mainThread();


    }

    public void loginApi(String email, String password) {
        Map<Object, Object> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        apiService.login(params)
                .subscribeOn(subscribeOn)
                .observeOn(observeOn)
                .subscribe(new DisposableSingleObserver<ApiResponse>() {
                    @Override
                    public void onSuccess(@NotNull ApiResponse model) {
                        if (model.status) {
                            callBack.Result(model, "userLoginApi", true);
                        } else {
                            callBack.Result(model.msg, "userLoginApi", false);
                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        callBack.Result(showError(e), "userLoginApi", false);
                    }
                });
    }

    public void addNewUserApi(String firstName, String secondName, String email, String password) {
        Map<Object, Object> params = new HashMap<>();
        params.put("firstName", firstName);
        params.put("secondName", secondName);
        params.put("email", email);
        params.put("password", password);
        apiService.addNewUser(params)
                .subscribeOn(subscribeOn)
                .observeOn(observeOn)
                .subscribe(new DisposableSingleObserver<ApiResponse>() {
                    @Override
                    public void onSuccess(@NotNull ApiResponse model) {
                        if (model.status) {
                            callBack.Result(model, "addNewUserApi", true);
                        } else {
                            callBack.Result(model.msg, "addNewUserApi", false);
                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        callBack.Result(showError(e), "addNewUserApi", false);
                    }
                });
    }

    public void addRegTokenApi(String email, String password, String reg_token) {
        Map<Object, Object> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        params.put("reg_token", reg_token);
        apiService.addToken(params)
                .subscribeOn(subscribeOn)
                .observeOn(observeOn)
                .subscribe(new DisposableSingleObserver<ApiResponse>() {
                    @Override
                    public void onSuccess(@NotNull ApiResponse model) {
                        if (model.status) {
                            callBack.Result(model, "addRegTokenApi", true);
                        } else {
                            callBack.Result(model.msg, "addRegTokenApi", false);
                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        callBack.Result(showError(e), "addRegTokenApi", false);
                    }
                });
    }

    private String showError(Throwable e) {
        String message = "";
        try {
            if (e instanceof IOException) {
                message = "تأكد ان هناك اتصال بالانترنت";
            } else if (e instanceof HttpException) {
                HttpException error = (HttpException) e;
                if (error.response().code() == 401) {
                    String errorBody = error.response().errorBody().string();
                    JSONObject jObj = new JSONObject(errorBody);
                    message = jObj.getString("msg");
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                } else {
                    String errorBody = error.response().errorBody().string();
                    JSONObject jObj = new JSONObject(errorBody);
                    message = jObj.getString("msg");
                }

            } else {
                message = e.getMessage();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return message;
    }
}
