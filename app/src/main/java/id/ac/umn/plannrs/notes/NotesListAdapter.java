package id.ac.umn.plannrs.notes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.ac.umn.plannrs.R;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NotesViewHolder> {
    private final LayoutInflater mInflater;
    private List<Notes> listNotes;
    private Context tContext;
    private NotesViewModel nvm;

    public NotesListAdapter(Context context, NotesViewModel nvm) {
        tContext = context;
        this.nvm = nvm;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_notes, parent, false);
        return new NotesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        if(listNotes != null) {
            Notes notes = listNotes.get(position);
            holder.tvTitleNotes.setText(notes.getTitleNotes());
        }

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notes notes = listNotes.get(holder.getAdapterPosition());
                Intent edit = new Intent(tContext, NotesEdit.class);
                edit.putExtra("NOTES", notes);
                tContext.startActivity(edit);
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notes notes = listNotes.get(holder.getAdapterPosition());
                AlertDialog.Builder builder = new AlertDialog.Builder(tContext);
                builder.setCancelable(true);
                builder.setTitle("Do you want to delete '"+notes.getTitleNotes()+"' ?");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nvm.delete(notes);
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listNotes != null) {
            return listNotes.size();
        } else {
            return 0;
        }
    }

    public void setListNotes(List<Notes> notess){
        listNotes = notess;
        notifyDataSetChanged();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitleNotes;
        private ImageButton editButton, deleteButton;
        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvTitleNotes = itemView.findViewById(R.id.tvTitleNotes);
            this.editButton = itemView.findViewById(R.id.editButton);
            this.deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
