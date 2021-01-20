package net.senior.hr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ViewJob extends AppCompatActivity {
    TextView tvtitle, tvskils, date,tvdesc, tvvacs ,company,hr , registDate,mobile,email,address;
    ImageView logo;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_job);
        tvdesc=findViewById(R.id.tvDesc);
        tvskils=findViewById(R.id.tvSkills);
        tvtitle=findViewById(R.id.tvTitle);
        tvvacs=findViewById(R.id.tvVac);
        company=findViewById(R.id.companyName);
        hr=findViewById(R.id.hrName);
        date=findViewById(R.id.date);

        logo=findViewById(R.id.logo);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();

        tvtitle.setText(getIntent().getStringExtra("title"));
        tvdesc.setText(getIntent().getStringExtra("desc"));
        tvskils.setText(getIntent().getStringExtra("skills"));
        tvvacs.setText(getIntent().getStringExtra("vacs"));
        company.setText(getIntent().getStringExtra("compName"));
        hr.setText(getIntent().getStringExtra("hrName"));
        date.setText(getIntent().getStringExtra("date"));
             Picasso.get().load(getIntent().getStringExtra("logo")).into(logo);
    }
    public void back(View view) {
        startActivity(new Intent(this,EmployerStartActivity.class));
    }


    public void appliedEmp(View view) {
        Intent i=new Intent(this,AppliedEmp.class);
        i.putExtra("jobId",getIntent().getStringExtra("id"));
    }
}
