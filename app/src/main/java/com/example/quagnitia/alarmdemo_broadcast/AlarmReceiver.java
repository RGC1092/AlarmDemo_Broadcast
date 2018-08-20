package com.example.quagnitia.alarmdemo_broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.widget.Toast;

/**
 * Created by hp on 21-May-18.
 */

public class AlarmReceiver extends BroadcastReceiver {

    static Ringtone ringtone;
    static Vibrator v;
    @Override
    public void onReceive(Context context, Intent intent) {
        // Set the alarm here.
        Toast.makeText(context, "Alarm! Wake up! Wake up!", Toast.LENGTH_LONG).show();
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();
        if (ringtone.isPlaying()) {
            v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            long[] pattern = {0, 1000, 1000};
            v.vibrate(pattern, 1);

        } else {
            v.cancel();
            ringtone.stop();
        }

           /* Intent i=new Intent(context,MainActivity.class);
            i.putExtra("RING", (Parcelable) ringtone);
            context.startActivity(i);*/


    }
}
