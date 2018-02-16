/*
 *  LeEco Extras Settings Module for Resurrection Remix ROMs
 *  Made by @andr68rus 2017
 */

package com.cyanogenmod.settings.lepref;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import android.util.Log;
import android.os.SystemProperties;
import java.io.*;
import android.widget.Toast;  
import android.preference.ListPreference;

public class LePrefSettings extends PreferenceActivity implements OnPreferenceChangeListener {
	private static final boolean DEBUG = false;
	private static final String TAG = "LePref";
	private static final String ENABLE_QC_KEY = "qc_setting";
//	private static final String ENABLE_HAL3_KEY = "hal3";
//	private static final String AKT_KEY = "akt";
	private static final String QC_SYSTEM_PROPERTY = "persist.sys.le_fast_chrg_enable";
//	private static final String HAL3_SYSTEM_PROPERTY = "persist.camera.HAL3.enabled";
//	private static final String AKT_SYSTEM_PROPERTY = "persist.AKT.profile";

	private SwitchPreference mEnableQC;
//	private SwitchPreference mEnableHAL3;
//	private ListPreference mAKT;

    private Context mContext;
    private SharedPreferences mPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.le_settings);
        mContext = getApplicationContext();
        
        mEnableQC = (SwitchPreference) findPreference(ENABLE_QC_KEY);
        mEnableQC.setChecked(SystemProperties.getBoolean(QC_SYSTEM_PROPERTY, false));
        mEnableQC.setOnPreferenceChangeListener(this);
                
//        mEnableHAL3 = (SwitchPreference) findPreference(ENABLE_HAL3_KEY);
//        mEnableHAL3.setChecked(SystemProperties.getBoolean(HAL3_SYSTEM_PROPERTY, false));
//        mEnableHAL3.setOnPreferenceChangeListener(this);
        
//        mAKT = (ListPreference) findPreference(AKT_KEY);
//        mAKT.setValue(SystemProperties.get(AKT_SYSTEM_PROPERTY, "Stock"));
//        mAKT.setOnPreferenceChangeListener(this);
        
        if (DEBUG) Log.d(TAG, "Initializating done");
    }

	// Control Quick Charge
    private void setEnableQC(boolean value) {
		if (DEBUG) Log.d(TAG, "QC Changed");
		if(value) {
			SystemProperties.set(QC_SYSTEM_PROPERTY, "1");
		} else {
			SystemProperties.set(QC_SYSTEM_PROPERTY, "0");
		}
		if (DEBUG) Log.d(TAG, "QC setting changed");
    }
/* 
    // Set AKT
    private void setAKT(String value) {
		try {
			Process su = Runtime.getRuntime().exec("su");
			DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());
			outputStream.writeBytes("mount -o remount,rw /system\n");
			outputStream.writeBytes("cat /system/etc/lepref/AKT/" + value + " > /system/etc/init.d/99AKT\n");
			outputStream.writeBytes("chmod 777 /system/etc/init.d/99AKT\n");
			outputStream.writeBytes("/system/etc/init.d/99AKT\n");
			outputStream.writeBytes("mount -o remount,ro /system\n");
			outputStream.flush();
			outputStream.writeBytes("exit\n");
			outputStream.flush();
			su.waitFor();
		} catch(IOException e){
			Toast toast = Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
			toast.show();
		} catch(InterruptedException e){
			Toast toast = Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
			toast.show();
		}
		SystemProperties.set(AKT_SYSTEM_PROPERTY, value);
    }
 
 
    // Control Camera2API
    private void setEnableHAL3(boolean value) {
	if(value) {
		SystemProperties.set(HAL3_SYSTEM_PROPERTY, "1");
	} else {
			SystemProperties.set(HAL3_SYSTEM_PROPERTY, "0");
	}
	if (DEBUG) Log.d(TAG, "HAL3 setting changed");
    }
*/

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final String key = preference.getKey();
        boolean value;
        String strvalue;
        if (DEBUG) Log.d(TAG, "Preference changed.");
        if (ENABLE_QC_KEY.equals(key)) {
			value = (Boolean) newValue;
			mEnableQC.setChecked(value);
			setEnableQC(value);
			return true;
//		} else  if (ENABLE_HAL3_KEY.equals(key)) {
//			value = (Boolean) newValue;
//			mEnableHAL3.setChecked(value);
//			setEnableHAL3(value);
//			return true;
//		} else if (AKT_KEY.equals(key)) {
//			strvalue = (String) newValue;
//			//mEnableHAL3.setChecked(value);
			//setEnableHAL3(value);
//			if (DEBUG) Log.d(TAG, "AKT setting changed: " + strvalue);
//			setAKT(strvalue);
//			return true;
		}
          
          
        return false;
    }

}
