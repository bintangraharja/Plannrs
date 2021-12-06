package id.ac.umn.plannrs.fastaction;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.ac.umn.plannrs.R;

public class FastActionListAdapter extends RecyclerView.Adapter<FastActionListAdapter.FastActionViewHolder>
{
    private final LayoutInflater mInflater;
    private List<FastAction> daftarFA;
    private FastActionViewModel faVM;
    public FastActionListAdapter(Context context, FastActionViewModel faVM){
        mInflater = LayoutInflater.from(context);
        this.faVM = faVM;
    }
    @NonNull
    @Override
    public FastActionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.fa_item_layout, parent,false);
        return new FastActionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FastActionViewHolder holder, int position) {
        if(daftarFA != null){
            FastAction fa = daftarFA.get(position);
            holder.tvTitle.setText(fa.getTitle());
            if("Complete".equals(fa.getStatus())){
                holder.rButton.setChecked(true);
                holder.tvTitle.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }else{
                holder.tvTitle.setPaintFlags(0);
                holder.rButton.setChecked(false);
            }
        }
        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FastAction fa = daftarFA.get(holder.getAdapterPosition());
                faVM.delete(fa);
            }
        });
        holder.rButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FastAction fa = daftarFA.get(holder.getAdapterPosition());
                if("Uncomplete".equals(fa.getStatus().toString())){
                    fa.setStatus("Complete");
                    faVM.update(fa);
                }else{
                    fa.setStatus("Uncomplete");
                    faVM.update(fa);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(daftarFA != null){
            return daftarFA.size();
        } else {
            return 0;
        }
    }

    public FastAction getFastActionAtPosition(int posisi){
        return daftarFA.get(posisi);
    }

    public void setDaftarFA(List<FastAction> fas){
        daftarFA = fas;
        notifyDataSetChanged();
    }

    class FastActionViewHolder extends RecyclerView.ViewHolder{
        private final TextView tvTitle;
        private ImageButton btnDel;
        private CheckBox rButton;
        public FastActionViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvTitle = itemView.findViewById(R.id.title);
            this.btnDel = itemView.findViewById(R.id.btnDel);
            this.rButton = itemView.findViewById(R.id.rButton);
        }
    }
}