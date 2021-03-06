package com.paulz.hhb.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pualbeben on 17/12/8.
 */

public class Msg {
    public String title;
    public String date;
    @SerializedName("abstract")
    public String abstractStr;
    public String id;
    public String url;
    public String img;
    public String imgtitle;
    public int unread;
    public Extend message_extra;
    public int type;

    public class Extend{
        public String id;
        public String url;
        public String sn;
    }
}
