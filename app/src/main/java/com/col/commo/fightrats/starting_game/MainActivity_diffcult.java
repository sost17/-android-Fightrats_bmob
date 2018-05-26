package com.col.commo.fightrats.starting_game;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.col.commo.fightrats.R;
import com.col.commo.fightrats.end_game.Finish;
import com.col.commo.fightrats.welcom_page.Welcome;

import java.util.HashMap;
import java.util.Random;

import static java.lang.System.exit;

/**
 * Created by commo on 2017/5/30.
 */

public class MainActivity_diffcult extends Activity implements View.OnClickListener {
    private int jf = 0;
    private TextView textView ;
    private ImageView integral,hole,hole1,hole2,hole3,hole4,hole5,hole6,hole7,hole8,hole9,hole10,hole11,hole12,hole13,hole14,hole15,hole16,hole17,hole18,hole19,ham1,ham2,ham3,ham4,ham5;
    private ImageButton audio,pause;
    private Chronometer ch ;
    Animation translate;
    private SoundPool pool;
    private static MediaPlayer mp = null;
    private HashMap<Integer, Integer> soundmap = new HashMap<Integer, Integer>();
    int count,music_isplaying = 0;
    String login_user;

    @SuppressLint("HandlerLeak") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_diffcult);

        //音乐
        open_music();

        //计时器
        ch = (Chronometer) findViewById(R.id.chronometer1);
        ch.setBase(SystemClock.elapsedRealtime());
        ch.setFormat("已用时间：%s");
        ch.start();

        initView();

        Intent getintent = getIntent();
        login_user = getintent.getStringExtra("login_user");

        MyThread thread = new MyThread();
        thread.start();
    }

    public void initView(){
        textView = (TextView) findViewById(R.id.name_view);
        integral=(ImageView) findViewById(R.id.add20);
        audio = (ImageButton) findViewById(R.id.audio);
        pause = (ImageButton) findViewById(R.id.pause);
        hole = (ImageView) findViewById(R.id.hole1);
        hole1 = (ImageView) findViewById(R.id.hole2);
        hole2 = (ImageView) findViewById(R.id.hole3);
        hole3 = (ImageView) findViewById(R.id.hole4);
        hole4 = (ImageView) findViewById(R.id.hole5);
        hole5 = (ImageView) findViewById(R.id.hole6);
        hole6 = (ImageView) findViewById(R.id.hole7 );
        hole7 = (ImageView) findViewById(R.id.hole8 );
        hole8 = (ImageView) findViewById(R.id.hole9 );
        hole9 = (ImageView) findViewById(R.id.hole10);
        hole10 = (ImageView) findViewById(R.id.hole11);
        hole11 = (ImageView) findViewById(R.id.hole12);
        hole12 = (ImageView) findViewById(R.id.hole13);
        hole13 = (ImageView) findViewById(R.id.hole14);
        hole14 = (ImageView) findViewById(R.id.hole15);
        hole15 = (ImageView) findViewById(R.id.hole16);
        hole16 = (ImageView) findViewById(R.id.hole17);
        hole17 = (ImageView) findViewById(R.id.hole18);
        hole18 = (ImageView) findViewById(R.id.hole19);
        hole19 = (ImageView) findViewById(R.id.hole20);
        ham1 = (ImageView) findViewById(R.id.hammer);
        ham2 = (ImageView) findViewById(R.id.hammer2);
        ham3 = (ImageView) findViewById(R.id.hammer3);
        ham4 = (ImageView) findViewById(R.id.hammer4);
        ham5 = (ImageView) findViewById(R.id.hammer5);
        ham1.setImageResource(R.drawable.hammer);
        ham2.setImageResource(R.drawable.hammer);
        ham3.setImageResource(R.drawable.hammer);
        ham4.setImageResource(R.drawable.hammer);
        ham5.setImageResource(R.drawable.hammer);
        audio.setImageResource(R.drawable.open_audio);
        pause.setImageResource(R.drawable.pause);

        audio.setOnClickListener(this);
        pause.setOnClickListener(this);

        inithole();
        count = 0;
        integral.setVisibility(View.INVISIBLE);
        translate = AnimationUtils.loadAnimation(MainActivity_diffcult.this, R.anim.anim_translate_add20);

        pool.play(soundmap.get(1), 1, 1, 0, -1, 1);
    }

    public void inithole(){
        hole.setImageResource(R.drawable.nothave_mouse);
        hole1.setImageResource(R.drawable.nothave_mouse);
        hole2.setImageResource(R.drawable.nothave_mouse);
        hole3.setImageResource(R.drawable.nothave_mouse);
        hole4.setImageResource(R.drawable.nothave_mouse);
        hole5.setImageResource(R.drawable.nothave_mouse);
        hole6.setImageResource(R.drawable.nothave_mouse);
        hole7.setImageResource(R.drawable.nothave_mouse);
        hole8.setImageResource(R.drawable.nothave_mouse);
        hole9.setImageResource(R.drawable.nothave_mouse);
        hole10.setImageResource(R.drawable.nothave_mouse);
        hole11.setImageResource(R.drawable.nothave_mouse);
        hole12.setImageResource(R.drawable.nothave_mouse);
        hole13.setImageResource(R.drawable.nothave_mouse);
        hole14.setImageResource(R.drawable.nothave_mouse);
        hole15.setImageResource(R.drawable.nothave_mouse);
        hole16.setImageResource(R.drawable.nothave_mouse);
        hole17.setImageResource(R.drawable.nothave_mouse);
        hole18.setImageResource(R.drawable.nothave_mouse);
        hole19.setImageResource(R.drawable.nothave_mouse);
        hole.setEnabled(false);
        hole1.setEnabled(false);
        hole2.setEnabled(false);
        hole3.setEnabled(false);
        hole4.setEnabled(false);
        hole5.setEnabled(false);
        hole6.setEnabled(false);
        hole7.setEnabled(false);
        hole8.setEnabled(false);
        hole9.setEnabled(false);
        hole10.setEnabled(false);
        hole11.setEnabled(false);
        hole12.setEnabled(false);
        hole13.setEnabled(false);
        hole14.setEnabled(false);
        hole15.setEnabled(false);
        hole16.setEnabled(false);
        hole17.setEnabled(false);
        hole18.setEnabled(false);
        hole19.setEnabled(false);

    }

    public void fightmouses(){
        pool.play(soundmap.get(2), 1, 1, 0, 0, 1);
        integral.startAnimation(translate);
        integral.setVisibility(View.INVISIBLE);
        jf++;
        textView.setText("积分："+jf*2+"0");
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
    public void onClick(View v) {
        switch (v.getId()){
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
            case R.id.pause:
                final int temp_count = count;
                final int temp_jf = jf;
                count = -1;
                close_music();
                LayoutInflater inflater = LayoutInflater.from(this);
                View view = inflater.inflate(R.layout.pause,null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setView(view);
                builder.setCancelable(false);
                builder.create();
                final AlertDialog dialog = builder.show();
                ImageButton play = (ImageButton) view.findViewById(R.id.play);
                ImageButton home = (ImageButton) view.findViewById(R.id.home);

                play.setImageResource(R.drawable.play);
                home.setImageResource(R.drawable.home);

                play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        count = temp_count;
                        jf = temp_jf;
                        dialog.dismiss();
                        if(music_isplaying == 1){
                            audio.setImageResource(R.drawable.close_audio);
                            close_music();
                        } else if(music_isplaying == 0){
                            open_music();
                        }
                        MyThread thread = new MyThread();
                        thread.start();
                    }
                });
                home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent intent = new Intent(MainActivity_diffcult.this,Welcome.class);
                        intent.putExtra("login_user",login_user);
                        startActivity(intent);
                        finish();
                    }
                });
                break;
            default:
                break;
        }
    }

    public void open_music(){
        if(mp != null){
            mp.release();
        }
        mp = MediaPlayer.create(MainActivity_diffcult.this, R.raw.dalaoshu);
        mp.start();
        pool = new SoundPool(2, AudioManager.STREAM_SYSTEM, 0);
        soundmap.put(1, pool.load(MainActivity_diffcult.this, R.raw.dalaoshu,1));
        soundmap.put(2, pool.load(MainActivity_diffcult.this, R.raw.enter,1));
    }
    public void close_music(){
        if(mp != null){
            mp.stop();
            mp.release();
            mp=null;
            pool = null;
        }
    }

    public class Handle extends Handler {
        public void handleMessage(Message msg) {
            if(msg.what == 0x111){
                int i = (int) msg.obj;
                inithole();

                switch (count){
                    case 0:
                        ham1.setImageResource(0);
                        break;
                    case 1:
                        ham2.setImageResource(0);
                        break;
                    case 2:
                        ham3.setImageResource(0);
                        break;
                    case 3:
                        ham4.setImageResource(0);
                        break;
                    case 4:
                        ham5.setImageResource(0);
                        break;
                    default:
                        break;
                }

                switch (i){
                    case 0:
                        count ++;
                        hole.setEnabled(true);
                        hole.setImageResource(R.drawable.have_diffcultmouse);
                        hole.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                count --;
                                hole.setImageResource(R.drawable.finght_diffcultmouse);
                                fightmouses();
                                hole.setEnabled(false);
                            }
                        });
                        break;
                    case 1:
                        count ++;
                        hole1.setEnabled(true);
                        hole1.setImageResource(R.drawable.have_diffcultmouse);
                        hole1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                count --;
                                hole1.setImageResource(R.drawable.finght_diffcultmouse);
                                fightmouses();
                                hole1.setEnabled(false);
                            }
                        });
                        break;
                    case 2:
                        count ++;
                        hole2.setEnabled(true);
                        hole2.setImageResource(R.drawable.have_diffcultmouse);
                        hole2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                count --;
                                hole2.setImageResource(R.drawable.finght_diffcultmouse);
                                fightmouses();
                                hole2.setEnabled(false);
                            }
                        });
                        break;
                    case 3:
                        count ++;
                        hole3.setEnabled(true);
                        hole3.setImageResource(R.drawable.have_diffcultmouse);
                        hole3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                count --;
                                hole3.setImageResource(R.drawable.finght_diffcultmouse);
                                fightmouses();
                                hole3.setEnabled(false);
                            }
                        });
                        break;
                    case 4:
                        count ++;
                        hole4.setEnabled(true);
                        hole4.setImageResource(R.drawable.have_diffcultmouse);
                        hole4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                count --;
                                hole4.setImageResource(R.drawable.finght_diffcultmouse);
                                fightmouses();
                                hole4.setEnabled(false);
                            }
                        });
                        break;
                    case 5:
                        count ++;
                        hole5.setEnabled(true);
                        hole5.setImageResource(R.drawable.have_diffcultmouse);
                        hole5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                count --;
                                hole5.setImageResource(R.drawable.finght_diffcultmouse);
                                fightmouses();
                                hole5.setEnabled(false);
                            }
                        });
                        break;
                    case 6:
                        count ++;
                        hole6.setEnabled(true);
                        hole6.setImageResource(R.drawable.have_diffcultmouse);
                        hole6.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                count --;
                                hole6.setImageResource(R.drawable.finght_diffcultmouse);
                                fightmouses();
                                hole6.setEnabled(false);
                            }
                        });
                        break;
                    case 7:
                        count ++;
                        hole7.setEnabled(true);
                        hole7.setImageResource(R.drawable.have_diffcultmouse);
                        hole7.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                count --;
                                hole7.setImageResource(R.drawable.finght_diffcultmouse);
                                fightmouses();
                                hole7.setEnabled(false);
                            }
                        });
                        break;
                    case 8:
                        count ++;
                        hole8.setEnabled(true);
                        hole8.setImageResource(R.drawable.have_diffcultmouse);
                        hole8.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                count --;
                                hole8.setImageResource(R.drawable.finght_diffcultmouse);
                                fightmouses();
                                hole8.setEnabled(false);
                            }
                        });
                        break;
                    case 9:
                        count ++;
                        hole9.setEnabled(true);
                        hole9.setImageResource(R.drawable.have_diffcultmouse);
                        hole9.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                count --;
                                hole9.setImageResource(R.drawable.finght_diffcultmouse);
                                fightmouses();
                                hole9.setEnabled(false);
                            }
                        });
                        break;
                    case 10:
                        count ++;
                        hole10.setEnabled(true);
                        hole10.setImageResource(R.drawable.have_diffcultmouse);
                        hole10.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                count --;
                                hole10.setImageResource(R.drawable.finght_diffcultmouse);
                                fightmouses();
                                hole10.setEnabled(false);
                            }
                        });
                        break;
                    case 11:
                        count ++;
                        hole11.setEnabled(true);
                        hole11.setImageResource(R.drawable.have_diffcultmouse);
                        hole11.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                count --;
                                hole11.setImageResource(R.drawable.finght_diffcultmouse);
                                fightmouses();
                                hole11.setEnabled(false);
                            }
                        });
                        break;
                    case 12:
                        count ++;
                        hole12.setEnabled(true);
                        hole12.setImageResource(R.drawable.have_diffcultmouse);
                        hole12.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                count --;
                                hole12.setImageResource(R.drawable.finght_diffcultmouse);
                                fightmouses();
                                hole12.setEnabled(false);
                            }
                        });
                        break;
                    case 13:
                        count ++;
                        hole13.setEnabled(true);
                        hole13.setImageResource(R.drawable.have_diffcultmouse);
                        hole13.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                count --;
                                hole13.setImageResource(R.drawable.finght_diffcultmouse);
                                fightmouses();
                                hole13.setEnabled(false);
                            }
                        });
                        break;
                    case 14:
                        count ++;
                        hole14.setEnabled(true);
                        hole14.setImageResource(R.drawable.have_diffcultmouse);
                        hole14.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                count --;
                                hole14.setImageResource(R.drawable.finght_diffcultmouse);
                                fightmouses();
                                hole14.setEnabled(false);
                            }
                        });
                        break;
                    case 15:
                        count ++;
                        hole15.setEnabled(true);
                        hole15.setImageResource(R.drawable.have_diffcultmouse);
                        hole15.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                count --;
                                hole15.setImageResource(R.drawable.finght_diffcultmouse);
                                fightmouses();
                                hole15.setEnabled(false);
                            }
                        });
                        break;
                    case 16:
                        count ++;
                        hole16.setEnabled(true);
                        hole16.setImageResource(R.drawable.have_diffcultmouse);
                        hole16.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                count --;
                                hole16.setImageResource(R.drawable.finght_diffcultmouse);
                                fightmouses();
                                hole16.setEnabled(false);
                            }
                        });
                        break;
                    case 17:
                        count ++;
                        hole17.setEnabled(true);
                        hole17.setImageResource(R.drawable.have_diffcultmouse);
                        hole17.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                count --;
                                hole17.setImageResource(R.drawable.finght_diffcultmouse);
                                fightmouses();
                                hole17.setEnabled(false);
                            }
                        });
                        break;
                    case 18:
                        count ++;
                        hole18.setEnabled(true);
                        hole18.setImageResource(R.drawable.have_diffcultmouse);
                        hole18.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                count --;
                                hole18.setImageResource(R.drawable.finght_diffcultmouse);
                                fightmouses();
                                hole18.setEnabled(false);
                            }
                        });
                        break;
                    case 19:
                        count ++;
                        hole19.setEnabled(true);
                        hole19.setImageResource(R.drawable.have_diffcultmouse);
                        hole19.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                count --;
                                hole19.setImageResource(R.drawable.finght_diffcultmouse);
                                fightmouses();
                                hole19.setEnabled(false);
                            }
                        });
                        break;
                    default:
                        break;
                }
            }
            super.handleMessage(msg);
        }
    }

    public class MyThread extends Thread{
        Handle handler = new Handle();

        public void run() {
            int hole_number = -1;
            while(!Thread.currentThread().isInterrupted()){
                hole_number = new Random().nextInt(20);
                Message m = handler.obtainMessage();
                m.what = 0x111;
                m.obj = hole_number;
                handler.sendMessage(m);

                if (count >= 4){
                    Intent intent = new Intent(MainActivity_diffcult.this,Finish.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("num", jf*2);
                    bundle.putString("login_user",login_user);
                    bundle.putString("grade","diffcult");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                    break;
                } else if(count == -1){
                    break;
                }

                try {
                    Thread.sleep(800);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        final int temp_count = count;
        final int temp_jf = jf;
        count = -1;
        close_music();
        if(keyCode == KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(MainActivity_diffcult.this)
                    .setCancelable(false)
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

                            count = temp_count;
                            jf = temp_jf;
                            if(music_isplaying == 1){
                                audio.setImageResource(R.drawable.close_audio);
                                close_music();
                            } else if(music_isplaying == 0){
                                open_music();
                            }
                            MyThread thread = new MyThread();
                            thread.start();
                        }
                    })
                    .create().show();
        }

        return false;
    }
}
