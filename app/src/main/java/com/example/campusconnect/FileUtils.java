package com.example.campusconnect;

import android.content.Context;
import android.net.Uri;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FileUtils {
    public static File getFileFromUri(Context context, Uri uri) throws Exception {
        InputStream is = context.getContentResolver().openInputStream(uri);
        File tempFile = File.createTempFile("upload", ".jpg", context.getCacheDir());
        tempFile.deleteOnExit();
        FileOutputStream fos = new FileOutputStream(tempFile);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = is.read(buffer)) > 0) {
            fos.write(buffer, 0, length);
        }
        fos.close();
        is.close();
        return tempFile;
    }
}
