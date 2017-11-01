package com.au.shareinfoapplication.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreUtil {
    private static final String SHARED_BUS_MESSAGE_UUID = "bus_message_uuid";
    private SharedPreferences defaultPreference;

    public PreUtil(Context context) {
        defaultPreference = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getSharedBusMessageUuid() {
        return defaultPreference.getString(SHARED_BUS_MESSAGE_UUID, null);
    }

    public void setShareBusMessageUuid(String uuid) {
        defaultPreference.edit().putString(SHARED_BUS_MESSAGE_UUID, uuid).apply();
    }
}
