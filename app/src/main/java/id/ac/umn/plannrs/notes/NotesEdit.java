package id.ac.umn.plannrs.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import id.ac.umn.plannrs.Noter;
import id.ac.umn.plannrs.R;

public class NotesEdit extends AppCompatActivity {
    private EditText editTitle, editNotes;
    private ImageButton button_save;
    private Intent intent;
    private Notes noteEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_edit);
        intent = getIntent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_edit); // get the reference of Toolbar
        setSupportActionBar(toolbar); // Setting/replace toolbar as the ActionBar
        getSupportActionBar().setDisplayShowTitleEnabled(false); // hide the current title from the Toolbar

        editTitle = findViewById(R.id.editTitle);
        editNotes = findViewById(R.id.editNotes);
        if(intent.hasExtra("NOTES")) {
            noteEdit = (Notes) intent.getSerializableExtra("NOTES");
            editTitle.setText(noteEdit.getTitleNotes());
            editNotes.setText(noteEdit.getDetailNotes());
        }

        button_save = findViewById(R.id.button_save);
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mTitle = editTitle.getText().toString();
                String mDetail = editNotes.getText().toString();
                if(mTitle.length() <= 0) {
                    String message = "Semua harus diisi";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                } else if (mTitle.length() <= 15) {
                    if(noteEdit == null) {
                        Intent intentJawab = new Intent();
                        Notes notes = new Notes(mTitle, mDetail);
                        intentJawab.putExtra("NOTES", notes);
                        setResult(RESULT_OK, intentJawab);
                        finish();
                    } else {
                        Intent intentJawab = new Intent(getApplicationContext(), Noter.class);
                        noteEdit.setTitleNotes(mTitle);
                        noteEdit.setDetailNotes(mDetail);
                        intentJawab.putExtra("EDIT", noteEdit);
                        startActivity(intentJawab);
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Max 15 letters", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.topnav_notes_edit, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.button_save) {

        }
        return super.onOptionsItemSelected(item);
    }

}