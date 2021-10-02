package com.example.myHealth;

import android.text.TextUtils;
import androidx.annotation.NonNull;

public class OpentokConfig {
    /*
    Fill the following variables using your own Project info from the OpenTok dashboard
    https://dashboard.tokbox.com/projects
    */

    // Replace with a API key
    public static final String API_KEY = "47339051";

    // Replace with a generated Session ID
    public static final String SESSION_ID = "2_MX40NzMzOTA1MX5-MTYzMjA1OTY0OTIxMX5rM1VUdU4rcmg0ZGR0WmRlUjVXODBoOEZ-fg";

    // Replace with a generated token (from the dashboard or using an OpenTok server SDK)
    public static final String TOKEN = "T1==cGFydG5lcl9pZD00NzMzOTA1MSZzaWc9MjI0MTk2NDYyZjQ4ZmY5NWFlN2IxNzQwZTNmOGJhNjM3MGU0YTUwNzpzZXNzaW9uX2lkPTJfTVg0ME56TXpPVEExTVg1LU1UWXpNakExT1RZME9USXhNWDVyTTFWVWRVNHJjbWcwWkdSMFdtUmxValZYT0RCb09FWi1mZyZjcmVhdGVfdGltZT0xNjMyMDU5Nzc4Jm5vbmNlPTAuNjI2MzI0MDMzNTA5NTIxNyZyb2xlPXB1Ymxpc2hlciZleHBpcmVfdGltZT0xNjM0NjUxNzc4JmluaXRpYWxfbGF5b3V0X2NsYXNzX2xpc3Q9";

    public static boolean isValid() {
        if (TextUtils.isEmpty(OpentokConfig.API_KEY)
                || TextUtils.isEmpty(OpentokConfig.SESSION_ID)
                || TextUtils.isEmpty(OpentokConfig.TOKEN)) {
            return false;
        }

        return true;
    }

    @NonNull
    public static String getDescription() {
        return "OpenTokConfig:" + "\n"
                + "API_KEY: " + OpentokConfig.API_KEY + "\n"
                + "SESSION_ID: " + OpentokConfig.SESSION_ID + "\n"
                + "TOKEN: " + OpentokConfig.TOKEN + "\n";
    }
}
