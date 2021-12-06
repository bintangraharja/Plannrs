package id.ac.umn.plannrs.reminder;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ReminderViewModel extends AndroidViewModel {
    private ReminderRepository remRepository;

    private LiveData<List<ReminderDB>> daftarReminder;
    public ReminderViewModel(@NonNull Application application) {
        super(application);
        remRepository = new ReminderRepository(application);
        daftarReminder = remRepository.getDaftarRem();
    }
    public LiveData<List<ReminderDB>> getDaftarRem(){
        return this.daftarReminder;
    }
    public void insert(ReminderDB reminder) {
        remRepository.insert(reminder);
    }
    public void delete(ReminderDB reminder) {
        remRepository.delete(reminder);
    }
    public void deleteAlarm(){ remRepository.deleteAlarm();}
    public void update(ReminderDB reminder) {remRepository.update(reminder); }

}
