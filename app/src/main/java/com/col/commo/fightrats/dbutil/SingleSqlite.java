package com.col.commo.fightrats.dbutil;

import android.content.Context;
import android.database.Cursor;

import com.col.commo.fightrats.bmob_util.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by commo on 2017/6/3.
 */

public class SingleSqlite {
    private DatabaseHelper dbhelper;
    private Context mcontext;
    private String tableName;

    public SingleSqlite(Context context, String tableName){
        this.mcontext = context;
        dbhelper = new DatabaseHelper(context);
        this.tableName = tableName;
    }

    public int ifexitsts(){
        Cursor cu = dbhelper.getReadableDatabase().rawQuery("select count(*) from singleRanking_table",null);
        cu.moveToFirst();
        int ifexists = cu.getInt(0);
        cu.close();
        dbhelper.close();
        return ifexists;
    }

    public void initDB(){
        dbhelper.getReadableDatabase().execSQL("insert into singleRanking_table values('single',0,0,0)");
        dbhelper.close();
    }

    public void updateRanking(String column,String jf){
        if(Integer.parseInt(jf) > getRanking(column)){
            dbhelper.getReadableDatabase().execSQL("update singleRanking_table set " + column + " = ? where flag = 'single'",new String[]{jf});
            dbhelper.close();
        }
    }

    public int getRanking(String column){
        Cursor cu = dbhelper.getReadableDatabase().rawQuery("select " + column + " from singleRanking_table",null);
        cu.moveToFirst();
        int jf = cu.getInt(0);
        cu.close();
        dbhelper.close();
        return jf;
    }

    public void insert_ranking(String username,String easy,String normal,String diffcult){
        dbhelper.getReadableDatabase().execSQL("insert into Ranking_table values(?,?,?,?)",new String[]{username,easy,normal,diffcult});
        dbhelper.close();
    }

    public void truncate_ranking(){
        dbhelper.getReadableDatabase().execSQL("delete from Ranking_table");
        dbhelper.close();
    }

    public List<User> list_ranking(String column){
        List<User> ranking_list = null;
        ranking_list = new ArrayList<>();
        Cursor cu = dbhelper.getReadableDatabase().rawQuery("select username , " + column + " from Ranking_table",null);
        while(cu.moveToNext()){
            User user = new User();
            int username_index = cu.getColumnIndex("username");
            int column_index = cu.getColumnIndex(column);
            String username = cu.getString(username_index);
            String Str_column = cu.getString(column_index);
            user.setUsername(username);
            if(column.equals("easy")){
                user.setEasy(Str_column);
            }else if(column.equals("normal")){
                user.setNormal(Str_column);
            }else if(column.equals("diffcult")){
                user.setDiffcult(Str_column);
            }
            ranking_list.add(user);
        }
        cu.close();
        dbhelper.close();
        return ranking_list;
    }

    public int is_online(){
        Cursor cu = dbhelper.getReadableDatabase().rawQuery("select count(*) from Ranking_table",null);
        cu.moveToFirst();
        int ifexists = cu.getInt(0);
        cu.close();
        dbhelper.close();
        return ifexists;
    }
}
