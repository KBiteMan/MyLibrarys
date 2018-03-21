package date.bitman.mylibrarys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import date.bitman.company_lib.calendar.CalendarView;
import date.bitman.company_lib.time.CustomDatePicker;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author 张禹
 * @date 2018-01-23 14:05
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.btn);
        final TextView timeView = (TextView) findViewById(R.id.time);
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDatePicker picker = new CustomDatePicker(MainActivity.this, new CustomDatePicker.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        timeView.setText(time);
                    }
                }, "2000-01-01 00:00", "2050-12-31 23:59");
                picker.showSpecificTime(CustomDatePicker.ALL);
                picker.setIsLoop(true);
                picker.show(format.format(System.currentTimeMillis()));
            }
        });

    }

}
