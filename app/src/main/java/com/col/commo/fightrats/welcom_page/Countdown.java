package com.col.commo.fightrats.welcom_page;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.col.commo.fightrats.starting_game.MainActivity_diffcult;
import com.col.commo.fightrats.starting_game.MainActivity_easy;
import com.col.commo.fightrats.starting_game.MainActivity_normal;
import com.col.commo.fightrats.R;

import static java.lang.System.exit;

/**
 * Created by commo on 2017/5/30.
 */

public class Countdown extends AppCompatActivity {

    private String[] action = new String[]{"③","②","①","GO"};
    private Handler handler;
    public String grade ,login_user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.countdown);

        Intent getintent = getIntent();
        grade = getintent.getStringExtra("grade");
        login_user = getintent.getStringExtra("login_user");

        handler= new Handler(){

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                switch (msg.what) {
                    case 0x100:
                        FrameLayout ll=(FrameLayout)findViewById(R.id.framelayout1);
                        final View view=new MyView(Countdown.this,action[msg.arg1]);
                        ll.addView(view);
                        Log.i("info", "添加view");
                        view.animate().alpha(0f).setDuration(2000)
                                .setListener(new  AnimatorListenerAdapter(){
                                    public void onAnimationEnd(Animator animation) {
                                        view.setVisibility(View.GONE);
                                    }
                                });

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
                for(int i=0;i<action.length;i++){
                    Message msg = Message.obtain();
                    msg.what = 0x100;
                    msg.arg1 = i;
                    handler.sendMessage(msg);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if(grade.equals("easy")){
                    Intent intent = new Intent(Countdown.this,MainActivity_easy.class);
                    intent.putExtra("login_user",login_user);
                    startActivity(intent);
                    finish();
                }else if(grade.equals("normal")){
                    Intent intent = new Intent(Countdown.this,MainActivity_normal.class);
                    intent.putExtra("login_user",login_user);
                    startActivity(intent);
                    finish();
                }else if(grade.equals("diffcult")){
                    Intent intent = new Intent(Countdown.this,MainActivity_diffcult.class);
                    intent.putExtra("login_user",login_user);
                    startActivity(intent);
                    finish();
                }

            }
        }).start();
    }

    public class MyView extends View{

        private String text;
        public MyView(Context context, String text) {
            super(context);
            this.text=text;
        }

        @SuppressLint("DrawAllocation") @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.BLACK);//设置背景为透明
            Paint p = new Paint();
            Typeface font = Typeface.createFromAsset(getAssets(),"hupo.ttf");
            p.setAntiAlias(true);
            p.setColor(Color.RED);
            p.setTypeface(font);
            p.setTextSize(500);
            p.setTextAlign(Paint.Align.LEFT);//该方法即为设置基线上那个点究竟是left,center,还是right  这里我设置为center
            Rect bounds = new Rect();
            p.getTextBounds(text, 0, text.length(), bounds);//获取文字属性以及测量
            Paint.FontMetricsInt fontMetrics = p.getFontMetricsInt();//左下角的纵坐标
            int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;//Paint左下角的纵坐标
            canvas.drawText(text,getMeasuredWidth() / 2 - bounds.width() / 2, baseline, p);
            super.onDraw(canvas);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(Countdown.this)
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
}
