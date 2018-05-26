package com.col.commo.fightrats.welcom_page;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.col.commo.fightrats.R;
import com.col.commo.fightrats.bmob_util.User;
import com.col.commo.fightrats.dbutil.SingleSqlite;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static java.lang.System.exit;

/**
 * Created by commo on 2017/5/30.
 */

public class Starting extends AppCompatActivity {

    private String Bmob_AppId = "4624138117021eaba80524b91475ac3d";
    private Handler handler;
    private SingleSqlite dbhelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.countdown);

        Bmob.initialize(this, Bmob_AppId);
        dbhelper = new SingleSqlite(this,"Ranking_table");
        dbhelper.truncate_ranking();
        Bmob_list();

        handler= new Handler(){

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                switch (msg.what) {
                    case 0x100:
                        FrameLayout ll=(FrameLayout)findViewById(R.id.framelayout1);
                        ll.setBackgroundResource(R.drawable.dds);
                        break;

                    default:
                        break;
                }
            }

        };
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                for(int i=0;i<2;i++){
                    Message msg = Message.obtain();
                    msg.what = 0x100;
                    handler.sendMessage(msg);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                Intent intent = new Intent(Starting.this,Welcome.class);
                intent.putExtra("login_user","");
                startActivity(intent);
                finish();
            }
        }).start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(Starting.this)
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
                    System.out.println(list.size());
                    for(int i = 0; i < list.size(); i ++){
                        dbhelper.insert_ranking(list.get(i).getUsername(),list.get(i).getEasy(),list.get(i).getNormal(),list.get(i).getDiffcult());
                    }
                }
            }
        });
    }
}
