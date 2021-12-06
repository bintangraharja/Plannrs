package id.ac.umn.plannrs.fastaction;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class FastActionViewModel extends AndroidViewModel {
    private FastActionRepository faRepository;

    private LiveData<List<FastAction>> daftarFA;
    public FastActionViewModel(@NonNull Application application) {
        super(application);
        faRepository = new FastActionRepository(application);
        daftarFA = faRepository.getDaftarFA();
    }
    public LiveData<List<FastAction>> getDaftarFA(){
        return this.daftarFA;
    }
    public void insert(FastAction fa) {
        faRepository.insert(fa);
    }
    public void deleteAll() {
        faRepository.deleteAll();
    }
    public void delete(FastAction fa) {
        faRepository.delete(fa);
    }
    public void update(FastAction fa) {faRepository.update(fa); }

}
