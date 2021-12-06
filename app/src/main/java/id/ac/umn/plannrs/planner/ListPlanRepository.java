package id.ac.umn.plannrs.planner;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import id.ac.umn.plannrs.PlannrsRoomDatabase;

public class ListPlanRepository {
    private ListPlanDAO daoPlanner;
    private LiveData<List<ListPlan>> listTask;
    private LiveData<List<ListPlan>> todoTask;
    private LiveData<List<ListPlan>> inProgressTask;
    private LiveData<List<ListPlan>> completedTask;

    ListPlanRepository(Application app){
        PlannrsRoomDatabase db = PlannrsRoomDatabase.getDatabase(app);
        daoPlanner = db.daoPlanner();
        listTask = daoPlanner.getAllTask();
        todoTask = daoPlanner.getTodoTask();
        inProgressTask = daoPlanner.getInProgressTask();
        completedTask = daoPlanner.getCompletedTask();
    }

    LiveData<List<ListPlan>> getListTask(){return this.listTask;}
    LiveData<List<ListPlan>> getTodoTask(){return this.todoTask;}
    LiveData<List<ListPlan>> getInProgressTaskTask(){return this.inProgressTask;}
    LiveData<List<ListPlan>> getCompletedTaskTask(){return this.completedTask;}
    public void insert(ListPlan task){new insertAsyncTask(daoPlanner).execute(task);}
    public void delete(ListPlan task){new deleteAsyncTask(daoPlanner).execute(task);}
    public void update(ListPlan task){new updateAsyncTask(daoPlanner).execute(task);}
    private static class insertAsyncTask extends
            AsyncTask<ListPlan, Void, Void> {
        private ListPlanDAO asyncDaoPlanner;
        insertAsyncTask(ListPlanDAO dao){
            this.asyncDaoPlanner = dao;
        }
        @Override
        protected Void doInBackground(final ListPlan... task){
            asyncDaoPlanner.insert(task[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends
            AsyncTask<ListPlan, Void, Void>{
        private ListPlanDAO asyncDaoPlanner;
        deleteAsyncTask(ListPlanDAO dao){
            this.asyncDaoPlanner = dao;
        }
        @Override
        protected Void doInBackground(final ListPlan... tasks){
            asyncDaoPlanner.delete(tasks[0]);
            return null;
        }
    }
    private static class updateAsyncTask extends
            AsyncTask<ListPlan, Void, Void>{
        private ListPlanDAO asyncDaoPlanner;
        updateAsyncTask(ListPlanDAO dao){
            this.asyncDaoPlanner = dao;
        }
        @Override
        protected Void doInBackground(final ListPlan... tasks){
            asyncDaoPlanner.update(tasks[0]);
            return null;
        }
    }
}
