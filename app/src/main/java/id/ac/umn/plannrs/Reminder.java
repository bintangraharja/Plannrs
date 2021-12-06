package id.ac.umn.plannrs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import id.ac.umn.plannrs.reminder.AddReminder;
import id.ac.umn.plannrs.reminder.AlarmBroadcast;
import id.ac.umn.plannrs.reminder.ReminderAdapter;
import id.ac.umn.plannrs.reminder.ReminderDB;
import id.ac.umn.plannrs.reminder.ReminderViewModel;

public class Reminder extends AppCompatActivity {
    FloatingActionButton mCreateRem;
    RecyclerView mRecyclerview;
    ReminderViewModel reminderVM;
    private static final int REQUEST_TAMBAH = 1;
    AlarmManager am;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_reminder); // get the reference of Toolbar
        setSupportActionBar(toolbar); // Setting/replace toolbar as the ActionBar
        getSupportActionBar().setDisplayShowTitleEnabled(false); // hide the current title from the Toolbar

        mCreateRem = (FloatingActionButton) findViewById(R.id.create_reminder);
        mCreateRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Reminder.this, AddReminder.class);
                startActivityForResult(intent,REQUEST_TAMBAH);
            }
        });
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerView);
        reminderVM = ViewModelProviders.of(this).get(ReminderViewModel.class);
        reminderVM.deleteAlarm();
        final ReminderAdapter adapter = new ReminderAdapter(this, reminderVM, am);
        mRecyclerview.setAdapter(adapter);

        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        reminderVM.getDaftarRem().observe(this, new Observer<List<ReminderDB>>() {
            @Override
            public void onChanged(List<ReminderDB> reminders) {
                adapter.setDaftarRem(reminders);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            ReminderDB reminder = (ReminderDB) data.getSerializableExtra("REMINDER");
            String dates = data.getStringExtra("DATE");
            if (requestCode == REQUEST_TAMBAH) {
                reminderVM.insert(reminder);
            }
            setAlarm(reminder.getTitle(),dates,reminder.getTime(),reminder.getDesc(), data.getStringExtra("TIMENOTIFY"));
        }
        mRecyclerview.getAdapter().notifyDataSetChanged();
    }

    private void setAlarm(String text, String date, String time, String desc, String timeTonotify) {

        Intent intent = new Intent(getApplicationContext(), AlarmBroadcast.class);
        intent.putExtra("event", text);
        intent.putExtra("time", date);
        intent.putExtra("date", time);
        intent.putExtra("desc",desc);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), 1, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        String dateandtime = date + " " + timeTonotify;
        DateFormat formatter = new SimpleDateFormat("d-M-yyyy hh:mm");
        try {
            Date date1 = formatter.parse(dateandtime);
            am.set(AlarmManager.RTC_WAKEUP, date1.getTime(), pendingIntent);
            Toast.makeText(getApplicationContext(), "Alarm", Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}