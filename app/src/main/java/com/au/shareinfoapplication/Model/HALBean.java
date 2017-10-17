package com.au.shareinfoapplication.Model;


import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class HALBean {
    @SerializedName("_links")
    private Map<String, Link> links;

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }
}
