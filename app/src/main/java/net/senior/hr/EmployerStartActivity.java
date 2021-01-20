package net.senior.hr;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EmployerStartActivity extends AppCompatActivity {
RecyclerView rec;
List<JobModel> jobs;
String compName,hrName,logo;
JobsAdapter jobsAdapter;
FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;
    private EmployerModel company;
    Intent intent;
Button applied;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_start);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        rec=findViewById(R.id.job_rec);



        jobs=new ArrayList();
        databaseReference.child("Jobs").addValueEventListener(new ValueEventListener()
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
                 intent=new Intent(EmployerStartActivity.this,ViewJob.class);

                jobsAdapter.setOnViewListier(new JobsAdapter.OnViewListiner() {
                    @Override
                    public void onViewClicked(int position) {
                        databaseReference.child("Employer").child(jobs.get(position).getCompId()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                intent.putExtra("compName",dataSnapshot.getValue(EmployerModel.class).getCompanyName());
                                intent.putExtra("hrName",dataSnapshot.getValue(EmployerModel.class).getHrName());
                                intent.putExtra("logo",dataSnapshot.getValue(EmployerModel.class).getPhoto());
                                startActivity(intent);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }

                        });



                        intent.putExtra("title",jobs.get(position).getTitle());
                        intent.putExtra("id",jobs.get(position).getId());
                        intent.putExtra("compId",jobs.get(position).getCompId());
                        intent.putExtra("desc",jobs.get(position).getDesc());
                        intent.putExtra("skills",jobs.get(position).getSkils());
                        intent.putExtra("vacs",jobs.get(position).getVacs()+"");

                        intent.putExtra("date",jobs.get(position).getJobDate());



                       }
                });
                rec.setLayoutManager(new LinearLayoutManager(EmployerStartActivity.this));
                DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(EmployerStartActivity.this,  DividerItemDecoration.VERTICAL);
                rec.addItemDecoration(dividerItemDecoration);
                rec.setAdapter(jobsAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.addjob:
                Intent i = new Intent(this, AddJob.class);
                startActivity(i);
                return true;
                case R.id.myjob:
                 i = new Intent(this, MyJobs.class);
                startActivity(i);
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                i = new Intent(this, MainActivity.class);
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
   }



}
