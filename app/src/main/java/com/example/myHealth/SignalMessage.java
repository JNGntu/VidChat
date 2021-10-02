package com.example.myHealth;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SignalMessage {

    private String messageText;
    private String timeStamp;
    private String date;

    private Boolean remote;

    public SignalMessage(String messageText) {

        this.messageText = messageText;
        this.remote = false;
        this.timeStamp = new SimpleDateFormat("h:mm a").format(new Date());
        this.date = new SimpleDateFormat("dd MMM").format(new Date());
    }

    public SignalMessage(String messageText, Boolean remote) {

        this.messageText = messageText;
        this.remote = remote;
        this.timeStamp = new SimpleDateFormat("h:mm a").format(new Date());
        this.date = new SimpleDateFormat("dd MMM").format(new Date());
    }

    public String getMessageText() {
        return this.messageText;
    }

    public String getTimeStamp() {
        return this.timeStamp;
    }

    public String getDate() {
        return this.date;
    }

    public void setMessageText(String mMessageText) {
        this.messageText = mMessageText;
    }

    public Boolean isRemote() {
        return this.remote;
    }

    public void setRemote(Boolean remote) {
        this.remote = remote;
    }

}
