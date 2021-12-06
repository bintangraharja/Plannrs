package id.ac.umn.plannrs.notes;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import id.ac.umn.plannrs.PlannrsRoomDatabase;

public class NotesRepository {
    private NotesDAO daoNotes;
    private LiveData<List<Notes>> listNotes;

    NotesRepository(Application app) {
        PlannrsRoomDatabase db = PlannrsRoomDatabase.getDatabase(app);
        daoNotes = db.daoNotes();
        listNotes = daoNotes.getAllNotes();
    }

    LiveData<List<Notes>> getListNotes() {
        return this.listNotes;
    }

    public void insert(Notes notes) {
        new insertAsyncTask(daoNotes).execute(notes);
    }

    public void deleteAll() {
        new deleteAllAsyncTask(daoNotes).execute();
    }

    public void delete(Notes notes) {
        new deleteAsyncTask(daoNotes).execute(notes);
    }

    public void update(Notes notes) {
        new updateAsyncTask(daoNotes).execute(notes);
    }

    private static class insertAsyncTask extends AsyncTask<Notes, Void, Void> {
        private NotesDAO asyncDaoNotes;
        insertAsyncTask(NotesDAO dao) {
            this.asyncDaoNotes = dao;
        }

        @Override
        protected Void doInBackground(final Notes... notes) {
            asyncDaoNotes.insert(notes[0]);
            return null;
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private NotesDAO asyncDaoNotes;
        deleteAllAsyncTask(NotesDAO dao) {
            this.asyncDaoNotes = dao;
        }

        @Override protected Void doInBackground(final Void... voids) {
            asyncDaoNotes.deleteAll();
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Notes, Void, Void> {
        private NotesDAO asyncDaoNotes;
        deleteAsyncTask(NotesDAO dao) {
            this.asyncDaoNotes = dao;
        }

        @Override protected Void doInBackground(final Notes... Notess) {
            asyncDaoNotes.delete(Notess[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Notes, Void, Void> {
        private NotesDAO asyncDaoNotes;
        updateAsyncTask(NotesDAO dao) {
            this.asyncDaoNotes = dao;
        }

        @Override protected Void doInBackground(final Notes... Notess) {
            asyncDaoNotes.update(Notess[0]);
            return null;
        }
    }
}
