package id.ac.umn.plannrs.reminder;

import android.os.Debug;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "tblReminder")
public class ReminderDB implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "time")
    private String time;
    @ColumnInfo(name = "description")
    private String desc;


    public ReminderDB(String title, String date, String time, String desc){
        this.title = title;
        this.date = date;
        this.time = time;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc(){ return desc;}

    public void setDesc(String description){ this.desc = description;}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}