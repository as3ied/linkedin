package net.senior.hr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EmployeeStartActivity extends AppCompatActivity {
    RecyclerView rec;
    List<JobModel> jobs;
    List<String> matcJobs;
    List<String> jobsid;
    JobModel j;
    EmployerModel company;
    JobsAdapter jobsAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    int i;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_employer_start);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        rec = findViewById(R.id.job_rec);
        jobs = new ArrayList();
        jobsid = new ArrayList();
        matcJobs = new ArrayList();
databaseReference.child("CV").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        matcJobs=dataSnapshot.getValue(CVModel.class).getIntrestedJobs();
        for(String s:matcJobs){

            databaseReference.child("Added Jobs").child(s).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        jobsid.add(dataSnapshot1.getValue(JobModel.class).getId());

                    }

                    for (String id : jobsid) {
                        databaseReference.child("Jobs").child(id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                j = dataSnapshot.getValue(JobModel.class);
                                jobs.add(j);

                                jobsAdapter = new JobsAdapter(jobs);
                                jobsAdapter.setOnViewListier(new JobsAdapter.OnViewListiner() {
                                    @Override
                                    public void onViewClicked(int position) {

                                        databaseReference.child("Employer").child(jobs.get(position).getCompId()).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                intent.putExtra("email", dataSnapshot.getValue(EmployerModel.class).getEmail());
                                                intent.putExtra("mobile", dataSnapshot.getValue(EmployerModel.class).getMobile());
                                                intent.putExtra("address", dataSnapshot.getValue(EmployerModel.class).getAddress());
                                                intent.putExtra("registDate", dataSnapshot.getValue(EmployerModel.class).getRegisdat());
//                                                    intent.putExtra("id", j.getId());

                                                intent.putExtra("compName", dataSnapshot.getValue(EmployerModel.class).getCompanyName());
                                                intent.putExtra("hrName", dataSnapshot.getValue(EmployerModel.class).getHrName());
                                                intent.putExtra("logo", dataSnapshot.getValue(EmployerModel.class).getPhoto());
                                                startActivity(intent);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                        intent = new Intent(EmployeeStartActivity.this, ViewJobEmp.class);

                                        intent.putExtra("title", jobs.get(position).getTitle());
                                        intent.putExtra("id", jobs.get(position).getId());
                                        intent.putExtra("desc", jobs.get(position).getDesc());
                                        intent.putExtra("skills", jobs.get(position).getSkils());
                                        intent.putExtra("vacs", jobs.get(position).getVacs() + "");

                                        intent.putExtra("date", jobs.get(position).getJobDate());


                                    }
                                });
                                rec.setLayoutManager(new LinearLayoutManager(EmployeeStartActivity.this));
                                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(EmployeeStartActivity.this, DividerItemDecoration.VERTICAL);
                                rec.addItemDecoration(dividerItemDecoration);
                                rec.setAdapter(jobsAdapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});








    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.employee_menu, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.userCv:
                Intent i = new Intent(this, AddCV.class);
                startActivity(i);
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                i = new Intent(this, MainActivity.class);
                startActivity(i);
                return true;
            case R.id.allJobs:
                i = new Intent(this, AllJobsEmp.class);
                startActivity(i);
                return true;
            case R.id.favJobs:
                i = new Intent(this, FavJobs.class);
                startActivity(i);
                return true;case R.id.AppliedJobs:
                i = new Intent(this, AppliedJobs.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}



