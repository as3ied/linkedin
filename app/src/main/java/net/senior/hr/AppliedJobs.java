package net.senior.hr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AppliedJobs extends AppCompatActivity {

    RecyclerView rec;
    List<JobModel> jobs;
    JobsAdapter jobsAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_jobs);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        rec=findViewById(R.id.applied_rec);
        jobs=new ArrayList();
        databaseReference.child("Applied Job").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(!jobs.isEmpty())
                {
                    jobs.clear();
                }

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    JobModel data =dataSnapshot1.getValue(JobModel.class);
                    jobs.add(data);

                }
                jobsAdapter = new JobsAdapter(jobs);
                jobsAdapter.setOnViewListier(new JobsAdapter.OnViewListiner() {
                    @Override
                    public void onViewClicked(int position) {

                        databaseReference.child("Employer").child(jobs.get(position).getCompId()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                intent.putExtra("registDate",dataSnapshot.getValue(EmployerModel.class).getRegisdat());
                                intent.putExtra("email",dataSnapshot.getValue(EmployerModel.class).getEmail());
                                intent.putExtra("mobile",dataSnapshot.getValue(EmployerModel.class).getMobile());
                                intent.putExtra("address",dataSnapshot.getValue(EmployerModel.class).getAddress());

                                intent.putExtra("compName",dataSnapshot.getValue(EmployerModel.class).getCompanyName());
                                intent.putExtra("hrName",dataSnapshot.getValue(EmployerModel.class).getHrName());
                                intent.putExtra("logo",dataSnapshot.getValue(EmployerModel.class).getPhoto());
                                startActivity(intent);                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        intent=new Intent(AppliedJobs.this,ViewJobEmp.class);

                        intent.putExtra("title",jobs.get(position).getTitle());
                        intent.putExtra("id",jobs.get(position).getId());
                        intent.putExtra("desc",jobs.get(position).getDesc());
                        intent.putExtra("skills",jobs.get(position).getSkils());
                        intent.putExtra("vacs",jobs.get(position).getVacs()+"");
                        intent.putExtra("date",jobs.get(position).getJobDate());



                    }
                });
                rec.setLayoutManager(new LinearLayoutManager(AppliedJobs.this));
                DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(AppliedJobs.this,  DividerItemDecoration.VERTICAL);
                rec.addItemDecoration(dividerItemDecoration);
                rec.setAdapter(jobsAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });


    }

}

