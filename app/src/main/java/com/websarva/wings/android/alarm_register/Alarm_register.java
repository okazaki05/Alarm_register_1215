package com.websarva.wings.android.alarm_register;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Alarm_register extends AppCompatActivity {

    // 時刻表示テキストビュー
    TextView txt = null;

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

        final TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Toast.makeText(Alarm_register.this, hourOfDay+"時"+minute+"分", Toast.LENGTH_LONG).show();
                        // 画面上のテキストビューに設定時刻を表示する
                        txt.setText(hourOfDay+"時"+minute+"分");

                    }
                }, hour, minute, true);
        timePickerDialog.show();
    }
}