package id.ac.umn.plannrs.reminder;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import id.ac.umn.plannrs.R;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderVM>{
    private ReminderViewModel dataholder;
    private final LayoutInflater mInflater;
    private List<ReminderDB> daftarReminder;
    private Context context;
    private AlarmManager am;


    public ReminderAdapter(Context context, ReminderViewModel dataholder, AlarmManager alarm) {
        this.context = context;
        this.am = alarm;
        mInflater = LayoutInflater.from(context);
        this.dataholder = dataholder;
    }

    @NonNull
    @Override
    public ReminderVM onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_reminder_file, parent, false);  //inflates the xml file in recyclerview
        return new ReminderVM(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderVM holder, int position) {
        ReminderDB reminder = daftarReminder.get(position);


        holder.mTitle.setText("#" + reminder.getId() + " " + reminder.getTitle());                                 //Binds the single reminder objects to recycler view
        holder.mDate.setText(reminder.getDate());
        holder.mTime.setText(reminder.getTime());
        holder.mImageBtn.setId(reminder.getId());

        holder.mImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReminderDB reminder = daftarReminder.get(holder.getAdapterPosition());
                dataholder.delete(reminder);
                Intent intent = new Intent(context, AlarmBroadcast.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        context, 1, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                am.cancel(pendingIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(daftarReminder != null){
            return daftarReminder.size();
        }else {
            return 0;
        }
    }

    public void setDaftarRem(List<ReminderDB> rem){
        daftarReminder = rem;
        notifyDataSetChanged();
    }

    class ReminderVM extends RecyclerView.ViewHolder {

        TextView mTitle, mDate, mTime;
        ImageButton mImageBtn;

        public ReminderVM(@NonNull View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.txtTitle);                               //holds the reference of the materials to show data in recyclerview
            mDate = (TextView) itemView.findViewById(R.id.txtDate);
            mTime = (TextView) itemView.findViewById(R.id.txtTime);
            mImageBtn = (ImageButton) itemView.findViewById(R.id.imageButton);
        }
    }
}
