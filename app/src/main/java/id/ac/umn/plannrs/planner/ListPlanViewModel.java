package id.ac.umn.plannrs.planner;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ListPlanViewModel extends AndroidViewModel {
    private ListPlanRepository taskRepository;
    private LiveData<List<ListPlan>> listTask;
    private LiveData<List<ListPlan>> todoTask;
    private LiveData<List<ListPlan>> inProgressTask;
    private LiveData<List<ListPlan>> completedTask;
    public ListPlanViewModel(@NonNull Application application) {
        super(application);
        taskRepository = new ListPlanRepository(application);
        listTask = taskRepository.getListTask();
        todoTask = taskRepository.getTodoTask();
        inProgressTask = taskRepository.getInProgressTaskTask();
        completedTask = taskRepository.getCompletedTaskTask();
    }


    public LiveData<List<ListPlan>> getListTask(){return this.listTask;}
    public LiveData<List<ListPlan>> getTodoTask(){return this.todoTask;}
    public LiveData<List<ListPlan>> getInProgressTask(){return this.inProgressTask;}
    public LiveData<List<ListPlan>> getCompletedTask(){return this.completedTask;}
    public void insert(ListPlan task){taskRepository.insert(task);}
    public void delete(ListPlan task){taskRepository.delete(task);}
    public void update(ListPlan task){taskRepository.update(task);}
}
