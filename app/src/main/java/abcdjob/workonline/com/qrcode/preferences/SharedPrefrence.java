package abcdjob.workonline.com.qrcode.preferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;

import abcdjob.workonline.com.qrcode.Models.Settings;
import abcdjob.workonline.com.qrcode.Models.UserDTO;
import abcdjob.workonline.com.qrcode.Util.GlobalVariables;


public class SharedPrefrence {
    public static SharedPreferences myPrefs;
    public static SharedPreferences.Editor prefsEditor;

    public static SharedPrefrence myObj;
    public static final String DATE_STRING = "dateString";

    private SharedPrefrence() {

    }

    @SuppressLint("ApplySharedPref")
    public void clearAllPreferences() {
        prefsEditor = myPrefs.edit();
        prefsEditor.clear();
        prefsEditor.commit();
    }


    public static SharedPrefrence getInstance(Context ctx) {
        if (myObj == null) {
            myObj = new SharedPrefrence();
            myPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
            prefsEditor = myPrefs.edit();
        }
        return myObj;
    }

    public void clearPreferences(String key) {
        prefsEditor.remove(key);
        prefsEditor.commit();
    }


    public void setIntValue(String Tag, int value) {
        prefsEditor.putInt(Tag, value);
        prefsEditor.apply();
    }

    public int getIntValue(String Tag) {
        return myPrefs.getInt(Tag, 0);
    }

    public void setLongValue(String Tag, long value) {
        prefsEditor.putLong(Tag, value);
        prefsEditor.apply();
    }

    public long getLongValue(String Tag) {
        return myPrefs.getLong(Tag, 0);
    }


    public void setValue(String Tag, String token) {
        prefsEditor.putString(Tag, token);
        prefsEditor.commit();
    }



    public String getValue(String Tag) {
        if (Tag.equalsIgnoreCase(GlobalVariables.LATITUDE))
            return myPrefs.getString(Tag, "22.7497853");
        else if (Tag.equalsIgnoreCase(GlobalVariables.LONGITUDE))
            return myPrefs.getString(Tag, "75.8989044");
        return myPrefs.getString(Tag, "");
    }


    public boolean getBooleanValue(String Tag) {
        return myPrefs.getBoolean(Tag, false);

    }

    public void setBooleanValue(String Tag, boolean token) {
        prefsEditor.putBoolean(Tag, token);
        prefsEditor.commit();
    }




    public void setParentUser2(UserDTO userDTO, String tag) {

        Gson gson = new Gson();
        String hashMapString = gson.toJson(userDTO);
        Log.d("KINGSN", "setParentUser2: "+hashMapString);
        prefsEditor.putString(tag, hashMapString);
        prefsEditor.apply();
    }

    public UserDTO getParentUser2(String tag) {
        String obj = myPrefs.getString(tag, "defValue");
        if (obj.equals("defValue")) {
            return new UserDTO();
        } else {
            Gson gson = new Gson();
            String storedHashMapString = myPrefs.getString(tag, "");
            Type type = new TypeToken<UserDTO>() {
            }.getType();
            return gson.fromJson(storedHashMapString, type);
        }
    }

    public void setSettings(Settings settings, String tag) {

        Gson gson = new Gson();
        String hashMapString = gson.toJson(settings);

        prefsEditor.putString(tag, hashMapString);
        prefsEditor.apply();
    }

    public Settings getSettings(String tag) {
        String obj = myPrefs.getString(tag, "defValue");
        if (obj.equals("defValue")) {
            return new Settings();
        } else {
            Gson gson = new Gson();
            String storedHashMapString = myPrefs.getString(tag, "");
            Type type = new TypeToken<Settings>() {
            }.getType();
            return gson.fromJson(storedHashMapString, type);
        }
    }

   /* public UserDTO getParentUser2(String tag) {
        String obj = myPrefs.getString(tag, "defValue");
        assert obj != null;
        if (obj.equals("defValue")) {
            return new UserDTO();
        } else {
            Gson gson = new Gson();
            String storedHashMapString = myPrefs.getString(tag, "");
            Type type = new TypeToken<UserDTO>() {
            }.getType();
            return gson.fromJson(storedHashMapString, type);
        }
    }
*/



}
