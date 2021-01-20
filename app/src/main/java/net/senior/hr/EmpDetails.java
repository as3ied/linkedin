package net.senior.hr;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class EmpDetails extends AppCompatActivity {
    TextView fullName, address, mobile, gender, marital, bd, university, faculty, experience, gradYear;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_details);

        fullName = findViewById(R.id.fullName);
        address = findViewById(R.id.address);
        mobile = findViewById(R.id.mobile);
        gender = findViewById(R.id.gender);
        marital = findViewById(R.id.maritalStat);
        bd = findViewById(R.id.bd);
        faculty = findViewById(R.id.faculty);
        experience = findViewById(R.id.experince);
        gradYear = findViewById(R.id.gradYear);
        university = findViewById(R.id.university);
        img = findViewById(R.id.profile_picture);

        fullName.setText(getIntent().getStringExtra("fullName"));
        address.setText(getIntent().getStringExtra("address"));
        bd.setText(getIntent().getStringExtra("bd"));
        gradYear.setText(getIntent().getStringExtra("gradYear"));
        experience.setText(getIntent().getStringExtra("exper"));
        mobile.setText(getIntent().getStringExtra("mobile"));
        gender.setText(getIntent().getStringExtra("gender"));
        marital.setText(getIntent().getStringExtra("marital"));
        faculty.setText(getIntent().getStringExtra("faculty"));
        university.setText(getIntent().getStringExtra("univer"));
        Picasso.get().load(getIntent().getStringExtra("photo")).into(img);

    }

    public void downloadCV(View view) {


//            String fileName=getIntent().getStringExtra("url");
//            DownloadManager.Request request = new DownloadManager.Request(uri);
//            request.setTitle(fileName);
//            request.setNotificationVisibility(1);
//            request.allowScanningByMediaScanner();
//            request.setMimeType("application/pdf");

        Intent i=new Intent();
        i.setType("application/pdf");
        i.setData(Uri.parse(getIntent().getStringExtra("url")));
        startActivity(i);
    }
}
