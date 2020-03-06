package com.example.video_do_an.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.video_do_an.Contact.Video;

import java.util.ArrayList;

public class SQLHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLHelper";
    static final String DB_NAME_TABLE = "SQLItemClick";
    static final String DB_NAME = "SQLItemClick.db";
    static final int VERSION = 1;
    SQLiteDatabase sqLiteDatabase;
    ContentValues contentValues;
    Cursor cursor;

    public SQLHelper(Context context){super(context, DB_NAME, null, VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQLItemClick = "CREATE TABLE SQLItemClick ( "+
                "image TEXT," +
                "title TEXT," +
                "file_mp4 TEXT )";
        db.execSQL(SQLItemClick);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion!= newVersion){
            db.execSQL("drop table if exists "+ DB_NAME_TABLE);
            onCreate(db);
        }
    }

    public void insertItem (Video video){
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put("image",video.getImg());
        contentValues.put("title",video.getText());
        contentValues.put("file_mp4",video.getMp4());
        sqLiteDatabase.insert(DB_NAME_TABLE,null,contentValues);
        closeDB();
    }

    public ArrayList<Video> getAllItem(){
        Video video;
        ArrayList<Video> videoArrayList = new ArrayList<>();
        sqLiteDatabase = getReadableDatabase();
        cursor = sqLiteDatabase.query(false,DB_NAME_TABLE,null,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            String img = cursor.getString(cursor.getColumnIndex("image"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String file_mp4 = cursor.getString(cursor.getColumnIndex("file_mp4"));
            video = new Video(img,title,file_mp4);
            videoArrayList.add(video);
        }
        closeDB();
        return videoArrayList;
    }

    public int deleteItem(String title){
        sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete(DB_NAME_TABLE,"title=?", new String[]{title});
    }

    public boolean deleteAll(){
        int result;
        sqLiteDatabase =getWritableDatabase();
        result = sqLiteDatabase.delete(DB_NAME_TABLE,null,null);
        closeDB();
        if (result == 1){
            return true;
        }
        else return false;
    }

    private void closeDB() {
        if(sqLiteDatabase != null) sqLiteDatabase.close();
        if (contentValues != null) contentValues.clear();
        if (cursor != null) cursor.close();
    }


}
