package com.example.firstmyapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private SQLiteDatabase database;
    private  Context _context;

    public Database(Context context) {
        _context=context;
        database = _context.openOrCreateDatabase("InstagramClone",Context.MODE_PRIVATE,null);
        database.execSQL("CREATE TABLE IF NOT EXISTS Posts (PostId INTEGER PRIMARY KEY AUTOINCREMENT, UserId INTEGER, image BLOB)");
       // database.execSQL("CREATE TABLE IF NOT EXISTS Users (UserId INTEGER PRIMARY KEY AUTOINCREMENT, UserName VARCHAR, Password VARCHAR)");
    }
    public void SavePost(byte[] image,String userName){
        try{
            String sql = "INSERT INTO Posts (image, UserName) VALUES(?, ?)";
            SQLiteStatement sqLiteStatement = database.compileStatement(sql);
            sqLiteStatement.bindBlob(1,image);
            sqLiteStatement.bindString(2,userName);
            sqLiteStatement.execute();

        }catch (Exception e){
            Toast.makeText(_context,"Kayıt başarısız :"+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    public List<Post> GetPosts(){
        List<Post> posts = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM Posts ORDER BY PostId DESC",null);
        int imageIndex = cursor.getColumnIndex("image");
        int userNameIndex = cursor.getColumnIndex("UserName");
        while (cursor.moveToNext()){
            byte[] imageByte = cursor.getBlob(imageIndex);
            Bitmap image = BitmapFactory.decodeByteArray(imageByte,0,imageByte.length);
            String userName = cursor.getString(userNameIndex);
            posts.add(new Post(userName,R.drawable.img4,image));
        }
        cursor.close();
        return posts;
    }
}
