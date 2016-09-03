package com.games.khelo;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

    public static final String HOST="IP_ADDR";
    public static final String ALL_MESSAGES="http://"+HOST+"/khelo/v1/group/_GID_/messages";
    public static final String GROUP_MEMBERS="http://"+HOST+"/khelo/v1/group/_GID_/members";
    public static final String GROUPS_OF_MEMBER="http://"+HOST+"/khelo/v1/user/_MUID_/groups";
    public static final String GROUPS_MADE_BY_ADMIN="http://"+HOST+"/khelo/v1/admin/_AID_/groups";
    public static final String NEW_USER="http://"+HOST+"/khelo/v1/user/register";
    public static final String NEW_GROUP="http://"+HOST+"/khelo/v1/group/create/_UID_";
    public static final String NEW_MESSAGE="http://"+HOST+"/khelo/v1/group/_GID_/message";
    public static final String NEW_MEMBER_TO_GROUP="http://"+HOST+"/khelo/v1/group/_GID_/add-member/_MID_";
    public static final String FCM_TOKEN_UPDATE="http://"+HOST+"/khelo/v1/user-token-update/_UID_";

    private static ServerRequests serverRequests=null;

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
        et.apply();
        Intent i=new Intent(c,MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        c.startActivity(i);
    }

    //change return type
     public void loadAllMessages(int groupID, final Context c) {
        JsonArrayRequest req=new JsonArrayRequest(
                Request.Method.GET,
                ALL_MESSAGES.replace("_GID_", String.valueOf(groupID)),
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //return arraylist of model class of having these fields
                        //message_id
                        //group_id
                        //sender_uid
                        //message
                        //timestamp
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError) {
                            Toast.makeText(c, "", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
         Volley.newRequestQueue(c).add(req);
    }

    public void groupMembers(int groupID, final Context c) {
        JsonArrayRequest req=new JsonArrayRequest(
                Request.Method.GET,
                GROUP_MEMBERS.replace("_GID_", String.valueOf(groupID)),
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //return arraylist of model class of having these fields
                        //member id
                        //name
                        //email
                        //phone
                        //fcm_id
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError) {
                            Toast.makeText(c, "", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        Volley.newRequestQueue(c).add(req);
    }

    private void groups(final Context c,String url) {
        JsonArrayRequest req=new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //return arraylist of model class of having these fields
                        //group_id
                        //group_name
                        //creation_date
                        //creator_uid -- useless
                        //name -- useless
                        //phone -- useless
                        //email -- useless
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError) {
                            Toast.makeText(c, "", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        Volley.newRequestQueue(c).add(req);
    }

    public void groupsAsMember(int memberId, final Context c) {
        groups(c,GROUPS_OF_MEMBER.replace("_MUID_",String.valueOf(memberId)));
    }

    public void groupsAsAdmin(int adminId, final Context c) {
        groups(c,GROUPS_MADE_BY_ADMIN.replace("_AID_",String.valueOf(adminId)));
    }

    public void sendTokenToServer(final String tokenId, int uId, final Context c) {
        StringRequest req = new StringRequest(
                Request.Method.PUT,
                FCM_TOKEN_UPDATE.replace("_UID_",String.valueOf(uId)),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //just a message of success
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError) {
                            Toast.makeText(c, "", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map= new HashMap<>();
                map.put("fcm_id",tokenId);
                return map;
            }
        };
        Volley.newRequestQueue(c).add(req);
    }

    public void registerUser(final String name, final String phone, final String email, final Context c) {
        StringRequest req = new StringRequest(
                Request.Method.POST,
                NEW_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //just a message of success
                        //user details inside nested jsonObject
                        //name,phone,email,userid,fcm_id
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError) {
                            Toast.makeText(c, "", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map= new HashMap<>();
                map.put("name",name);
                map.put("phone",phone);
                map.put("email",email);
                return map;
            }
        };
        Volley.newRequestQueue(c).add(req);
    }

    public void newMemberToGroup(int gID, int mId, final Context c) {
        StringRequest req = new StringRequest(
                Request.Method.POST,
                NEW_MEMBER_TO_GROUP.replace("_GID_",String.valueOf(gID)).replace("_MID_",String.valueOf(mId)),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //just a message of success
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError) {
                            Toast.makeText(c, "", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        Volley.newRequestQueue(c).add(req);
    }

    public void newGroup(final String groupName, int uID, final Context c) {
        StringRequest req = new StringRequest(
                Request.Method.POST,
                NEW_GROUP.replace("_UID_",String.valueOf(uID)),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //just a message of success
                        //group details under 'group_data' nested json
                        //id,group_name,creation_date,creator_uid
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError) {
                            Toast.makeText(c, "", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map= new HashMap<>();
                map.put("group_name",groupName);
                return map;
            }
        };
        Volley.newRequestQueue(c).add(req);
    }

    public void newMessage(int groupID, final int senderID, final String message, final Context c) {
        StringRequest req = new StringRequest(
                Request.Method.POST,
                NEW_MESSAGE.replace("_GID_",String.valueOf(groupID)),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //just a message of success
                        //group_details under 'group' json
                        //message details under 'message'
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError) {
                            Toast.makeText(c, "", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map= new HashMap<>();
                map.put("sender_uid", String.valueOf(senderID));
                map.put("message",message);
                return map;
            }
        };
        Volley.newRequestQueue(c).add(req);
    }
}
