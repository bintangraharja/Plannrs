package id.ac.umn.plannrs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.hotspot2.pps.Credential;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import java.util.Collections;

import ir.androidexception.roomdatabasebackupandrestore.Backup;
import ir.androidexception.roomdatabasebackupandrestore.OnWorkFinishListener;
import ir.androidexception.roomdatabasebackupandrestore.Restore;


public class MainActivity extends AppCompatActivity {
    DriveServiceHelper driveServiceHelper;
    private ImageButton btnPlanner, btnNotes, btnFastAction, btnReminder;
    private Button btnBackup,btnRestore;
    private int EXTERNAL_STORAGE_PERMISSION_CODE = 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT>22){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INTERNET, Manifest.permission.READ_CONTACTS},EXTERNAL_STORAGE_PERMISSION_CODE);
        }
        requestSignIn();


        btnPlanner = findViewById(R.id.btnPlanner);
        btnNotes = findViewById(R.id.btnNotes);
        btnFastAction = findViewById(R.id.btnFastAction);
        btnReminder = findViewById(R.id.btnReminder);
        btnBackup = findViewById(R.id.backup);
        btnRestore = findViewById(R.id.restore);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_home); // get the reference of Toolbar
        setSupportActionBar(toolbar); // Setting/replace toolbar as the ActionBar
        getSupportActionBar().setDisplayShowTitleEnabled(false); // hide the current title from the Toolbar

        btnPlanner.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent plannerIntent = new Intent(MainActivity.this, Planner.class);
                startActivity(plannerIntent);
            }
        });
        btnReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reminderIntent = new Intent(MainActivity.this, Reminder.class);
                startActivity(reminderIntent);
            }
        });
        btnNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent notesIntent = new Intent(MainActivity.this, Noter.class);
                startActivity(notesIntent);
            }
        });
        btnFastAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fastActionIntent = new Intent(MainActivity.this, FastActionMain.class);
                startActivity(fastActionIntent);
            }
        });

        btnBackup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Do you want to backup your data?");
                builder.setMessage("Your file will be stored in\n"+Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download \n and will be upload to Google Drive");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Backup.Init()
                                .database(PlannrsRoomDatabase.getDatabase(getApplicationContext()))
                                .path(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download")
                                .fileName("PlannrsBackup.txt")
                                .onWorkFinishListener(new OnWorkFinishListener() {
                                    @Override
                                    public void onFinished(boolean success, String message) {
                                        uploadGdrive();
                                    }
                                })
                                .execute();

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

        btnRestore.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Do you want to restore data?");
                builder.setMessage("Your current data will be lost. \nMake sure your file is in\n"+Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download/PlannrsBackup.txt");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Restore.Init()
                                .database(PlannrsRoomDatabase.getDatabase(getApplicationContext()))
                                .backupFilePath(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/PlannrsBackup.txt")
                                .onWorkFinishListener(new OnWorkFinishListener() {
                                    @Override
                                    public void onFinished(boolean success, String message) {
                                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .execute();
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

    private void uploadGdrive() {
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Uploading to Google Drive");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/PlannrsBackup.txt";
        driveServiceHelper.createFileDB(filePath)
                .addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        progressDialog.dismiss();

                        Toast.makeText(getApplicationContext(),"Uploaded Succesfully",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"File Already Exist, Delete It First",Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void requestSignIn() {
        String serverClientId = getString(R.string.server_client_id);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.DRIVE_APPFOLDER))
                .requestServerAuthCode(serverClientId)
                .requestEmail()
                .build();


        GoogleSignInClient client = GoogleSignIn.getClient(this,gso);
        Intent signInIntent = client.getSignInIntent();
        startActivityForResult(signInIntent,400);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode)
        {
            case 400:
                if(resultCode == RESULT_OK){
                    handleSignInIntent(data);
                }else{
                    Toast.makeText(getApplicationContext(), "failed login 1", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void handleSignInIntent(Intent data) {
        GoogleSignIn.getSignedInAccountFromIntent(data)
                .addOnSuccessListener(new OnSuccessListener<GoogleSignInAccount>() {
                    @Override
                    public void onSuccess(GoogleSignInAccount googleSignInAccount) {
                        GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(MainActivity.this, Collections.singleton(Scopes.DRIVE_APPFOLDER));
                        credential.setSelectedAccount(googleSignInAccount.getAccount());

                        Drive googleDriveService = new Drive.Builder(
                                AndroidHttp.newCompatibleTransport(),
                                new GsonFactory(),
                                credential)
                                .setApplicationName("Plannrs")
                                .build();

                        driveServiceHelper = new DriveServiceHelper(googleDriveService);
                        Toast.makeText(getApplicationContext(), "success login", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}