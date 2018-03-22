package com.example.hoang.doantotnghiep.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

/**
 * Created by kien.lovan on 1/12/2018.
 */

public class TakeImageUtils {

    public static void showPictureDialog(Activity context) {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(context);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Chọn ảnh từ thư viện ảnh",
                "Chụp ảnh từ camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                requestPermissionsStorage(context);
                                dialog.dismiss();
                                break;
                            case 1:
                                requestPermissionsCamera(context);
                                dialog.dismiss();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }


    private static void requestPermissionsCamera(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                takePhotoFromCamera(context);
            } else {
                ActivityCompat.requestPermissions(context,
                        new String[]{Manifest.permission.CAMERA},
                        Const.REQUEST_CODE_PERMISSIONS);
            }
        } else {
            takePhotoFromCamera(context);
        }
    }


    private static void requestPermissionsStorage(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED
                    && context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                choosePhotoFromGallary(context);
            } else {
                ActivityCompat.requestPermissions(context,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Const.REQUEST_CODE_PERMISSIONS);
            }
        } else {
            choosePhotoFromGallary(context);
        }
    }

    public static void choosePhotoFromGallary(Activity context) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        context.startActivityForResult(galleryIntent, Const.GALLERY);
    }

    private static void takePhotoFromCamera(Activity context) {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        context.startActivityForResult(intent, Const.CAMERA);
    }


    public static String saveImage(Bitmap myBitmap,Context context) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + Const.IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(context,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return "";
    }
}
