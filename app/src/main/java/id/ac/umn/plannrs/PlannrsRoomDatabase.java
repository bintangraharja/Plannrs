package id.ac.umn.plannrs;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import id.ac.umn.plannrs.fastaction.FastAction;
import id.ac.umn.plannrs.fastaction.FastActionDAO;
import id.ac.umn.plannrs.notes.Notes;
import id.ac.umn.plannrs.notes.NotesDAO;
import id.ac.umn.plannrs.planner.ListPlan;
import id.ac.umn.plannrs.planner.ListPlanDAO;
import id.ac.umn.plannrs.reminder.ReminderDAO;
import id.ac.umn.plannrs.reminder.ReminderDB;

@Database(entities = {Notes.class, ListPlan.class, FastAction.class, ReminderDB.class}, version = 1, exportSchema = false)
public abstract class PlannrsRoomDatabase extends RoomDatabase {
    public abstract ListPlanDAO daoPlanner();
    public abstract NotesDAO daoNotes();
    public abstract FastActionDAO daoFastAction();
    public abstract ReminderDAO daoReminder();
    private static id.ac.umn.plannrs.PlannrsRoomDatabase INSTANCE;

    public static PlannrsRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized(id.ac.umn.plannrs.PlannrsRoomDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder
                            (context.getApplicationContext(), PlannrsRoomDatabase.class, "dbPlannrs")
                            .addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
}