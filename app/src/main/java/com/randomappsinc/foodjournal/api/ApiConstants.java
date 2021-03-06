package com.randomappsinc.foodjournal.api;

public class ApiConstants {

    static final String GRANT_TYPE = "client_credentials";

    static final String BASE_URL = "https://api.yelp.com";

    static final String AUTHORIZATION = "Authorization";
    static final String BEARER_PREFIX = "Bearer ";

    static final String DEFAULT_SEARCH_TERM = "Restaurant";
    static final int DEFAULT_NUM_RESTAURANTS = 10;

    static final String BEST_MATCH = "best_match";
    static final String DISTANCE = "distance";

    public static final int HTTP_STATUS_OK = 200;
    public static final int HTTP_STATUS_UNAUTHORIZED = 401;
}
