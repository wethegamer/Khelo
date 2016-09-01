package com.games.khelo;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    public void signIn(Context c, String s,String s2)
    {
        if(s.length()!=10)
        {
            Toast.makeText(c, "Invalid Phone Number!", Toast.LENGTH_SHORT).show();
            return;
        }
        for(int i=0;i<10;i++)
        {
            if(s.charAt(i)<'0' || s.charAt(i)>'9')
            {
                Toast.makeText(c, "Invalid Phone Number!", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if(s2.isEmpty())
        {
            Toast.makeText(c, "Invalid Phone Number!", Toast.LENGTH_SHORT).show();
            return;
        }
        SharedPreferences.Editor et=MainActivity.details.edit();
        et.putString(MainActivity.PHONE,s);
        et.commit();
        Intent i=new Intent(c,MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        c.startActivity(i);

    }




}
