package com.umn.ac.id.datakaryawan.session;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSession {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    private static final String PREFER_NAME = UserSession.class.getSimpleName();
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    private static final String USER_ID = "id";
    private static final String USER_NAME = "name";
    private static final String USER_EMAIL = "email";
    private static final String USER_Is_Admin = "isAdmin";

    public UserSession(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setIsUserLogin(boolean isUserLogin) {
        editor.putBoolean(IS_USER_LOGIN, isUserLogin);
        editor.commit();
    }

    public void setUserId(String id){
        editor.putString(USER_ID, id);
        editor.commit();
    }

    public void setName(String name){
        editor.putString(USER_NAME, name);
        editor.commit();
    }

    public void setUserName(String email){
        editor.putString(USER_EMAIL, email);
        editor.commit();
    }

    public void setUserIsAdmin(String isAdmin){
        editor.putString(USER_Is_Admin, isAdmin);
        editor.commit();
    }

    public boolean getIsUserLogin() {
        return pref.getBoolean(IS_USER_LOGIN, false);
    }

    public String getUserId() {
        return pref.getString(USER_ID, "");
    }

    public String getUserName() {
        return pref.getString(USER_NAME, "");
    }

    public String getUserEmail() {
        return pref.getString(USER_EMAIL, "");
    }

    public String getUserIsAdmin() {
        return pref.getString(USER_Is_Admin, "");
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();
    }
}
