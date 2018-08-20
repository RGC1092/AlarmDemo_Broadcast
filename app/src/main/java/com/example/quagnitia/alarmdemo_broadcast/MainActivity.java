package com.example.quagnitia.alarmdemo_broadcast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TimePicker timepicker;
    ToggleButton tbToggle;
    AlarmManager alarmManager;
    Context context = this;
    PendingIntent pendingIntent;
    Button btnStop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timepicker = findViewById(R.id.timepicker);
        tbToggle = findViewById(R.id.tbToggle);
        btnStop=findViewById(R.id.btnStop);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        final Intent i=getIntent();

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG","OnStop:Alarm is stop");

                Intent intent = new Intent(context, AlarmReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(context, 1234, intent, PendingIntent.FLAG_UPDATE_CURRENT|Intent.FILL_IN_DATA);
                alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);
                Ringtone r= AlarmReceiver.ringtone;
                r.stop();
                Vibrator vb=AlarmReceiver.v;
                vb.cancel();

                // int ring=i.getExtras().getInt("RING");
                Toast.makeText(context,"Alarm is Stop",Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void onToggleclick(View view) {
        long time;
        if (((ToggleButton) view).isChecked()) {
            Toast.makeText(MainActivity.this, "ALARM ON", Toast.LENGTH_SHORT).show();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, timepicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, timepicker.getCurrentMinute());
            Intent intent = new Intent(this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT|  Intent.FILL_IN_DATA);

            time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
            if (System.currentTimeMillis() > time) {
                if (calendar.AM_PM == 0)
                    time = time + (1000 * 60 * 60 * 12);
                else
                    time = time + (1000 * 60 * 60 * 24);
            }
            alarmManager.set(AlarmManager.RTC_WAKEUP, time,  pendingIntent);
            // alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        } else {
            alarmManager.cancel(pendingIntent);
            Toast.makeText(MainActivity.this, "ALARM OFF", Toast.LENGTH_SHORT).show();
        }

    }
/*
       public void onStop(View view) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1234, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

    }
*/

}
