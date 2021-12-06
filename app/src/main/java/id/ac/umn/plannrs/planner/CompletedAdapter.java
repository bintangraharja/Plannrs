package id.ac.umn.plannrs.planner;

import android.content.Context;
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

public class CompletedAdapter extends RecyclerView.Adapter<CompletedAdapter.CompletedVH> {
    private final LayoutInflater mInflater;
    private List<ListPlan> listTask;
    private  ListPlanViewModel listPlanViewModel;
    public CompletedAdapter(Context context, ListPlanViewModel listPlanViewModel){
        mInflater = LayoutInflater.from(context);
        this.listPlanViewModel = listPlanViewModel;
    }
    @NonNull
    @Override
    public CompletedVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.planner_completed_layout,
                parent,false);
        return new CompletedVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedVH holder, int position) {
        if(listTask != null){
            ListPlan task = listTask.get(position);
            holder.completedTask.setText(task.getTask());
        }
        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListPlan lp = listTask.get(holder.getAdapterPosition());
                listPlanViewModel.delete(lp);
            }
        });
        holder.btnBack.setOnClickListener(new View.OnClickListener(){

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
    public void setCompletedTask(List<ListPlan> tasks){
        listTask = tasks;
        notifyDataSetChanged();
    }

    public class CompletedVH extends RecyclerView.ViewHolder {
        private final TextView completedTask;
        private ImageButton btnBack, btnDel;
        public CompletedVH(@NonNull View itemView) {
            super(itemView);
            this.completedTask = itemView.findViewById(R.id.completedTask);
            this.btnBack = itemView.findViewById(R.id.btnBack);
            this.btnDel = itemView.findViewById(R.id.btnDel);
        }
    }
}
