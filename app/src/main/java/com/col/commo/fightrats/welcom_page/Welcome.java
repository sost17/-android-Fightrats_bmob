package com.col.commo.fightrats.welcom_page;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.col.commo.fightrats.R;
import com.col.commo.fightrats.bmob_util.User;
import com.col.commo.fightrats.dbutil.SingleSqlite;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import static java.lang.System.exit;

/**
 * Created by commo on 2017/5/30.
 */

public class Welcome extends AppCompatActivity implements View.OnClickListener {

    private ImageButton button1,button2,button3,returns,audio,help,leaderbroad;
    public ImageButton user_btn;
    private TextView help_view,login_user;
    int flag = -1,music_isplaying = 0,help_show= 0;
    private static MediaPlayer mp = null;
    String username,pwd,userId;
    public int cor ;
    private SingleSqlite dbhelper,dbhelperInternet;
    int is_online;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.welcome);

        dbhelperInternet = new SingleSqlite(this,"Ranking_table");
        dbhelper = new SingleSqlite(this,"singleRanking_table");
        //音乐
        open_music();

        initSqlite();

        getData();

        button1 = (ImageButton) findViewById(R.id.button1);
        button2 = (ImageButton) findViewById(R.id.button2);
        button3 = (ImageButton) findViewById(R.id.button3);
        returns = (ImageButton) findViewById(R.id.returns);
        user_btn = (ImageButton) findViewById(R.id.user_btn);
        audio = (ImageButton) findViewById(R.id.audio);
        help = (ImageButton) findViewById(R.id.help);
        leaderbroad = (ImageButton) findViewById(R.id.leadrbroad);
        help_view = (TextView) findViewById(R.id.help_text);
        help_view.setMovementMethod(ScrollingMovementMethod.getInstance());
        login_user = (TextView) findViewById(R.id.login_user);

        Intent getintent = getIntent();
        String is_login = getintent.getStringExtra("login_user");
        if(!is_login.equals("")){
            user_btn.setImageResource(0);
            login_user.setText(is_login);
        }

        button1.setOnClickListener(this);
        returns.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        user_btn.setOnClickListener(this);
        audio.setOnClickListener(this);
        help.setOnClickListener(this);
        leaderbroad.setOnClickListener(this);
        login_user.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
                if(flag == -1 ){
                    button1.setImageResource(R.drawable.easy_label);
                    button2.setImageResource(R.drawable.normal_label);
                    button3.setImageResource(R.drawable.diffcult_label);
                    returns.setImageResource(R.drawable.returns);
                    flag = 0;
                }else if(flag == 1){
                    button1.setImageResource(R.drawable.easy_label);
                    button2.setImageResource(R.drawable.normal_label);
                    button3.setImageResource(R.drawable.diffcult_label);
                    returns.setImageResource(R.drawable.returns);
                    flag = 2;
                }else if(flag == 0 || flag == 2){
                    Intent intent = new Intent(Welcome.this,Countdown.class);
                    intent.putExtra("grade","easy");
                    intent.putExtra("login_user",login_user.getText().toString());
                    startActivity(intent);
                    finish();
                }

                break;
            case R.id.button2:
                if(flag == -1 ){
                    button1.setImageResource(R.drawable.easy_label);
                    button2.setImageResource(R.drawable.normal_label);
                    button3.setImageResource(R.drawable.diffcult_label);
                    returns.setImageResource(R.drawable.returns);
                    flag = 0;
                }else if(flag == 1){
                    button1.setImageResource(R.drawable.easy_label);
                    button2.setImageResource(R.drawable.normal_label);
                    button3.setImageResource(R.drawable.diffcult_label);
                    returns.setImageResource(R.drawable.returns);
                    flag = 2;
                }else if(flag == 0 || flag == 2){
                    Intent intent = new Intent(Welcome.this,Countdown.class);
                    intent.putExtra("grade","normal");
                    intent.putExtra("login_user",login_user.getText().toString());
                    startActivity(intent);
                    finish();
                }

                break;
            case R.id.button3:
                if(flag == -1){
                    new AlertDialog.Builder(Welcome.this)
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
                }else if(flag == 0 || flag == 2){
                    Intent intent = new Intent(Welcome.this,Countdown.class);
                    intent.putExtra("grade","diffcult");
                    intent.putExtra("login_user",login_user.getText().toString());
                    startActivity(intent);
                    finish();
                }

                break;
            case R.id.returns:
                if(flag == 0 || flag == 1){
                    button1.setImageResource(0);
                    button2.setImageResource(R.drawable.begin_game);
                    button3.setImageResource(R.drawable.end_game);
                    returns.setImageResource(0);
                    flag = -1;
                }

                break;
            case R.id.user_btn:
                is_online = dbhelperInternet.is_online();
                LayoutInflater inflater = LayoutInflater.from(this);
                View view = inflater.inflate(R.layout.login,null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setView(view);
                builder.setCancelable(false);
                builder.create();
                final AlertDialog dialog = builder.show();
                final TextView title = (TextView) view.findViewById(R.id.title);
                final EditText edit_user = (EditText) view.findViewById(R.id.user);
                final EditText edit_passwd = (EditText) view.findViewById(R.id.passwd);
                final EditText edit_corpasswd = (EditText) view.findViewById(R.id.cor_passwd);
                final Button login_btn = (Button) view.findViewById(R.id.login);
                final Button signup_btn = (Button) view.findViewById(R.id.signup);
                ImageButton close_btn = (ImageButton) view.findViewById(R.id.close);
                final LinearLayout ll_pwd = (LinearLayout) view.findViewById(R.id.layout_pwd);
                cor = 0;
                login_btn.setEnabled(true);
                signup_btn.setEnabled(true);
                login_user.setEnabled(true);

                close_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                login_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        username = edit_user.getText().toString();
                        pwd = edit_passwd.getText().toString();
                        if(username.equals("")){
                            Toast.makeText(Welcome.this, "用户名为空", Toast.LENGTH_SHORT).show();
                        }else if(pwd.equals("")){
                            Toast.makeText(Welcome.this, "请输入密码", Toast.LENGTH_SHORT).show();
                        }else{
                            login_btn.setEnabled(false);
                            signup_btn.setEnabled(false);
                            BmobQuery<User> query = new BmobQuery<User>();
                            query.addWhereEqualTo("username",username);
                            query.findObjects(new FindListener<User>() {
                                @Override
                                public void done(List<User> list, BmobException e) {
                                    try{
                                        User newuser = new User();
                                        newuser = list.get(0);
                                        userId=newuser.getObjectId();
                                        if(e==null&&newuser.getStatus().equals(false)){
                                            //登录用户
                                            User p=new User();
                                            p.setUsername(username);
                                            p.setPassword(pwd);
                                            p.login(new SaveListener<User>() {
                                                @Override
                                                public void done(User user, BmobException e) {
                                                    if(e==null){
//                                                        Toast.makeText(Welcome.this, "登录成功", Toast.LENGTH_SHORT).show();
                                                        //修改用户的状态，修改数据必须在用户登录之后才能修改
                                                        User Currentuser= new User();
                                                        Currentuser.setStatus(true);
                                                        Currentuser.update(userId,new UpdateListener() {
                                                            @Override
                                                            public void done(BmobException e) {
                                                                if(e==null){

                                                                    Toast.makeText(Welcome.this, "登录成功", Toast.LENGTH_SHORT).show();
                                                                }else{
                                                                    Toast.makeText(Welcome.this, "登录失败", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });

                                                        dialog.dismiss();
                                                        user_btn.setImageResource(0);
                                                        login_user.setText(username);
                                                    }else{
                                                        Toast.makeText(Welcome.this, "密码错误", Toast.LENGTH_SHORT).show();
                                                        login_btn.setEnabled(true);
                                                        signup_btn.setEnabled(true);
                                                    }
                                                }
                                            });
                                        }else{
                                            Toast.makeText(Welcome.this, "该用户上次未正常退出，或其他设备已登录，对方已下线", Toast.LENGTH_SHORT).show();
                                            User p=new User();
                                            p.setUsername(username);
                                            p.setPassword(pwd);
                                            p.login(new SaveListener<User>() {
                                                @Override
                                                public void done(User user, BmobException e) {
                                                    if(e==null){
//                                                        Toast.makeText(Welcome.this, "登录成功", Toast.LENGTH_SHORT).show();
                                                        //修改用户的状态，修改数据必须在用户登录之后才能修改
                                                        User Currentuser= new User();
                                                        Currentuser.setStatus(true);
                                                        Currentuser.update(userId,new UpdateListener() {
                                                            @Override
                                                            public void done(BmobException e) {
                                                                if(e==null){
                                                                    Toast.makeText(Welcome.this, "登录成功", Toast.LENGTH_SHORT).show();
//                                                                    Toast.makeText(Welcome.this, "更新成功", Toast.LENGTH_SHORT).show();
                                                                }else{
                                                                    Toast.makeText(Welcome.this, "登录失败", Toast.LENGTH_SHORT).show();
//                                                                    Toast.makeText(Welcome.this, "更新失败", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });

                                                        dialog.dismiss();
                                                        user_btn.setImageResource(0);
                                                        login_user.setText(username);
                                                    }else{
                                                        Toast.makeText(Welcome.this, "密码错误", Toast.LENGTH_SHORT).show();
                                                        login_btn.setEnabled(true);
                                                        signup_btn.setEnabled(true);
                                                    }
                                                }
                                            });
                                        }
                                    }catch (NullPointerException systeme){
                                        if(is_online == 0){
                                            Toast.makeText(Welcome.this, "没网了,请检查网络是否正常", Toast.LENGTH_SHORT).show();
                                            login_btn.setEnabled(true);
                                            signup_btn.setEnabled(true);
                                        }else{
                                            Toast.makeText(Welcome.this, "用户不存在", Toast.LENGTH_SHORT).show();
                                            login_btn.setEnabled(true);
                                            signup_btn.setEnabled(true);
                                        }
                                    }
                                }
                            });
                        }
                    }
                });

                signup_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(cor == 0){
                            ll_pwd.setVisibility(View.VISIBLE);
                            login_btn.setVisibility(View.GONE);
                            title.setText("注册");
                            cor = 1;
                        }

                        username = edit_user.getText().toString();
                        pwd = edit_passwd.getText().toString();
                        String cor_pwd = edit_corpasswd.getText().toString();
                        if(cor == 1){
                            cor = -1;
                        }else if(username.equals("")){
                            Toast.makeText(Welcome.this, "用户名为空", Toast.LENGTH_SHORT).show();
                        }else if(pwd.equals("")){
                            Toast.makeText(Welcome.this, "请输入密码", Toast.LENGTH_SHORT).show();
                        }else if(cor_pwd.equals("")){
                            Toast.makeText(Welcome.this, "请输入重复密码", Toast.LENGTH_SHORT).show();
                        }else if(!(pwd.equals(cor_pwd))){
                            Toast.makeText(Welcome.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                        }else{
                            login_btn.setEnabled(false);
                            signup_btn.setEnabled(false);
                            adduser(username,pwd);
                            dialog.dismiss();
                        }

                    }
                });

                break;
            case R.id.login_user:
                new AlertDialog.Builder(Welcome.this)
                        .setMessage("退出登录")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String usernames = login_user.getText().toString();
                                unloginuser(usernames);
                                login_user.setEnabled(false);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create().show();

                break;
            case R.id.audio:
                if(music_isplaying == 0){
                    audio.setImageResource(R.drawable.close_audio);
                    close_music();
                    music_isplaying = 1;
                } else if(music_isplaying == 1){
                    audio.setImageResource(R.drawable.open_audio);
                    open_music();
                    music_isplaying = 0;
                }
                break;
            case R.id.help:
                LinearLayout ll = (LinearLayout) findViewById(R.id.linearlayout_help);
                if(help_show == 0){
                    ll.setBackgroundResource(R.drawable.background_help);
                    help_view.setText("  该游戏由经典的砸地鼠游戏改编而来，一共分为三个游戏模式：简单、一般、困难。\n" +
                            "简单砸中地鼠一次加10分，一般砸中地鼠一次加15分，困难砸中一次加20分。\n" +
                            "该游戏在玩耍的时候一共有5条命，如果有五次没有砸中地鼠，就会结束游戏，是不是很简单啊。\n" +
                            "该游戏还有两个排行榜功能，一个是本地最高分，另一个是用户的最高分。\n");
                    help_show = 1;
                }else if(help_show == 1){
                    ll.setBackgroundResource(0);
                    help_view.setText("");
                    help_show = 0;
                }

                break;
            case R.id.leadrbroad:
                Intent intent = new Intent(Welcome.this,Ranking.class);
                intent.putExtra("login_user",login_user.getText().toString());
                startActivity(intent);
                finish();
            default:
                break;
        }
    }

    public void open_music(){
        if(mp != null){
            mp.release();
        }
        mp = MediaPlayer.create(Welcome.this, R.raw.starting);
        mp.start();
    }
    public void close_music(){
        if(mp != null){
            mp.stop();
            mp.release();
            mp=null;
        }
    }

    public void adduser(String username,String passwd){
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwd);
        user.setStatus(false);
        user.setEasy("0");
        user.setNormal("0");
        user.setDiffcult("0");
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if(e==null){
                    Toast.makeText(Welcome.this, "注册成功", Toast.LENGTH_SHORT).show();
                }else{
                    if(is_online == 0){
                        Toast.makeText(Welcome.this, "没网了,请检查网络是否正常", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Welcome.this, "用户已存在", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void unloginuser(String username){//退出登录
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("username", username);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {

                User newuser=new User();
                newuser=list.get(0);
                userId=newuser.getObjectId();
                if(e==null&&newuser.getStatus().equals(true)){
                    User Currentuser= new User();
                    Currentuser.setStatus(false);
                    Currentuser.update(userId,new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                user_btn.setImageResource(R.drawable.user);
                                login_user.setText("");
                                Toast.makeText(Welcome.this, "退出成功", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(Welcome.this, "退出失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    User.logOut();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        if(mp != null){
            mp.stop();
            mp.release();
            mp=null;
        }
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(Welcome.this)
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

    public void Bmob_list(){
        BmobQuery<User> bmobQuery = new BmobQuery<User>();
        bmobQuery.addWhereNotEqualTo("username","");
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if(e == null){
                    for(int i = 0; i < list.size(); i ++){
                        dbhelperInternet.insert_ranking(list.get(i).getUsername(),list.get(i).getEasy(),list.get(i).getNormal(),list.get(i).getDiffcult());
                    }
                }
            }
        });
    }

    public void getData(){
        dbhelperInternet.truncate_ranking();
        Bmob_list();
    }

    public void initSqlite(){
        int ifexists = dbhelper.ifexitsts();

        if(ifexists == 0){
            dbhelper.initDB();
        }
    }
}
