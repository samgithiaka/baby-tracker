package com.mwai.babyapp.app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

public class BaseFragment extends Fragment {
    public Activity activityContext;
    private static final String TAG = "BaseFragment";

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityContext = this.getActivity();
    }

    /**
     * ask for permission
     * @return true or false
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected boolean checkForPermission() {
        try {
            Log.e(TAG, "checkForPermission: checking for permissions");
            boolean accepted = true;

            // Check if we have write permission
            int permission = ActivityCompat.checkSelfPermission(activityContext,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (permission != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(activityContext,
                        PERMISSIONS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE
                );
                accepted = false;
            }

            return accepted;
        } catch (Exception e) {
            Log.e(TAG, "checkPermissionREAD_EXTERNAL_STORAGE: ", e);
            return false;
        }
    }
}