package id.ac.umn.plannrs.fastaction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import id.ac.umn.plannrs.R;

public class DetilActivity extends AppCompatActivity {
    private EditText mAddTitle;
    Button saveBtn, cancelBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detil);

        mAddTitle = (EditText) findViewById(R.id.addTitle);
        saveBtn = (Button) findViewById(R.id.btnSubmit);
        cancelBtn = (Button) findViewById(R.id.btnExit);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentJawab = new Intent();
                setResult(RESULT_CANCELED,intentJawab);
                finish();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = mAddTitle.getText().toString().trim();
                Intent intentJawab = new Intent();
                FastAction fa = new FastAction(title, "Uncomplete");
                intentJawab.putExtra("FAST ACTION", fa);
                setResult(RESULT_OK, intentJawab);
                finish();
            }
        });
    }
}