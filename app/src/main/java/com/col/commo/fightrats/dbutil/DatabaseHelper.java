package com.col.commo.fightrats.dbutil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by commo on 2017/6/3.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "Fightrats.db";
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table if not exists singleRanking_table(flag varchar(5),easy int ,normal int,diffcult int)");
        db.execSQL("create table if not exists Ranking_table(username varchar(50),easy int ,normal int,diffcult int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists singleRanking_table");
        db.execSQL("drop table if exists Ranking_table");
        onCreate(db);

    }
}
