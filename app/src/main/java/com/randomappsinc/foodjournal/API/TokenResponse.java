package com.randomappsinc.foodjournal.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alexanderchiou on 8/13/17.
 */

public class TokenResponse {
    @SerializedName("access_token")
    @Expose
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }
}
