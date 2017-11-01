package com.au.shareinfoapplication.traffic.model;


import com.au.shareinfoapplication.model.ShareInfo;

import java.util.List;

public class TrafficInfoResponse {
    List<ShareInfo> result;

    public List<ShareInfo> getResult() {
        return result;
    }

    public void setResult(List<ShareInfo> result) {
        this.result = result;
    }
}
