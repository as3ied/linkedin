package net.senior.hr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EditJob extends AppCompatActivity {
    CheckBox ch1, ch2, ch3, ch4, ch5, ch6, ch7, ch8;
Button applied;
    EditText tvtitle, tvskils, tvdesc, tvvacs;
    String title, skills, desc, vacs,id;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
     ArrayList<String> keywords;
     String date;
     JobModel jobModel;
    private Date jobDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_job);

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
                databaseReference.child("Jobs").child(getIntent().getStringExtra("id")).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        jobModel=dataSnapshot.getValue(JobModel.class);
                                   }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });


                SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
                date = sf.format(jobDate);
                tvtitle = findViewById(R.id.title);
                tvdesc = findViewById(R.id.desc);
                tvvacs = findViewById(R.id.vacancies);
                tvskils = findViewById(R.id.skills);
                    tvtitle.setText(getIntent().getStringExtra("title"));
                    tvdesc.setText(getIntent().getStringExtra("desc"));
                    tvskils.setText(getIntent().getStringExtra("skills"));
                    tvvacs.setText(getIntent().getStringExtra("vacs"));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void register(View view) {

        databaseReference.child("Jobs").child(getIntent().getStringExtra("id")).child("desc").setValue(tvdesc.getText().toString());
        databaseReference.child("Jobs").child(getIntent().getStringExtra("id")).child("skils").setValue(tvskils.getText().toString());
        databaseReference.child("Jobs").child(getIntent().getStringExtra("id")).child("title").setValue(tvtitle.getText().toString());
        databaseReference.child("Jobs").child(getIntent().getStringExtra("id")).child("vacs").setValue(Integer.valueOf(tvvacs.getText().toString()));
startActivity(new Intent(this,MyJobs.class));
    }

    public void appliedEmp(View view) {
        Intent intent=new Intent(EditJob.this,AppliedEmp.class);
        intent.putExtra("id",getIntent().getStringExtra("id"));
        startActivity(intent);

    }
}
