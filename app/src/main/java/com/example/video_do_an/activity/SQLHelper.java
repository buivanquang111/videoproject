package com.example.video_do_an.activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.video_do_an.trang_chu.Video;

import java.util.ArrayList;
import java.util.List;

public class SQLHelper extends SQLiteOpenHelper {
    private static final String TAG = "com.example.video_do_an.activity.SQLHelper";
    static final String DB_NAME = "Video.db";
    static final String DB_NAME_TABLE = "Video";
    static final int DB_VERSION = 5;

    SQLiteDatabase sqLiteDatabase;
    ContentValues contentValues;
    Cursor cursor;

    public SQLHelper( Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCreaTable = "CREATE TABLE Product ( " +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "img Text," +
                " title Text,"+"file_mp4 Text )";

        //Chạy câu lệnh tạo bảng product
        db.execSQL(queryCreaTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("drop table if exists " + DB_NAME_TABLE);
            onCreate(db);
        }
    }

    public void insertVideo(Video video) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();

        contentValues.put("title", video.getText());
        contentValues.put("img", video.getImg());
        contentValues.put("file_mp4",video.getMp4());

        sqLiteDatabase.insert(DB_NAME_TABLE, null, contentValues);
        closeDB();
    }

    public int deleteVideo(String title) {
        sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete(DB_NAME_TABLE, " title=?",
                new String[]{String.valueOf(title)});
    }

    public boolean delAllVideo() {
        int result;
        sqLiteDatabase = getWritableDatabase();
        result = sqLiteDatabase.delete(DB_NAME_TABLE, null, null);
        closeDB();
        if (result == 1) return true;
        else return false;
    }

    public void updateVideo(String title, String img, String file_mp4) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("img", img);
        contentValues.put("file_mp4",file_mp4);

        sqLiteDatabase.update(DB_NAME_TABLE, contentValues, "title=?",
                new String[]{String.valueOf(title)});
        closeDB();
    }


    @SuppressLint("LongLogTag")
    public void getAllVideo() {
        sqLiteDatabase = getReadableDatabase();
        cursor = sqLiteDatabase.query(false, DB_NAME_TABLE, null,
                null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String img = cursor.getString(cursor.getColumnIndex("img"));
            String file_mp4 = cursor.getString(cursor.getColumnIndex("file_mp4"));
            Log.d(TAG, "getAllProduct: " + "title - " + title + " - img - " + img + " - file_mp4 - " + file_mp4);
        }
        closeDB();
    }

    public List<Video> getAllVideoAdvanced() {
        List<Video> arrayList = new ArrayList<>();
        Video video;

        sqLiteDatabase = getReadableDatabase();
        cursor = sqLiteDatabase.query(false, DB_NAME_TABLE, null, null, null
                , null, null, null, null);

        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String img = cursor.getString(cursor.getColumnIndex("img"));
            String file_mp4 = cursor.getString(cursor.getColumnIndex("file_mp4"));
            video = new Video(img, title, file_mp4);
            arrayList.add(video);
        }
        closeDB();
        return arrayList;
    }

    private void closeDB() {
        if (sqLiteDatabase != null) sqLiteDatabase.close();
        if (contentValues != null) contentValues.clear();
        if (cursor != null) cursor.close();
    }
}
