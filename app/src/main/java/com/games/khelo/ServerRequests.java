package com.games.khelo;


import android.content.Context;
import android.widget.Toast;

public class ServerRequests {
//    public static final String UID="user_id";
//    public static final String FCM_ID="fcm_id";
//    public static final String USER_NAME="username";
//    public static final String EMAIL_ID="emailId";
//    public static final String PHONE="phone";
//    public static final String GRP_ID="group_id";
//    public static final String GRP_NAME="group_name";
//    public static final String CREATION_DATE="creation_date";
//    public static final String CREATOR_UID="creator_uid";
//    public static final String MSG_ID="message_id";
//    public static final String MSG="message";
//    public static final String TIME_STAMP="time_stamp";

    public static ServerRequests serverRequests=null;

    public static ServerRequests getInstance()
    {
        if(serverRequests==null)
            serverRequests=new ServerRequests();
        return serverRequests;
    }

    public void signIn(Context c, String s)
    {
        if(s.length()!=10)
        {
            Toast.makeText(c, "Invalid Phone Number!", Toast.LENGTH_SHORT).show();
            return;
        }
    }


}
