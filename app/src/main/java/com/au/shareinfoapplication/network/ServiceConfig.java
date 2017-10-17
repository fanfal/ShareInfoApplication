package com.au.shareinfoapplication.network;


import com.au.shareinfoapplication.Model.HALBean;
import com.au.shareinfoapplication.Model.Link;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import utils.JsonUtil;

public class ServiceConfig {
    private static final String POST_SHARE_LOCATION_INFO_KEY = "post_share_location_info";
    private static final String GET_OBTAIN_LOCATION_INFO = "get_obtain_location_info";
    private SIHttpUtil httpUtil;
    private String configUrl;
    private Map<String, String> config;

    public ServiceConfig(SIHttpUtil httpUtil, String configUrl) {
        this.httpUtil = httpUtil;
        this.configUrl = configUrl;
    }

    public String generateShareCarInfoUrl() throws IOException {
        initConfig();
        return config.get(POST_SHARE_LOCATION_INFO_KEY);
    }

    public String generateObtainCarInfoUrl() throws IOException {
        initConfig();
        return config.get(GET_OBTAIN_LOCATION_INFO);
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
