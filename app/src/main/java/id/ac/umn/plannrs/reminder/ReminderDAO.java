package id.ac.umn.plannrs.reminder;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface ReminderDAO {
    @Query("SELECT * FROM tblReminder")
    LiveData<List<ReminderDB>> getAllReminder();

    @Query("DELETE FROM tblReminder WHERE strftime('%Y%m%d%H%M',datetime('now','localtime')) >= strftime('%Y%m%d%H%M%S', date||time)")
    void deleteAlarm();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ReminderDB reminder);

    @Delete
    void delete(ReminderDB reminder);

    @Update
    void update(ReminderDB reminder);
}