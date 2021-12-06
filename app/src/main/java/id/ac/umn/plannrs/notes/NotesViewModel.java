package id.ac.umn.plannrs.notes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {
    private NotesRepository notesRepository;
    private LiveData<List<Notes>> listNotes;
    public NotesViewModel(@NonNull Application application) {
        super(application);
        notesRepository = new NotesRepository(application);
        listNotes = notesRepository.getListNotes();
    }

    public LiveData<List<Notes>> getListNotes() {
        return this.listNotes;
    }

    public void insert(Notes notes) {
        notesRepository.insert(notes);
    }

    public void deleteAll() {
        notesRepository.deleteAll();
    }

    public void delete(Notes notes) {
        notesRepository.delete(notes);
    }

    public void update(Notes notes) {
        notesRepository.update(notes);
    }
}
