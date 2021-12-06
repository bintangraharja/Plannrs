package id.ac.umn.plannrs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;
import id.ac.umn.plannrs.notes.Notes;
import id.ac.umn.plannrs.notes.NotesEdit;
import id.ac.umn.plannrs.notes.NotesListAdapter;
import id.ac.umn.plannrs.notes.NotesViewModel;

public class Noter extends AppCompatActivity {
    private RecyclerView rvNotes;
    private NotesViewModel notesVM;
    FloatingActionButton btnadd;
    private static final int REQUEST_ADD = 1;
    private static final int REQUEST_EDIT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_notes); // get the reference of Toolbar
        setSupportActionBar(toolbar); // Setting/replace toolbar as the ActionBar
        getSupportActionBar().setDisplayShowTitleEnabled(false); // hide the current title from the Toolbar

        btnadd = (FloatingActionButton) findViewById(R.id.btnadd);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(Noter.this, NotesEdit.class);
                startActivityForResult(addIntent, REQUEST_ADD);
            }
        });

        rvNotes = (RecyclerView) findViewById(R.id.rvNotes);
        notesVM = ViewModelProviders.of(this).get(NotesViewModel.class);
        final NotesListAdapter adapter = new NotesListAdapter(this, notesVM);
        rvNotes.setAdapter(adapter);
        rvNotes.setLayoutManager(new LinearLayoutManager(this));

        if(getIntent().hasExtra("EDIT")) {
            Notes notes = (Notes) getIntent().getSerializableExtra("EDIT");
            notesVM.update(notes);
            Toast.makeText(getApplicationContext(), "Your notes have been saved.", Toast.LENGTH_LONG).show();
            finish();
        }

        notesVM.getListNotes().observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notess) {
                adapter.setListNotes(notess);
            }
        });
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Notes notes = (Notes) data.getSerializableExtra("NOTES");
            if (reqCode == REQUEST_ADD) {
                notesVM.insert(notes);
            } else if (reqCode == REQUEST_EDIT) {
                notesVM.update(notes);
            }
        }
        rvNotes.getAdapter().notifyDataSetChanged();
    }
}