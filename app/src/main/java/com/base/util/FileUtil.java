package com.base.util;

import android.net.Uri;
import android.text.TextUtils;

import com.base.App;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by appstreet on 2/23/17.
 */

public class FileUtil {
    public static JSONObject getAssetFileJson(String fileName) {
        if (TextUtils.isEmpty(fileName)) return null;
        try {
            JSONObject assetJson = parseInputStreamToJson(new InputStreamReader(App.getInstance().getAssets().open("json/" + fileName)));
            return assetJson;

        } catch (IOException e) {

        }
        return null;
    }

    static JSONObject parseInputStreamToJson(InputStreamReader isr) {
        JSONObject data = null;
        if (isr == null) return data;

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(isr);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            try {
                data = new JSONObject(builder.toString());
            } catch (JSONException e) {
                // LogUtils.debugLog(LogConfig.FILE, Resource.toString(R.string.error6), e);
            }

            return data;

        } catch (IOException e) {
            return data;

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //  LogUtils.debugLog(LogConfig.FILE, Resource.toString(R.string.error6), e);
                }
            }
        }
    }

    public static void moveFile(Uri sourceUri, Uri destinationUri) {
        File from = new File(sourceUri.getPath());
        File to = new File(destinationUri.getPath());
        from.renameTo(to);
    }

//    public static void deleteTempFiles() {
//        deleteIfExists(UriUtils.getIdUri());
//        deleteIfExists(UriUtils.getProfileUri());
//        deleteIfExists(UriUtils.getTempIdUri(false));
//        deleteIfExists(UriUtils.getTempProfileUri(false));
//    }
//
//    private static void deleteIfExists(Uri uri) {
//        if (!UriUtils.doesFileExist(uri))
//            return;
//        File f = new File(uri.getPath());
//        f.delete();
//    }

    public static void copyUri(Uri srcUri, Uri dstUri) throws IOException {
        File src = new File(srcUri.getPath());
        File dst = new File(dstUri.getPath());
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }
}
