package com.example.uptoskills;

import static android.app.Activity.RESULT_OK;
import static android.text.Html.fromHtml;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;

//import androidx.activity.result.ActivityResultCallerLauncher;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uptoskills.ui.signout.resume_data;
import com.example.uptoskills.ui.signout.templates;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.CancellationException;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.Sequence;
import kotlinx.coroutines.ChildHandle;
import kotlinx.coroutines.ChildJob;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.InternalCoroutinesApi;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.selects.SelectClause0;

public class JobViewActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> filePickerLauncher;
    private Dialog customDialog;
    private TextView tvSelectedFile;
    private EditText editName, editEmail, editPassingYear, editCity, editMobileNumber, editQualification, editCollegeName, editDOB, editAadharNumber;
    private Button btnChooseFile;
    private static final int REQUEST_CODE_FILE_PICKER = 1001;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobview);

        // Initialize the ActivityResultLauncher
        filePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null) {
                                Uri fileUri = data.getData();
                                // Handle the selected file
                                tvSelectedFile.setText(getFileName(fileUri));
                            }
                        }
                    }
                }
        );

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(JobViewActivity.this, ResumeDataActivity.class));
            }
        });

        TextView location = findViewById(R.id.loca);
        TextView post = findViewById(R.id.post);
        TextView salary = findViewById(R.id.sala);
        TextView jobname = findViewById(R.id.jobname);
        TextView main = findViewById(R.id.maintext);
        Button apply = findViewById(R.id.app);
        customDialog = new Dialog(JobViewActivity.this);
        tvSelectedFile = customDialog.findViewById(R.id.tvSelectedFile);

        // Initialize JobDatabase and Job
        //noinspection InstantiationOfUtilityClass
        jobdatabase jobDatabase = new jobdatabase();

        job job = new job();
        com.example.uptoskills.job.vlog_position = 0; // Set the vlog_position to a valid index

        location.setText(jobdatabase.location.get(com.example.uptoskills.job.vlog_position));
        post.setText(jobdatabase.date.get(com.example.uptoskills.job.vlog_position).substring(0, 10));
        salary.setText(jobdatabase.salary.get(com.example.uptoskills.job.vlog_position) + " INR / Month");
        jobname.setText(jobdatabase.title.get(com.example.uptoskills.job.vlog_position));

        String htmlCode = jobdatabase.innerdata.get(com.example.uptoskills.job.vlog_position);
        main.setText(HtmlCompat.fromHtml(htmlCode, HtmlCompat.FROM_HTML_MODE_LEGACY) + "\n\n\n\n");

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set the custom layout to the dialog
                customDialog.setContentView(R.layout.dialogbox);

                // Find the views in the custom layout
                editName = customDialog.findViewById(R.id.editName);
                editEmail = customDialog.findViewById(R.id.editEmail);
                editPassingYear = customDialog.findViewById(R.id.editPassingYear);
                editCity = customDialog.findViewById(R.id.editCity);
                editMobileNumber = customDialog.findViewById(R.id.editMobileNumber);
                editQualification = customDialog.findViewById(R.id.editQualification);
                editCollegeName = customDialog.findViewById(R.id.editCollegeName);
                editDOB = customDialog.findViewById(R.id.editDOB);
                editAadharNumber = customDialog.findViewById(R.id.editAadharNumber);
                btnChooseFile = customDialog.findViewById(R.id.btnChooseFile);

                btnChooseFile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Open the file picker
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("application/*");  // Set the file type filter, you can modify it based on your desired file types
                        filePickerLauncher.launch(intent);
                    }
                });

                Button btnSubmit = customDialog.findViewById(R.id.btnSubmit);
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Retrieve the values entered in the EditText fields
                        String name = editName.getText().toString();
                        String email = editEmail.getText().toString();
                        String passingYear = editPassingYear.getText().toString();
                        String city = editCity.getText().toString();
                        String mobileNumber = editMobileNumber.getText().toString();
                        String qualification = editQualification.getText().toString();
                        String collegeName = editCollegeName.getText().toString();
                        String dob = editDOB.getText().toString();
                        String aadharNumber = editAadharNumber.getText().toString();
                    }
                });

                customDialog.show();
            }
        });
    }

    private String getFileName(Uri uri) {
        String fileName = "";
        if (uri != null) {
            if (Objects.equals(uri.getScheme(), "content")) {
                try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                    if (cursor != null && cursor.moveToFirst()) {
                        int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                        if (nameIndex != -1) {
                            fileName = cursor.getString(nameIndex);
                        }
                    }
                }
            } else if (Objects.equals(uri.getScheme(), "file")) {
                fileName = new File(uri.getPath()).getName();
            }
        }
        return fileName;
    }
}
