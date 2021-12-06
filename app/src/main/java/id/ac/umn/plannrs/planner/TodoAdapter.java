package id.ac.umn.plannrs.planner;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import id.ac.umn.plannrs.R;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    private final LayoutInflater mInflater;
    private List<ListPlan> listTask;
    private  ListPlanViewModel listPlanViewModel;
    public TodoAdapter(Context context, ListPlanViewModel listPlanViewModel){
        mInflater = LayoutInflater.from(context);
        this.listPlanViewModel = listPlanViewModel;
    }
    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.planner_todo_layout,
                parent,false);
        return new TodoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoAdapter.TodoViewHolder holder, int position) {
        if(listTask != null){
            ListPlan task = listTask.get(position);
            holder.todoTask.setText(task.getTask());
        }
        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListPlan lp = listTask.get(holder.getAdapterPosition());
                listPlanViewModel.delete(lp);
            }
        });
        holder.btnUpdate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                ListPlan lp = listTask.get(holder.getAdapterPosition());
                lp.setStatus("In Progress");
                listPlanViewModel.update(lp);
            }
        });

    }
    @Override
    public int getItemCount() {
        if(listTask != null){
            return listTask.size();
        }else{
            return 0;
        }
    }
    public ListPlan getTaskPosition(int posisi){return listTask.get(posisi);}
    public void setTodoTask(List<ListPlan> tasks){
        listTask = tasks;
        notifyDataSetChanged();
    }


    class TodoViewHolder extends RecyclerView.ViewHolder{
        private final TextView todoTask;
        private ImageButton btnUpdate, btnDel;
        public TodoViewHolder(@NonNull View itemView){
            super(itemView);
            this.todoTask = itemView.findViewById(R.id.todoTask);
            this.btnUpdate = itemView.findViewById(R.id.btnUpdate);
            this.btnDel = itemView.findViewById(R.id.btnDel);
        }
    }

}
