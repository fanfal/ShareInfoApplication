package com.au.shareinfoapplication.network.response;


import com.au.shareinfoapplication.Model.ShareInfo;

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
