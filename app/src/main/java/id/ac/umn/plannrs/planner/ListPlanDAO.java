package id.ac.umn.plannrs.planner;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface ListPlanDAO {
    @Query("Select * FROM tblPlan")
    LiveData<List<ListPlan>> getAllTask();
    @Query("Select * FROM tblPlan WHERE  status == 'Todo'")
    LiveData<List<ListPlan>> getTodoTask();
    @Query("Select * FROM tblPlan WHERE  status == 'In Progress'")
    LiveData<List<ListPlan>> getInProgressTask();
    @Query("Select * FROM tblPlan WHERE  status == 'Completed'")
    LiveData<List<ListPlan>> getCompletedTask();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ListPlan listPlan);
    @Delete
    void delete(ListPlan listPlan);
    @Update
    void update(ListPlan listPlan);
}
