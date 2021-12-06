package id.ac.umn.plannrs.reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import id.ac.umn.plannrs.R;
import id.ac.umn.plannrs.Reminder;

public class AddReminder extends AppCompatActivity {
    Button mSubmitbtn, mDatebtn, mTimebtn, mExitbtn;
    EditText mTitledit, mDescedit;
    String timeTonotify, dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        mTitledit = (EditText) findViewById(R.id.editTitle);
        mDescedit = (EditText) findViewById(R.id.editDesc);
        mDatebtn = (Button) findViewById(R.id.btnDate);                                             //assigned all the material reference to get and set data
        mTimebtn = (Button) findViewById(R.id.btnTime);
        mSubmitbtn = (Button) findViewById(R.id.btnSubmit);
        mExitbtn = (Button) findViewById(R.id.btnExit);


        mTimebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTime();                                                                       //when we click on the choose time button it calls the select time method
            }
        });

        mDatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate();
            }                                        //when we click on the choose date button it calls the select date method
        });

        mExitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }                                        //when we click on the choose date button it calls the select date method
        });

        mSubmitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = mTitledit.getText().toString().trim();                               //access the data form the input field
                String desc = mDescedit.getText().toString().trim();
                String date = mDatebtn.getText().toString().trim();                                 //access the date form the choose date button
                String time = mTimebtn.getText().toString().trim();                                 //access the time form the choose time button

                if (title.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Enter text", Toast.LENGTH_SHORT).show();   //shows the toast if input field is empty
                } else {
                    if (time.isEmpty() || date.isEmpty()) {                                               //shows toast if date and time are not selected
                        Toast.makeText(getApplicationContext(), "Please select date and time", Toast.LENGTH_SHORT).show();
                    } else {
                        processinsert(title, date, time, desc, dates);
                    }
                }
            }
        });
    }


    private void processinsert(String title, String date, String time, String desc, String dates) {
        ReminderDB reminder = new ReminderDB(title, date, time, desc);
        Intent newIntent = new Intent();
        newIntent.putExtra("REMINDER",reminder);
        newIntent.putExtra("DATE",dates);
        newIntent.putExtra("TIMENOTIFY",timeTonotify);
        setResult(RESULT_OK, newIntent);
        finish();
    }

    private void selectTime() {                                                                     //this method performs the time picker task
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                timeTonotify = i + ":" + i1;                                                        //temp variable to store the time to set alarm
                if(i < 10){
                    mTimebtn.setText("0"+ i + ":"+i1);
                    if(i1<10){
                        mTimebtn.setText("0"+ i + ":0"+i1);
                    }
                }else{
                    mTimebtn.setText(i + ":"+i1);
                    if(i1<10){
                        mTimebtn.setText(i + ":0"+i1);
                    }
                }

            }
        }, hour, minute, true);
        timePickerDialog.show();
    }

    private void selectDate() {                                                                     //this method performs the date picker task
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                dates = (day + "-" + (month + 1) + "-" + year);
                if(day < 10){
                    mDatebtn.setText(year + "-" + (month + 1) + "-0"+ day);
                    if(month < 9){
                        mDatebtn.setText(year + "-0" + (month + 1) + "-0"+ day);
                    }
                }
                else{
                    mDatebtn.setText(year + "-" + (month + 1) + "-"+ day);
                }

            }
        }, year, month, day);

        datePickerDialog.show();
    }
    public String FormatTime(int hour, int minute) {                                                //this method converts the time into 12hr farmat and assigns am or pm

        String time;
        time = "";
        String formattedMinute;

        if (minute / 10 == 0) {
            formattedMinute = "0" + minute;
        } else {
            formattedMinute = "" + minute;
        }


        if (hour == 0) {
            time = "12" + ":" + formattedMinute + " AM";
        } else if (hour < 12) {
            time = hour + ":" + formattedMinute + " AM";
        } else if (hour == 12) {
            time = "12" + ":" + formattedMinute + " PM";
        } else {
            int temp = hour - 12;
            time = temp + ":" + formattedMinute + " PM";
        }


        return time;
    }
}