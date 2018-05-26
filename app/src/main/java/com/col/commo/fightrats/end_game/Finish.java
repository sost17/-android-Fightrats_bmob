package com.col.commo.fightrats.end_game;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.col.commo.fightrats.R;
import com.col.commo.fightrats.bmob_util.User;
import com.col.commo.fightrats.dbutil.SingleSqlite;
import com.col.commo.fightrats.starting_game.MainActivity_diffcult;
import com.col.commo.fightrats.starting_game.MainActivity_easy;
import com.col.commo.fightrats.starting_game.MainActivity_normal;
import com.col.commo.fightrats.welcom_page.Welcome;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import static java.lang.System.exit;

/**
 * Created by commo on 2017/5/27.
 */

public class Finish extends Activity {

    private SingleSqlite dbhelper;
    public String login_user,jf;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.finish);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ImageView mouse_view = (ImageView) findViewById(R.id.mouse_grade);
        TextView view = (TextView) findViewById(R.id.name_view);

        dbhelper = new SingleSqlite(this,"singleRanking_table");

        int ifexists = dbhelper.ifexitsts();

        if(ifexists == 0){
            dbhelper.initDB();
            ifexists = 1;
        }

        login_user = bundle.getString("login_user");

        Button start = (Button) findViewById(R.id.start);
        Button stop = (Button) findViewById(R.id.stop);
        if(bundle.getString("grade").equals("easy")){
            jf = bundle.getInt("num")+"0";
            view.setText("你的积分："+jf);
            if(ifexists == 1){
                dbhelper.updateRanking("easy",jf);
            }
            mouse_view.setImageResource(R.drawable.easy_mouse);
            if(!login_user.equals("")){
                update_jf(login_user,"easy");
            }
            start.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent inten = new Intent(Finish.this,MainActivity_easy.class);
                    inten.putExtra("login_user",login_user);
                    startActivity(inten);
                    finish();
                }
            });
        }else if(bundle.getString("grade").equals("normal")){
            jf = String.valueOf(bundle.getInt("num"));
            view.setText("你的积分："+jf);
            if(ifexists == 1){
                dbhelper.updateRanking("normal",String.valueOf(jf));
            }
            mouse_view.setImageResource(R.drawable.normal_mouse);
            if(!login_user.equals("")){
                update_jf(login_user,"normal");
            }
            start.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent inten = new Intent(Finish.this,MainActivity_normal.class);
                    inten.putExtra("login_user",login_user);
                    startActivity(inten);
                    finish();
                }
            });
        }else if(bundle.getString("grade").equals("diffcult")){
            jf = bundle.getInt("num")+"0";
            view.setText("你的积分："+jf);
            if(ifexists == 1){
                dbhelper.updateRanking("diffcult",jf);
            }
            mouse_view.setImageResource(R.drawable.diffcult_mouse);
            if(!login_user.equals("")){
                update_jf(login_user,"diffcult");
            }
            start.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent inten = new Intent(Finish.this,MainActivity_diffcult.class);
                    inten.putExtra("login_user",login_user);
                    startActivity(inten);
                    finish();
                }
            });
        }
        stop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Finish.this,Welcome.class);
                intent.putExtra("login_user",login_user);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(Finish.this)
                    .setMessage("你确定不玩了！！！")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            exit(0);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .create().show();
        }

        return false;
    }

    public void update_jf(String login_user,String grade){
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("username", login_user);
        if(grade.equals("easy")){
            query.findObjects(new FindListener<User>() {
                @Override
                public void done(List<User> list, BmobException e) {

                    User newuser=new User();
                    newuser=list.get(0);
                    userId=newuser.getObjectId();
                    if(e==null&&newuser.getStatus().equals(true)) {
                        //更新数据
                        User p = new User();
                        if(Integer.parseInt(jf) > Integer.parseInt(newuser.getEasy())){
                            p.setEasy(jf);
                        }
                        p.update(userId, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(Finish.this, "上传成绩成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Finish.this, "上传成绩失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            });
        }else if(grade.equals("normal")){
            query.findObjects(new FindListener<User>() {
                @Override
                public void done(List<User> list, BmobException e) {

                    User newuser=new User();
                    newuser=list.get(0);
                    userId=newuser.getObjectId();
                    if(e==null&&newuser.getStatus().equals(true)) {
                        //更新数据
                        User p = new User();
                        if(Integer.parseInt(jf) > Integer.parseInt(newuser.getNormal())){
                            p.setNormal(jf);
                        }
                        p.update(userId, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(Finish.this, "上传成绩成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Finish.this, "上传成绩失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            });
        }else if(grade.equals("diffcult")){
            query.findObjects(new FindListener<User>() {
                @Override
                public void done(List<User> list, BmobException e) {

                    User newuser=new User();
                    newuser=list.get(0);
                    userId=newuser.getObjectId();
                    if(e==null&&newuser.getStatus().equals(true)) {
                        //更新数据
                        User p = new User();
                        if(Integer.parseInt(jf) > Integer.parseInt(newuser.getDiffcult())){
                            p.setDiffcult(jf);
                        }
                        p.update(userId, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(Finish.this, "上传成绩成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Finish.this, "上传成绩失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            });
        }

    }
}
