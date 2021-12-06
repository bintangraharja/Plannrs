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

public class InProgressAdapter extends RecyclerView.Adapter<InProgressAdapter.InProgressVH> {
    private final LayoutInflater mInflater;
    private List<ListPlan> listTask;
    private  ListPlanViewModel listPlanViewModel;

    public InProgressAdapter(Context context, ListPlanViewModel listPlanViewModel) {
        mInflater = LayoutInflater.from(context);
        this.listPlanViewModel = listPlanViewModel;
    }

    @NonNull
    @Override
    public InProgressVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.planner_inprogress_layout,
                parent,false);
        return new InProgressVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InProgressVH holder, int position) {
        if(listTask != null){
            ListPlan task = listTask.get(position);
            holder.inProgressTask.setText(task.getTask());
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
                lp.setStatus("Completed");
                listPlanViewModel.update(lp);
            }
        });
        holder.btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ListPlan lp = listTask.get(holder.getAdapterPosition());
                lp.setStatus("Todo");
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
    public void setInProgressTask(List<ListPlan> tasks){
        listTask = tasks;
        notifyDataSetChanged();
    }

    public class InProgressVH extends RecyclerView.ViewHolder{
        private final TextView inProgressTask;
        private ImageButton btnUpdate, btnBack, btnDel;
        public InProgressVH(@NonNull View itemView) {
            super(itemView);
            this.inProgressTask = itemView.findViewById(R.id.inProgressTask);
            this.btnUpdate = itemView.findViewById(R.id.btnUpdate);
            this.btnDel = itemView.findViewById(R.id.btnDel);
            this.btnBack = itemView.findViewById(R.id.btnBack);
        }
    }
}
