package com.example.edconcierge;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.speech.tts.TextToSpeech;
import android.util.Base64;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DataContainer {

    static String hospitalName = "";

    static List<String> questionsInformation = new ArrayList<>();
    static List<String> answersInformation = new ArrayList<>();
    static List<Bitmap> iconsInformation = new ArrayList<>();

    static List<String> questionsNavigation = new ArrayList<>();
    static List<String> answersNavigation = new ArrayList<>();
    static List<Bitmap> iconsNavigation = new ArrayList<>();

    static String name = "";
    static String id = "";
    static List<String> messages = new ArrayList<>();
    static List<String> timeStamp = new ArrayList<>();

    static TextToSpeech textToSpeech;

    static void clear() {
        questionsInformation.clear();
        answersInformation.clear();
        iconsInformation.clear();
        questionsNavigation.clear();
        answersNavigation.clear();
        iconsNavigation.clear();
        hospitalName = "";
        name = "";
        id = "";
        messages.clear();
        timeStamp.clear();
    }

    static void getData(SharedPreferences sharedPreferences, Context context) {

        DataContainer.clear();

        DataContainer.hospitalName = sharedPreferences.getString("hospitalName", "");;
        DataContainer.name = sharedPreferences.getString("name", "guest");
        DataContainer.id = sharedPreferences.getString("id", "00000000");
        //DataContainer.messages = (new Gson()).fromJson(sharedPreferences.getString("messages", ""), ArrayList.class);

        DataBaseHelper dbHelper = new DataBaseHelper(context, "EDConcierge.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Question", null, "hospital=? and type=?", new String[]{hospitalName, "information"}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String question = cursor.getString(cursor.getColumnIndex("question"));
                String answer = cursor.getString(cursor.getColumnIndex("answer"));
                Bitmap icon = stringToBitmap(cursor.getString(cursor.getColumnIndex("icon")));
                DataContainer.questionsInformation.add(question);
                DataContainer.answersInformation.add(answer);
                DataContainer.iconsInformation.add(icon);
            } while (cursor.moveToNext());
        }

        cursor.close();

        cursor = db.query("Question", null, "hospital=? and type=?", new String[]{hospitalName, "navigation"}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String question = cursor.getString(cursor.getColumnIndex("question"));
                String answer = cursor.getString(cursor.getColumnIndex("answer"));
                Bitmap icon = stringToBitmap(cursor.getString(cursor.getColumnIndex("icon")));
                DataContainer.questionsNavigation.add(question);
                DataContainer.answersNavigation.add(answer);
                DataContainer.iconsNavigation.add(icon);
            } while (cursor.moveToNext());
        }
        cursor.close();

    }

    private static Bitmap stringToBitmap(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
