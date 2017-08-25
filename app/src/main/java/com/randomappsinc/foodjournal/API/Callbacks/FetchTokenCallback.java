package com.randomappsinc.foodjournal.API.Callbacks;

import com.randomappsinc.foodjournal.API.ApiConstants;
import com.randomappsinc.foodjournal.API.Models.TokenResponse;
import com.randomappsinc.foodjournal.API.RestClient;
import com.randomappsinc.foodjournal.Persistence.PreferencesManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alexanderchiou on 8/13/17.
 */

public class FetchTokenCallback implements Callback<TokenResponse> {
    @Override
    public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
        if (response.code() == ApiConstants.HTTP_STATUS_OK &&
            response.body() != null &&
            response.body().getAccessToken() != null) {
            PreferencesManager.get().setBearerToken(response.body().getAccessToken());
        } else {
            RestClient.getInstance().refreshToken();
        }
    }

    @Override
    public void onFailure(Call<TokenResponse> call, Throwable t) {
        RestClient.getInstance().refreshToken();
    }
}