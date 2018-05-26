package com.col.commo.fightrats.welcom_page;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.col.commo.fightrats.R;
import com.col.commo.fightrats.bmob_util.User;
import com.col.commo.fightrats.dbutil.SingleSqlite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by commo on 2017/6/4.
 */

public class Ranking extends AppCompatActivity implements View.OnClickListener {
    private ImageView easy,normal,diffcult;
    private ImageButton back;
    private RadioButton jd,yb,kn;
    private ListView listview;
    private List<Map<String, Object>> listitem;
    private SimpleAdapter adapter;
    private TextView text_easy,text_normal,text_diffcult;
    private SingleSqlite dbhelper;
    int jf_easy,jf_normal,jf_diffcult,list_notnull = 0;
    String is_login;
    public List<Map<String, Object>> newlistItems ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.maxjf);

        dbhelper = new SingleSqlite(this,"singleRanking_table");
        db_con();

        initview();

        Intent getintent = getIntent();
        is_login = getintent.getStringExtra("login_user");
    }

    public void initview(){
        easy = (ImageView) findViewById(R.id.easy);
        normal = (ImageView) findViewById(R.id.normal);
        diffcult = (ImageView) findViewById(R.id.diffcult);
        text_easy = (TextView) findViewById(R.id.text_easy);
        text_normal = (TextView) findViewById(R.id.text_normal);
        text_diffcult = (TextView) findViewById(R.id.text_diffcult);
        back = (ImageButton) findViewById(R.id.back);
        back.setImageResource(R.drawable.returns);
        easy.setImageResource(R.drawable.easy_label);
        normal.setImageResource(R.drawable.normal_label);
        diffcult.setImageResource(R.drawable.diffcult_label);
        text_easy.setText(String.valueOf(jf_easy));
        text_normal.setText(String.valueOf(jf_normal));
        text_diffcult.setText(String.valueOf(jf_diffcult));
        listview=(ListView) findViewById(R.id.listView1);
        jd=(RadioButton) findViewById(R.id.btn_jiandan);
        yb=(RadioButton) findViewById(R.id.btn_yiban);
        kn=(RadioButton) findViewById(R.id.btn_kunnan);
        jd.setOnClickListener(this);
        yb.setOnClickListener(this);
        kn.setOnClickListener(this);
        back.setOnClickListener(this);
        listitem=getpaihang("easy");//初始化 listitem 为简单的积分
        adapter=new SimpleAdapter(Ranking.this, listitem, R.layout.listitem
                , new String[] {"image","name","paiming"}
                , new int[] {R.id.user_view,R.id.name_view,R.id.jf_view});
        listview.setAdapter(adapter);
    }

    public void db_con(){
        jf_easy = dbhelper.getRanking("easy");
        jf_normal = dbhelper.getRanking("normal");
        jf_diffcult = dbhelper.getRanking("diffcult");

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(Ranking.this,Welcome.class);
            intent.putExtra("login_user",is_login);
            startActivity(intent);
            finish();
        }

        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_jiandan:
                jd.setTextColor(Color.parseColor("#FF0000"));
                yb.setTextColor(Color.parseColor("#000000"));
                yb.setChecked(false);
                kn.setTextColor(Color.parseColor("#000000"));
                yb.setChecked(false);
                listitem.clear();
                listitem.addAll(getpaihang("easy"));//这里必须这样做 不然 会不更新
                //因为使用了listitem=getpaihang(2)，这里的listitem和adapter里的就不是一个了，所以无法更新
                adapter.notifyDataSetChanged(); //这里不能使用该函数，不知道为啥，使用了wuxiao
//                Toast.makeText(Ranking.this, "简单", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_yiban:
                //viewPager.setCurrentItem(TAB_contact);
                jd.setTextColor(Color.parseColor("#000000"));
                jd.setChecked(false);
                yb.setTextColor(Color.parseColor("#FF0000"));
                kn.setTextColor(Color.parseColor("#000000"));
                kn.setChecked(false);
                listitem.clear();
                listitem.addAll(getpaihang("normal"));///这里必须这样做 不然 会不更新
                //因为使用了listitem=getpaihang(2)，这里的listitem和adapter里的就不是一个了，所以无法更新
                adapter.notifyDataSetChanged();  //这里不能使用该函数，不知道为啥，使用了无效
//                Toast.makeText(Ranking.this, "一般", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_kunnan:
                //viewPager.setCurrentItem(TAB_plugin);
                jd.setTextColor(Color.parseColor("#000000"));
                jd.setChecked(false);
                yb.setTextColor(Color.parseColor("#000000"));
                yb.setChecked(false);
                kn.setTextColor(Color.parseColor("#FF0000"));
                listitem.clear();
                listitem.addAll(getpaihang("diffcult"));//这里必须这样做 不然 会不更新
                //因为使用了listitem=getpaihang(2)，这里的listitem和adapter里的就不是一个了，所以无法更新
                adapter.notifyDataSetChanged(); //这里不能使用该函数，不知道为啥，使用了无效

//                Toast.makeText(Ranking.this, "困难", Toast.LENGTH_SHORT).show();
                break;

            case  R.id.back:
                Intent intent = new Intent(Ranking.this,Welcome.class);
                intent.putExtra("login_user",is_login);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }

    public List<Map<String, Object>> getpaihang(String grade) {
        // TODO Auto-generated method stub
        List<User> ranking_lisrt = null;
        if(grade.equals("easy")){
            ranking_lisrt = dbhelper.list_ranking("easy");
        }else if(grade.equals("normal")){
            ranking_lisrt = dbhelper.list_ranking("normal");
        }else if(grade.equals("diffcult")){
            ranking_lisrt = dbhelper.list_ranking("diffcult");
        }
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> newlistItems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < ranking_lisrt.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image",R.drawable.user);
            map.put("name",ranking_lisrt.get(i).getUsername());
            if(grade.equals("easy")){
                map.put("paiming",Integer.parseInt(ranking_lisrt.get(i).getEasy()));
            }else if(grade.equals("normal")){
                map.put("paiming",Integer.parseInt(ranking_lisrt.get(i).getNormal()));
            }else if(grade.equals("diffcult")){
                map.put("paiming",Integer.parseInt(ranking_lisrt.get(i).getDiffcult()));
            }

            listItems.add(map);
        }
        Collections.sort(listItems,new Comparator<Map<String, Object>>() {

            @Override
            public int compare(Map<String, Object> map0,
                               Map<String, Object> map1) {
                // TODO Auto-generated method stub
                int data1=(Integer) map0.get("paiming");
                int data2=(Integer) map1.get("paiming");
                if(data1<data2){
                    return 1;
                }
                return -1;
            }
        });
        for (int i = 0; i < listItems.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            if(i == 0){
                map.put("image",R.drawable.first);
            }else if(i == 1){
                map.put("image",R.drawable.second);
            }else if(i == 2){
                map.put("image",R.drawable.third);
            }else{
                map.put("image",R.drawable.user);
            }
            map.put("name",listItems.get(i).get("name"));
            map.put("paiming", listItems.get(i).get("paiming")+"分");
            newlistItems.add(map);
        }
        return newlistItems;
    }

}
