package com.websarva.wings.android.alarm_register;

import static java.lang.String.format;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

public class Alarm_register extends AppCompatActivity {

    // 時刻表示テキストビュー
    TextView txt = null;
    //時間になったら起動するパラメータ
    PendingIntent pendingIntent;
    AlarmManager manager;

    //設定時刻表示用
    String sdata;
    //初期値用 x:時　y:分
    int x=0;
    int y=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_register);
        txt = (TextView) findViewById(R.id.textView8);
/*
        //画面上の時刻ボタンID(button3)を参照する
        Button btn = (Button) findViewById(R.id.button3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.button3) {
                    showTimetDialog();
                }
            }
            private void showTimetDialog() {
                TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 2  && minute >= 0) {
                            textView.setText("おはようございます");
                        } else if(hourOfDay >= 10 && minute >= 0) {
                            textView.setText("こんにちわ");
                        } else if (hourOfDay >= 18 && minute >= 0) {
                            textView.setText("こんばんは");
                        }
                    }
                }, 13, 0, true);
                timePickerDialog.show();
            }
        });
 */
     }

   public void nya(View v){

        final Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

       System.out.println("Alarm_register::nya() hour="+hour+" minute="+minute);

        final TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Toast.makeText(Alarm_register.this, hourOfDay+"時"+minute+"分", Toast.LENGTH_LONG).show();
                        // 画面上のテキストビューに設定時刻を表示する
                        txt.setText(hourOfDay+":"+minute);

                    }
                }, hour, minute, true);
        timePickerDialog.show();
    }

    //開始ボタンを押した時
    public void aet(View view) {
        System.out.println("Alarm_register::aet()");
        write();

        //開始ボタンを押したら画面を隠す
        //finish();

        //タイマー起動
        //ベースコンテキストを取得
        Context context = getBaseContext();

        //アラームマネージャーの作成と設定
        manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        //インテントの作成   指定時刻にSubActivityを起動
        Intent intent = new Intent(context, Sub_alarm_Activity.class);

        //ペンディングインテントの作成
        pendingIntent = PendingIntent.getActivity(context,0,intent,0);

        //カレンダーの作成
        Calendar calendar = Calendar.getInstance();     //現在時間が取得される

        //指定時間をセット
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, x);
        calendar.set(Calendar.MINUTE, y);
        calendar.set(Calendar.SECOND, 0);

        System.out.println("Alarm_register::aet() x="+x+" y="+y);
        System.out.println("Alarm_register::aet() calendar.getTimeInMillis()="+calendar.getTimeInMillis());

        //指定間隔を分単位でする場合
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 1, pendingIntent);
        System.out.println("開始時------------------>" + pendingIntent);


        //指定間隔を予め用意されている間隔でする場合
        //manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
        //        AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);


    }
    //アプリケーション設定値登録
    private void write(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //プレーンテキストから値取得
        TextView jikoku=findViewById(R.id.textView8);
        sdata=jikoku.getText().toString();
        String ttt=(sdata.substring(0,2));
        String mmm=(sdata.substring(3));

        //TextView pct = findViewById(R.id.percent);
        //String text = pct.getText().toString();

        //数値に変換
        int tt = Integer.parseInt(ttt);
        int mm = Integer.parseInt(mmm);
        //int ps = Integer.parseInt(text);

        //SharedPreferencesに保存
        editor.putInt("SET_TIME", tt);
        editor.putInt("SET_Minutes", mm);
        //editor.putInt("percent", ps);

        //実際の保存
        editor.apply();

        read();

    }

    //アプリケーション設定値取得
    private void read() {

        //値を読み出す
        SharedPreferences pref  = PreferenceManager.getDefaultSharedPreferences(this);

        //SharedPreferencesからの呼び出し時刻
        int time = pref.getInt("SET_TIME", 0);
        int Minutes = pref.getInt("SET_Minutes", 0);

        //バッテリーボーダーラインの呼び出し
        //int percent = pref.getInt("percent", 40);


        x=time;
        y=Minutes;

        //テキストにセット
        String sjikoku = format(Locale.JAPANESE, "%02d:%02d", x, y);
        TextView textView = findViewById(R.id.textView8);
        textView.setText(sjikoku);

        //バッテリーボーダーラインを文字列に変換
        //String pp = Integer.valueOf(percent).toString();
        //TextView spct = findViewById(R.id.percent);
        //spct.setText(pp);

    }
}