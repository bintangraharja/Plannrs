package id.ac.umn.plannrs.fastaction;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FastActionDAO {
    @Query("SELECT * FROM tblFastAction")
    LiveData<List<FastAction>> getAllFastAction();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(FastAction fa);

    @Delete
    void delete(FastAction fa);

    @Query("DELETE FROM tblFastAction")
    void deleteAll();

    @Update
    void update(FastAction fa);
}