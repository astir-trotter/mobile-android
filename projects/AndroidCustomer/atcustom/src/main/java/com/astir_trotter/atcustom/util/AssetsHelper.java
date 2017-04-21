/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 12/2/16
 */
package com.astir_trotter.atcustom.util;

import android.content.Context;

import com.astir_trotter.atcustom.component.Constants;

import java.io.IOException;
import java.io.InputStream;

public class AssetsHelper {
    private static final String TAG = AssetsHelper.class.getSimpleName();

    public static String loadFromAsset(Context context, String filePathName) {
        String fileContent = Constants.EMPTY_STRING;

        try {
            InputStream is = context.getAssets().open(filePathName);
            int size = is.available();
            byte[] buffer = new byte[size];
            int readSize = is.read(buffer);
            is.close();
            fileContent = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return fileContent;
    }
}