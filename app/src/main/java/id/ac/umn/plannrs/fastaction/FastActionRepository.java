package id.ac.umn.plannrs.fastaction;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import id.ac.umn.plannrs.PlannrsRoomDatabase;

public class FastActionRepository {

    private FastActionDAO daoFastAction;
    private LiveData<List<FastAction>> daftarFA;

    FastActionRepository(Application app){
        PlannrsRoomDatabase db = PlannrsRoomDatabase.getDatabase(app);
        daoFastAction = db.daoFastAction();
        daftarFA = daoFastAction.getAllFastAction();
    }

    LiveData<List<FastAction>> getDaftarFA(){
        return this.daftarFA;
    }

    public void insert(FastAction fa){
        new insertAsyncTask(daoFastAction).execute(fa);
    }

    public void deleteAll(){
        new deleteAllAsyncTask(daoFastAction).execute();
    }

    public void delete(FastAction fa) {
        new deleteAsyncTask(daoFastAction).execute(fa);
    }

    public void update(FastAction fa) { new updateAsyncTask(daoFastAction).execute(fa); }

    private static class insertAsyncTask extends AsyncTask<FastAction, Void, Void> {
        private FastActionDAO asyncDaoFastAction;
        insertAsyncTask(FastActionDAO dao){
            this.asyncDaoFastAction = dao;
        }
        @Override
        protected Void doInBackground(final FastAction... fastaction){
            asyncDaoFastAction.insert(fastaction[0]);
            return null;
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private FastActionDAO asyncDaoFastAction;
        deleteAllAsyncTask(FastActionDAO dao){
            this.asyncDaoFastAction = dao;
        }
        @Override
        protected Void doInBackground(final Void... voids) {
            asyncDaoFastAction.deleteAll();
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<FastAction, Void, Void> {
        private FastActionDAO asyncDaoFastAction;
        deleteAsyncTask(FastActionDAO dao){
            this.asyncDaoFastAction = dao;
        }
        @Override
        protected Void doInBackground(final FastAction... fastaction){
            asyncDaoFastAction.delete(fastaction[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<FastAction, Void, Void>{
        private FastActionDAO asyncDaoFastAction;
        updateAsyncTask(FastActionDAO dao){
            this.asyncDaoFastAction = dao;
        }
        @Override
        protected Void doInBackground(final FastAction... fastaction){
            asyncDaoFastAction.update(fastaction[0]);
            return null;
        }
    }
}
