package date.bitman.company_lib.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.Toast;

public class BatteryReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equalsIgnoreCase(Intent.ACTION_BATTERY_CHANGED)) {

            //电池状态
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            switch (status) {
                //正在充电
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    Toast.makeText(context, "正在充电", Toast.LENGTH_SHORT).show();
                    break;
                default:
            }
        }
    }
}
