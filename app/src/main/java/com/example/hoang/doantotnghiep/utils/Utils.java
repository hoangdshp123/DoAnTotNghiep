package com.example.hoang.doantotnghiep.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.DrawableRes;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoang.doantotnghiep.Model.ModelApi.ExpensesDate;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.View.CustomView.ProgressView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by kien.lovan on 12/26/2017.
 */

public class Utils {
    public static ProgressView layoutProgress = null;
    public static Runnable myRunnable;
    public static Handler myHandler;
    public static final int time = 30000;

    public static void showProgressDialog(final Context context, String message) {
        dismissCurrentDialog();
        layoutProgress = new ProgressView(context);
        myHandler = new Handler();
        myRunnable = new Runnable() {
            @Override
            public void run() {
                if (isShowProgress()) {
                    Toast.makeText(context, "Không kết nối được Server. Xin thử lại sau", Toast.LENGTH_SHORT).show();
                    dismissCurrentDialog();
                }
            }
        };
        FrameLayout.LayoutParams layoutParamsControl = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParamsControl.gravity = Gravity.CENTER_VERTICAL;
        ((Activity) context).addContentView(layoutProgress, layoutParamsControl);
        layoutProgress.setVisibility(View.VISIBLE);
        myHandler.postDelayed(myRunnable, time);
        layoutProgress.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//				dismissCurrentDialog();
            }
        });
    }

    public static boolean isShowProgress() {
        if (layoutProgress != null) {
            return true;
        }
        return false;
    }

    public static void dismissCurrentDialog() {
        try {
            if (layoutProgress != null) {
                layoutProgress.setVisibility(View.GONE);
                layoutProgress = null;
            }
            if (myHandler != null) {
                myHandler.removeCallbacks(myRunnable);
                myHandler = null;
                myRunnable = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void shareLink(Context context, String url) {
        if (url.isEmpty()) {
            Toast.makeText(context, "Url is empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, url);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }

    public static String getCurrentTime() {
        //java.util.Calendar cal = java.util.Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aaa");
        String time = sdf.format(new Date());
        String final_time = time.replace("a.m.", "AM").replace("p.m.", "PM");

        return final_time;
    }

    public static String convertTimeStampToTime(long time) {
        Date date = new Date(time * 1000);
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        String dateFormatted = formatter.format(date);
        return dateFormatted;

    }

    public static String convertTimeStampToDay(long time) {
        Date date = new Date(time * 1000);
        DateFormat formatter = new SimpleDateFormat("EEEE");
        String dateFormatted = formatter.format(date);
        return dateFormatted;
    }

    public static String convertTimeStampToDate(long time) {
        Date date = new Date(time * 1000);
        DateFormat formatter = new SimpleDateFormat("EEEE, dd MMMM");
        String dateFormatted = formatter.format(date);
        return dateFormatted;
    }

    public static int convertTimeStampToIntHourse(String timeStampStr) {

        long timestampString = Long.parseLong(timeStampStr);
        String value = new SimpleDateFormat("HH").
                format(new Date(timestampString * 1000));
        return Integer.parseInt(value);
    }

    public static String getCurrentDate(String pattern) {
        String date = "";
        DateFormat format2 = new SimpleDateFormat(pattern);
        Date currentDate = new Date(System.currentTimeMillis());
        date = format2.format(currentDate);
        return date;

    }


    public static String getCurrentMounth() {
        String mounth = "";
        DateFormat format2 = new SimpleDateFormat("MM");
        Date currentDate = new Date(System.currentTimeMillis());
        mounth = format2.format(currentDate);
        return mounth;

    }

    public static String getCurrentYear() {
        String year = "";
        DateFormat format2 = new SimpleDateFormat("yyyy");
        Date currentDate = new Date(System.currentTimeMillis());
        year = format2.format(currentDate);
        return year;

    }

    public static String getCurrentDay() {
        String day = "";
        DateFormat format2 = new SimpleDateFormat("dd");
        Date currentDate = new Date(System.currentTimeMillis());
        day = format2.format(currentDate);
        return day;
    }

    public static String convertDateToISO(Date date) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        String nowAsISO = df.format(date);
        return nowAsISO;
    }

    public static Date convertUTCTimeToLocalTime(String UTCTime) {
        Date output = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = formatter.parse(UTCTime);

            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy"); //this format changeable
            dateFormatter.setTimeZone(TimeZone.getDefault());
            String dateString = dateFormatter.format(value);
            output = convertStringToDate(dateString, "dd/MM/yyyy");
        } catch (Exception e) {
            e.printStackTrace();

        }
        return output;
    }

    public static String convertUTCTimeToLocalTime2(String UTCTime) {
        String output = "";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = formatter.parse(UTCTime);

            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy"); //this format changeable
            dateFormatter.setTimeZone(TimeZone.getDefault());
            output = dateFormatter.format(value);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return output;
    }

    public static String convertUTCMonthToLocalTime(String UTCTime) {
        String output = "";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = formatter.parse(UTCTime);

            SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/yyyy"); //this format changeable
            dateFormatter.setTimeZone(TimeZone.getDefault());
            output = dateFormatter.format(value);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return output;
    }

    public static String convertLocalTimeToUTC(Date date) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
        df.setTimeZone(tz);
        return df.format(date);
    }

    public static String convertLocalTimeToUTC1(Date date) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
        //df.setTimeZone(tz);
        return df.format(date);
    }


    public static Date getPreveousDay(int preveous) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, preveous);
        return cal.getTime();
    }


    public static void initdialog(Context context, String message, String positive, String nagative) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                positive,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        if (nagative.length() > 0)
            builder1.setNegativeButton(
                    nagative,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public static String getRealPathFromURI(Uri contentURI, Context context) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }


    //ad dynamic tableview
    public static void createTableRow(Context context, TableLayout tableLayout, ArrayList<String> contentSubview, @DrawableRes int backgroundColor, boolean textBold) {
        final TableRow tr_head = new TableRow(context);
        tr_head.setBackgroundColor(Color.GRAY);
        tr_head.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.FILL_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        for (int i = 0; i < contentSubview.size(); i++) {
            TextView tv = new TextView(context);
            tv.setText(contentSubview.get(i));
            tv.setTextColor(Color.BLACK);
            tv.setBackgroundResource(backgroundColor);
            if (textBold) {
                tv.setTypeface(null, Typeface.BOLD);
            }

            tv.setPadding(5, 5, 5, 5);
            tr_head.addView(tv);
        }

        tableLayout.addView(tr_head, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.FILL_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
    }

    public static void addAllSubViewToTable(Context context, TableLayout tableLayout, ArrayList<ArrayList<String>> listData) {
        for (int i = 0; i < listData.size(); i++) {
            if (i == 0)
                createTableRow(context, tableLayout, listData.get(i), R.drawable.cell_shape1, true);
            else if (i == listData.size() - 1) {
                createTableRow(context, tableLayout, listData.get(i), R.drawable.cell_shape, true);
            } else
                createTableRow(context, tableLayout, listData.get(i), R.drawable.cell_shape, false);
        }
    }

    public static boolean isThisDateValid(String dateToValidate, String dateFromat) {

        if (dateToValidate == null) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
        sdf.setLenient(false);

        try {

            //if not valid, it will throw ParseException
            Date date = sdf.parse(dateToValidate);

        } catch (ParseException e) {

            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static Date convertStringToDate(String strDate, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String convertDateToString(Date date) {
        String dateTemp = "";
        DateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
        dateTemp = format2.format(date);
        return dateTemp;

    }
    public static String convertDateToStringFormat(Date date,String format) {
        String dateTemp = "";
        DateFormat format2 = new SimpleDateFormat(format);
        dateTemp = format2.format(date);
        return dateTemp;

    }

    public static String revertStringDate(String input) {

        String a[] = input.split("/");
        return (a[2] + "/" + a[1] + "/" + a[0]);

    }

    public static String getMonthFromFullDate(Date date) {
        String dateTemp = "";
        DateFormat format2 = new SimpleDateFormat("MM/yyyy");
        dateTemp = format2.format(date);
        return dateTemp;

    }


    public static int getCountOfDays(String createdDateString, String expireDateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        Date createdConvertedDate = null, expireCovertedDate = null, todayWithZeroTime = null;
        try {
            createdConvertedDate = dateFormat.parse(createdDateString);
            expireCovertedDate = dateFormat.parse(expireDateString);

            Date today = new Date();

            todayWithZeroTime = dateFormat.parse(dateFormat.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int cYear = 0, cMonth = 0, cDay = 0;

        if (createdConvertedDate.after(todayWithZeroTime)) {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(createdConvertedDate);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);

        } else {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(todayWithZeroTime);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);
        }

    /*Calendar todayCal = Calendar.getInstance();
    int todayYear = todayCal.get(Calendar.YEAR);
    int today = todayCal.get(Calendar.MONTH);
    int todayDay = todayCal.get(Calendar.DAY_OF_MONTH);
    */

        Calendar eCal = Calendar.getInstance();
        eCal.setTime(expireCovertedDate);

        int eYear = eCal.get(Calendar.YEAR);
        int eMonth = eCal.get(Calendar.MONTH);
        int eDay = eCal.get(Calendar.DAY_OF_MONTH);

        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        date1.clear();
        date1.set(cYear, cMonth, cDay);
        date2.clear();
        date2.set(eYear, eMonth, eDay);

        long diff = date2.getTimeInMillis() - date1.getTimeInMillis();

        float dayCount = (float) diff / (24 * 60 * 60 * 1000);

        return (int) dayCount;
    }

    public static File scaleImage(Context context, Bitmap bitmap){
        bitmap = scaleDown(bitmap, 300, true);
        String path = getRealPathFromURI(getImageUri(context, bitmap), context);
        File file = new File(path);
        return file;
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }

    public static int countDay(Date fromDay, Date toDay) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(fromDay);
        cal2.setTime(toDay);

        // Get the represented date in milliseconds
        long millis1 = cal1.getTimeInMillis();
        long millis2 = cal2.getTimeInMillis();

        // Calculate difference in milliseconds
        long diff = millis2 - millis1;

        // Calculate difference in days
        long diffDays = diff / (24 * 60 * 60 * 1000);

        return (int) diffDays;
    }

    //get first day of month
    public static String getFirstDateOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        return Utils.convertLocalTimeToUTC(cal.getTime());
    }

    //get last day of month
    public static String getLastDateOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR,23);
        cal.set(Calendar.MINUTE,59);
        cal.set(Calendar.SECOND,59);
        return Utils.convertLocalTimeToUTC(cal.getTime());
    }

    public static void myScreenClick(){

    }

    public static ArrayList<ExpensesDate.Data> addZeroToEmpty(ArrayList<ExpensesDate.Data> input,String date_from,String date_to){
        date_from = date_from.replace('/','-');
        date_to = date_to.replace('/','-');
        ArrayList<ExpensesDate.Data> output = new ArrayList<>();
        String g= date_from.substring(0,10);
        String m= date_to.substring(0,10);


        Date df = Utils.convertStringToDate(g,"yyyy-MM-dd");

        ArrayList<Date> listDateBetwen = new ArrayList<>();
        listDateBetwen.add(df);
        while (!g.equals(m)){
            Calendar c = Calendar.getInstance();
            c.setTime(df);
            c.add(Calendar.DATE, 1);
            df = c.getTime();
            listDateBetwen.add(df);
            g = convertDateToStringFormat(df,"yyyy-MM-dd").substring(0,10);
        }
//        for(int i = 0; i < 4;i++){
//            Calendar c = Calendar.getInstance();
//            c.setTime(df);
//            c.add(Calendar.DATE, 1);
//            df = c.getTime();
//            listDateBetwen.add(df);
//        }

        for(int i = 0; i < listDateBetwen.size();i++){
            String sDate = Utils.convertDateToStringFormat(listDateBetwen.get(i),"yyyy-MM-dd");

            boolean has = false;
            for(int j = 0; j < input.size();j++){
                ExpensesDate.Data item = input.get(j);
                String itemDate = item.getDate_time().substring(0,10);

                if(itemDate.equals(sDate)){
                    output.add(input.get(j));
                    has = true;
                    break;
                }
            }
            if(!has){

                ExpensesDate.Data empty = new ExpensesDate.Data("","","0",sDate,"",
                        "","","","","");
                output.add(empty);
            }



        }

        return output;
    }






    public static ArrayList<String> addDateZero(String fromDate,String toDate){
        fromDate = Utils.convertUTCTimeToLocalTime2(fromDate);
        toDate = Utils.convertUTCTimeToLocalTime2(toDate);
        fromDate = fromDate.replace('/','-');
        toDate = toDate.replace('/','-');
        ArrayList<String> result = new ArrayList<>();

        String m= toDate.substring(0,10);
        String g = fromDate.substring(0,10);

        Date df = Utils.convertStringToDate(g,"dd-MM-yyyy");

        ArrayList<Date> tempDay = new ArrayList<>();
        tempDay.add(df);
        while (!g.equals(m)){
            Calendar c = Calendar.getInstance();
            c.setTime(df);
            c.add(Calendar.DATE, 1);
            df = c.getTime();
            tempDay.add(df);
            g = convertDateToStringFormat(df,"dd-MM-yyyy").substring(0,10);
        }
        DateFormat format2 = new SimpleDateFormat("dd/MM");

        for(int i = 0; i < tempDay.size();i++){
            result.add(format2.format(tempDay.get(i)));
        }
        return result;



    }


}
