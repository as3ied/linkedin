package net.senior.hr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AppliedEmp extends AppCompatActivity {
    RecyclerView rec;
    EmpAdapter empAdapter;
    List<CVModel> emps;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_emp);
        rec = findViewById(R.id.emps_rec);
        emps = new ArrayList();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        databaseReference.child("Applied Emp").child(getIntent().getStringExtra("id")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CVModel cvModel = dataSnapshot.getValue(CVModel.class);
                emps.add(cvModel);

                empAdapter = new EmpAdapter(emps);
                rec.setLayoutManager(new LinearLayoutManager(AppliedEmp.this));
                rec.setAdapter(empAdapter);
                empAdapter.setOnEmpClickedListener(new EmpAdapter.OnEmpClickedListener() {
                    @Override
                    public void onEmpClicked(int pos) {
                        intent = new Intent(AppliedEmp.this, EmpDetails.class);

                        intent.putExtra("fullName", emps.get(pos).getFullName());
                        intent.putExtra("url", emps.get(pos).getFileUrl());
                        intent.putExtra("address", emps.get(pos).getAddress());
                        intent.putExtra("bd", emps.get(pos).getBd());
                        intent.putExtra("exper", emps.get(pos).getExperience());
                        intent.putExtra("faculty", emps.get(pos).getFaculty());
                        intent.putExtra("univer", emps.get(pos).getUniversity());
                        intent.putExtra("gender", emps.get(pos).getGender());
                        intent.putExtra("gradYear", emps.get(pos).getGradYear());
                        intent.putExtra("marital", emps.get(pos).getMarital());
                        intent.putExtra("mobile", emps.get(pos).getMobile());
                        intent.putExtra("photo", emps.get(pos).getPhoto());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
