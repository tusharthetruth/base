package com.base.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.base.App;
import com.base.R;

/**
 * Created by appstreet on 3/6/17.
 */

public class PemissionUtils {
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 999;
    public static final int CONTACT_READ_PERMISSION_REQUEST_CODE = 998;
    public static final int READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 997;
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 996;
    public static final int WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 995;
    public static final int READ_PHONE_STATE_REQUEST_CODE = 994;
    public static final int SMS_READ_REQUEST_CODE = 995;

    public static final String READ_CONTACT = Manifest.permission.READ_CONTACTS;
    public static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String CAMERA_REQUIRED = Manifest.permission.CAMERA;
    public static final String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    public static final String SMS_READ = Manifest.permission.READ_SMS;

    public static boolean isPermissionGranted(Context context, String permission) {
        if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isPermissionPreviouslyDenied(AppCompatActivity activity, String permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            return true;
        } else {
            return false;
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static void getPermission(final AppCompatActivity context, String permission, int requestCode, boolean showDialog) {
        if (!isPermissionGranted(context, permission)) {
            if (isPermissionPreviouslyDenied(context, permission)) {
                if (showDialog)
                    showDialog(context, permission);
            } else {
                context.requestPermissions(new String[]{permission}, requestCode);
            }
        }
    }

    public static void getPermission(final Fragment context, String permission, int requestCode, boolean showDialog) {
        if (!isPermissionGranted(context.getActivity(), permission)) {
            if (isPermissionPreviouslyDenied((AppCompatActivity) context.getActivity(), permission)) {
                if (showDialog)
                    showDialog(context.getActivity(), permission);
            } else {
                (context).requestPermissions(new String[]{permission}, requestCode);
            }
        }
    }

    private static void showDialog(final Activity activity, String permission) {
        String explaination = App.R().getString(R.string.err_generic_permission);
        switch (permission) {
            case READ_CONTACT:
                explaination = App.R().getString(R.string.err_contact_permission);
                break;
            case READ_EXTERNAL_STORAGE:
                explaination = App.R().getString(R.string.err_read_permission);
                break;
            case CAMERA_REQUIRED:
                explaination = App.R().getString(R.string.err_camera_permission);
                break;
            case WRITE_EXTERNAL_STORAGE:
                explaination = App.getInstance().getString(R.string.err_write_permission);
                break;
            case READ_PHONE_STATE:
                explaination = App.getInstance().getString(R.string.err_phone_permission);
                break;
        }
        UtilHandler.showPermissionNotAllow(activity, explaination, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UtilHandler.startInstalledAppDetailsActivity(activity);
                dialog.dismiss();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

    }


}
