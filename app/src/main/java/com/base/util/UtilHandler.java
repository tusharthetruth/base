package com.base.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

import com.base.R;

/**
 * Created by appstreet on 3/6/17.
 */

public class UtilHandler {

    public static void showPermissionNotAllow(final Activity contxt, String explanation, DialogInterface.OnClickListener positiveButton, DialogInterface.OnClickListener onNegativeAction) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(contxt);
        // set title
        alertDialogBuilder.setTitle(null);
        // set dialog message
        alertDialogBuilder
                .setMessage(explanation)
                .setCancelable(false)
                .setPositiveButton(contxt.getResources().getString(R.string.open_settings), positiveButton)
                .setNegativeButton(contxt.getResources().getString(R.string.cancel), onNegativeAction);
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }

    public static void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }
}
