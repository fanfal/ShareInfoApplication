package com.au.shareinfoapplication.network;


import com.au.shareinfoapplication.model.HALBean;
import com.au.shareinfoapplication.model.Link;
import com.au.shareinfoapplication.utils.JsonUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServiceConfig {
    private static final String POST_SHARE_LOCATION_INFO_KEY = "postShareLocationInfo";
    private static final String GET_OBTAIN_LOCATION_INFO = "getObtainLocationInfo";
    private static final String USER_SIGNIN = "userLogin";
    private static final String USER_SIGNUP = "userRegister";
    private static final String USER_REFRESH_TOKEN = "refreshToken";
    private static final String REMOVE_SHARE_LOCATION_INFO = "removeShareLocationInfo";
    private SIHttpUtil httpUtil;
    private String configUrl;
    private Map<String, String> config;

    public ServiceConfig(SIHttpUtil httpUtil, String configUrl) {
        this.httpUtil = httpUtil;
        this.configUrl = configUrl;
    }

    public String generateShareBusInfoUrl() throws IOException {
        initConfig();
        return config.get(POST_SHARE_LOCATION_INFO_KEY);
    }

    public String generateObtainBusInfoUrl() throws IOException {
        initConfig();
        return config.get(GET_OBTAIN_LOCATION_INFO);
    }

    public String generateSignInUrl() throws IOException {
        initConfig();
        return config.get(USER_SIGNIN);
    }

    public String generateSignUpUrl() throws IOException {
        initConfig();
        return config.get(USER_SIGNUP);
    }

    public String generateRefreshTokenUrl() throws IOException {
        initConfig();
        return config.get(USER_REFRESH_TOKEN);
    }

    public String generateRemoveBusInfoUrl() throws IOException {
        initConfig();
        return config.get(REMOVE_SHARE_LOCATION_INFO);
    }


    private void initConfig() throws IOException {
        if (config == null || config.isEmpty()) {
            config = new HashMap<>();
            HttpResponse response = httpUtil.get(configUrl);
            HALBean halBean = JsonUtil.parseJson(response.getResponseString(), HALBean.class);
            if (halBean.getLinks() != null && !halBean.getLinks().isEmpty()) {
                for (Map.Entry<String, Link> entry : halBean.getLinks().entrySet()) {
                    config.put(entry.getKey(), entry.getValue().getHref());
                }
            }
        }
    }

}
