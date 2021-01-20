package net.senior.hr;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddCV extends AppCompatActivity {

    EditText etFullName, etbd, etMobile, etAddress;
    TextView select;
    Uri pdfUri;
    CircleImageView circleImageView;
    EditText etFaculty, etUnivers, etGradYear, etExper;
    public static int REQUEST_PHONE_STATE_PERMISION = 5;
    Uri photopath;
    CheckBox ch1, ch2, ch3, ch4, ch5, ch6, ch7, ch8;
    RadioButton male, female;
    String marital, gend;
    Spinner spinner;
    ArrayList<String> intrestedJobs;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<String> keywords;
    private ProgressDialog progressDialog;
    String id, username, mobile, file_url, bd, address, faculty, univers, exper, gradYear, photo;
    private String image_url;
    private StorageReference storageReference;
    private UploadTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cv);
        intrestedJobs = new ArrayList();
        etAddress = findViewById(R.id.address);
        male = findViewById(R.id.male);
        select = findViewById(R.id.uploadedFile);
        female = findViewById(R.id.female);
        etFullName = findViewById(R.id.fullName);
        etbd = findViewById(R.id.bd);
        etFaculty = findViewById(R.id.faculty);
        etUnivers = findViewById(R.id.university);
        etGradYear = findViewById(R.id.gradYear);
        etExper = findViewById(R.id.experince);
        ch1 = findViewById(R.id.chk1);
        ch2 = findViewById(R.id.chk2);
        ch3 = findViewById(R.id.chk3);
        ch4 = findViewById(R.id.chk4);
        ch5 = findViewById(R.id.chk5);
        ch6 = findViewById(R.id.chk6);
        ch7 = findViewById(R.id.chk7);
        ch8 = findViewById(R.id.chk8);
        circleImageView = findViewById(R.id.profile_picture);


        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON_TOUCH)
                        .setAspectRatio(1, 1)
                        .start(AddCV.this);
            }
        });
        etMobile = findViewById(R.id.mobile);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        spinner = findViewById(R.id.maritalStat);

        final List<String> maritals = new ArrayList();
        databaseReference.child("marital").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String state = dataSnapshot1.getKey();


                    maritals.add(state);

                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddCV.this, android.R.layout.simple_spinner_item, maritals);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        marital = (String) adapterView.getItemAtPosition(i);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                databaseReference.child("Employee").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getValue(EmployeeModel.class);
                        etMobile.setText(dataSnapshot.getValue(EmployeeModel.class).getEmail());
                        etFullName.setText(dataSnapshot.getValue(EmployeeModel.class).getUserName());
                        etbd.setText(dataSnapshot.getValue(EmployeeModel.class).getBd());
                        etAddress.setText(dataSnapshot.getValue(EmployeeModel.class).getAddress());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

        keywords = new ArrayList();
        databaseReference.child("keywords").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String word = dataSnapshot1.getKey();


                    keywords.add(word);

                }

                ch1.setText(keywords.get(0));
                ch2.setText(keywords.get(1));
                ch3.setText(keywords.get(2));
                ch4.setText(keywords.get(3));
                ch5.setText(keywords.get(4));
                ch6.setText(keywords.get(5));
                ch7.setText(keywords.get(6));
                ch8.setText(keywords.get(7));


                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddCV.this, android.R.layout.simple_spinner_item, maritals);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        marital = (String) adapterView.getItemAtPosition(i);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


                databaseReference.child("Employee").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getValue(EmployeeModel.class);
                        etMobile.setText(dataSnapshot.getValue(EmployeeModel.class).getMobile());
                        etFullName.setText(dataSnapshot.getValue(EmployeeModel.class).getUserName());
                        etbd.setText(dataSnapshot.getValue(EmployeeModel.class).getBd());
                        etAddress.setText(dataSnapshot.getValue(EmployeeModel.class).getAddress());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

        databaseReference.child("CV").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    etAddress.setText(dataSnapshot.getValue(CVModel.class).getAddress());
                    etbd.setText(dataSnapshot.getValue(CVModel.class).getBd());
                    etFullName.setText(dataSnapshot.getValue(CVModel.class).getFullName());
                    etExper.setText(dataSnapshot.getValue(CVModel.class).getFaculty());
                    etFaculty.setText(dataSnapshot.getValue(CVModel.class).getExperience());
                    etGradYear.setText(dataSnapshot.getValue(CVModel.class).getGradYear());
                    etUnivers.setText(dataSnapshot.getValue(CVModel.class).getUniversity());
                    etMobile.setText(dataSnapshot.getValue(CVModel.class).getMobile());
                    if (dataSnapshot.getValue(CVModel.class).getFileUrl() != null) {
                        select.setText(dataSnapshot.getValue(CVModel.class).getFileUrl());

                    }
                    if (dataSnapshot.getValue(CVModel.class).getIntrestedJobs() != null) {
                        if (dataSnapshot.getValue(CVModel.class).getIntrestedJobs().contains("Android")) {
                            ch1.setChecked(true);
                        }
                        if (dataSnapshot.getValue(CVModel.class).getIntrestedJobs().contains("Angular")) {
                            ch2.setChecked(true);
                        }
                        if (dataSnapshot.getValue(CVModel.class).getIntrestedJobs().contains("Java")) {
                            ch3.setChecked(true);
                        }
                        if (dataSnapshot.getValue(CVModel.class).getIntrestedJobs().contains("MCSA")) {
                            ch4.setChecked(true);
                        }
                        if (dataSnapshot.getValue(CVModel.class).getIntrestedJobs().contains("Node Js")) {
                            ch5.setChecked(true);
                        }
                        if (dataSnapshot.getValue(CVModel.class).getIntrestedJobs().contains("Oracle")) {
                            ch6.setChecked(true);
                        }
                        if (dataSnapshot.getValue(CVModel.class).getIntrestedJobs().contains("PHP")) {
                            ch7.setChecked(true);
                        }
                        if (dataSnapshot.getValue(CVModel.class).getIntrestedJobs().contains("React")) {
                            ch8.setChecked(true);
                        }
                    }
                    Picasso.get().load(dataSnapshot.getValue(CVModel.class).getPhoto()).into(circleImageView);
                    image_url = dataSnapshot.getValue(CVModel.class).getPhoto();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void uploadCV(View view) {
        id = FirebaseAuth.getInstance().getUid();
        username = etFullName.getText().toString();
        mobile = etMobile.getText().toString();
        bd = etbd.getText().toString();
        address = etAddress.getText().toString();
        faculty = etFaculty.getText().toString();
        univers = etUnivers.getText().toString();
        exper = etExper.getText().toString();
        gradYear = etGradYear.getText().toString();
        if (male.isChecked()) {
            gend = "male";
        } else {
            gend = "female";
        }
        databaseReference.child("Employee").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                EmployeeModel employeeModel = dataSnapshot.getValue(EmployeeModel.class);

                if (ch1.isChecked()) {
                    databaseReference.child("keywords").child(ch1.getText().toString()).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(employeeModel);
                    intrestedJobs.add(ch1.getText().toString());
                }

                if (ch2.isChecked()) {
                    databaseReference.child("keywords").child(ch2.getText().toString()).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(employeeModel);
                    intrestedJobs.add(ch2.getText().toString());


                }
                if (ch3.isChecked()) {
                    databaseReference.child("keywords").child(ch3.getText().toString()).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(employeeModel);
                    intrestedJobs.add(ch3.getText().toString());

                }
                if (ch4.isChecked()) {
                    databaseReference.child("keywords").child(ch4.getText().toString()).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(employeeModel);
                    intrestedJobs.add(ch4.getText().toString());

                }
                if (ch5.isChecked()) {
                    databaseReference.child("keywords").child(ch5.getText().toString()).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(employeeModel);
                    intrestedJobs.add(ch5.getText().toString());

                }
                if (ch6.isChecked()) {
                    databaseReference.child("keywords").child(ch6.getText().toString()).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(employeeModel);
                    intrestedJobs.add(ch6.getText().toString());

                }
                if (ch7.isChecked()) {
                    databaseReference.child("keywords").child(ch7.getText().toString()).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(employeeModel);
                    intrestedJobs.add(ch7.getText().toString());

                }
                if (ch8.isChecked()) {
                    databaseReference.child("keywords").child(ch8.getText().toString()).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(employeeModel);
                    intrestedJobs.add(ch8.getText().toString());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        progressDialog = new ProgressDialog(AddCV.this);
        progressDialog.setMessage("Please Wait ...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        if (intrestedJobs.size() > 0) {
            createCV(username, mobile, gend, marital, bd, address, faculty, univers, gradYear, exper, image_url, intrestedJobs);

        } else {
            createCV(username, mobile, gend, marital, bd, address, faculty, univers, gradYear, exper, image_url);
        }
    }

    private void createCV(final String username, final String mobile, final String gend, final String marital, final String bd, final String address, final String faculty, final String univers, final String gradyear, final String exper, final String photo) {


        CVModel cvModel = new CVModel(username, mobile, gend, marital, bd, address, faculty, univers, gradYear, exper, image_url, file_url);

        databaseReference.child("CV").child(FirebaseAuth.getInstance().getUid()).setValue(cvModel);
        progressDialog.dismiss();
        Intent intent = new Intent(AddCV.this, EmployeeStartActivity.class);
        startActivity(intent);
        finish();

    }

    private void createCV(final String username, final String mobile, final String gend, final String marital, final String bd, final String address, final String faculty, final String univers, final String gradyear, final String exper, final String photo, final ArrayList<String> intrests) {


        CVModel cvModel = new CVModel(username, mobile, gend, marital, bd, address, faculty, univers, gradYear, exper, image_url, file_url, intrests);

        databaseReference.child("CV").child(FirebaseAuth.getInstance().getUid()).setValue(cvModel);
        progressDialog.dismiss();
        Intent intent = new Intent(AddCV.this, EmployeeStartActivity.class);
        startActivity(intent);
        finish();

    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            photopath = result.getUri();
            storageReference = FirebaseStorage.getInstance().getReference().child("images/" + photopath.getLastPathSegment());


            uploadTask = storageReference.putFile(photopath);
            Picasso.get()
                    .load(photopath)
                    .into(circleImageView);

            Task<Uri> task = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    return storageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {

                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri image_uri = task.getResult();
                        image_url = image_uri.toString();

                    }
                }
            });
        }
        if (requestCode == 11 && resultCode == RESULT_OK && data != null) {
            pdfUri = data.getData();
            storageReference = FirebaseStorage.getInstance().getReference().child("cvs").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(pdfUri.getLastPathSegment());


            uploadTask = storageReference.putFile(pdfUri);

            Task<Uri> task = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    return storageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {

                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri file_uri = task.getResult();
                        file_url = file_uri.toString();
//                        Toast.makeText(AddCV.this, "File Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        select.setText("File Uploaded Successfully");

                    } else {
                        Toast.makeText(AddCV.this, "No File Selected", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void selectFile(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PHONE_STATE_PERMISION);

        } else {
            selectPdf();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PHONE_STATE_PERMISION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectPdf();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();

            }
        }
    }

    private void selectPdf() {

        Intent i = new Intent();
        i.setType("application/pdf");

        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, 11);
    }


    public void checkCliced(View view) {
        switch (view.getId()){

        }
    }
}

