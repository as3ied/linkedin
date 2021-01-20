package net.senior.hr;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class AddJob extends AppCompatActivity {
    CheckBox ch1, ch2, ch3, ch4, ch5, ch6, ch7, ch8;

    EditText tvtitle, tvskils, tvdesc, tvvacs;
    String title, skills, desc, vacs,id ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
     JobModel jobModel;
     String date ,companyName,hrName,photo ,registDate,mobile,email,address;
public  Date jobDate;
    private ArrayList<String> keywords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference();
        ch1 = findViewById(R.id.chk1);
        ch2 = findViewById(R.id.chk2);
        ch3 = findViewById(R.id.chk3);
        ch4 = findViewById(R.id.chk4);
        ch5 = findViewById(R.id.chk5);
        ch6 = findViewById(R.id.chk6);
        ch7 = findViewById(R.id.chk7);
        ch8 = findViewById(R.id.chk8);
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


                jobDate = new Date();


                SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
                date = sf.format(jobDate);
                tvtitle = findViewById(R.id.title);
                tvdesc = findViewById(R.id.desc);
                tvvacs = findViewById(R.id.vacancies);
                tvskils = findViewById(R.id.skills);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

            public void register(View view) {
        title = tvtitle.getText().toString();
        desc = tvdesc.getText().toString();
        vacs = tvvacs.getText().toString();
        skills = tvskils.getText().toString();
        progressDialog = new ProgressDialog(AddJob.this);
        progressDialog.setMessage("Please Wait ...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
            id=UUID.randomUUID()+"";
             jobModel = new JobModel(title, desc, skills,id, Integer.valueOf(vacs),date,FirebaseAuth.getInstance().getCurrentUser().getUid());

            databaseReference.child("Jobs").child(id).setValue(jobModel);
            if(ch1.isChecked()){
                databaseReference.child("Added Jobs").child(ch1.getText().toString()).child(id).setValue(jobModel);


            } if(ch2.isChecked()){
                databaseReference.child("Added Jobs").child(ch2.getText().toString()).child(id).setValue(jobModel);


            } if(ch3.isChecked()){
                databaseReference.child("Added Jobs").child(ch3.getText().toString()).child(id).setValue(jobModel);

            } if(ch4.isChecked()){
                databaseReference.child("Added Jobs").child(ch4.getText().toString()).child(id).setValue(jobModel);


            } if(ch5.isChecked()){
                databaseReference.child("Added Jobs").child(ch5.getText().toString()).child(id).setValue(jobModel);
            } if(ch6.isChecked()){
                databaseReference.child("Added Jobs").child(ch6.getText().toString()).child(id).setValue(jobModel);

            } if(ch7.isChecked()){
                databaseReference.child("Added Jobs").child(ch7.getText().toString()).child(id).setValue(jobModel);


            } if(ch8.isChecked()) {
                databaseReference.child("Added Jobs").child(ch8.getText().toString()).child(id).setValue(jobModel);
            }

 Intent intent = new Intent(getApplicationContext(), EmployerStartActivity.class);
            startActivity(intent);
            finish();
        }
    }
