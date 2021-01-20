package net.senior.hr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ViewJobEmp extends AppCompatActivity {
    TextView tvtitle, tvskils, date,tvdesc, tvvacs ,company,hr , registDate,mobile,email,address;
    ImageView logo;
    JobModel j;
    String s;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_job_emp);
        tvdesc=findViewById(R.id.tvDesc);
        registDate=findViewById(R.id.compRegistDate);
        tvskils=findViewById(R.id.tvSkills);
        tvtitle=findViewById(R.id.tvTitle);
        tvvacs=findViewById(R.id.tvVac);
        company=findViewById(R.id.companyName);
        hr=findViewById(R.id.hrName);
        date=findViewById(R.id.date);
        email=findViewById(R.id.email);
        mobile=findViewById(R.id.mobile);
        address=findViewById(R.id.address);
        logo=findViewById(R.id.logo);
firebaseDatabase=FirebaseDatabase.getInstance();
databaseReference=firebaseDatabase.getReference();
s=getIntent().getStringExtra("id");
        tvtitle.setText(getIntent().getStringExtra("title"));
        tvdesc.setText(getIntent().getStringExtra("desc"));
        tvskils.setText(getIntent().getStringExtra("skills"));
        tvvacs.setText(getIntent().getStringExtra("vacs"));
        company.setText(getIntent().getStringExtra("compName"));
        hr.setText(getIntent().getStringExtra("hrName"));
        date.setText(getIntent().getStringExtra("date"));
        registDate.setText(getIntent().getStringExtra("registDate"));
        email.setText(getIntent().getStringExtra("email"));
        mobile.setText(getIntent().getStringExtra("mobile"));
        address.setText(getIntent().getStringExtra("address"));
        Picasso.get().load(getIntent().getStringExtra("logo")).into(logo);
    }



    public void back(View view) {
        startActivity(new Intent(this,EmployeeStartActivity.class));
    }

    public void apply(View view) {
databaseReference.child("Jobs").child(s).addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
          j=dataSnapshot.getValue(JobModel.class);
        databaseReference.child("CV").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                databaseReference.child("Applied Job").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(j.getId()).setValue(j);
                Toast.makeText(ViewJobEmp.this, "Applied Successfully", Toast.LENGTH_SHORT).show();

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
        Intent intent=new Intent(ViewJobEmp.this,EmployeeStartActivity.class);
startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.favourite_menu, menu);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        databaseReference.child("Jobs").child(getIntent().getStringExtra("id")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                databaseReference.child("Favs").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(getIntent().getStringExtra("id")).setValue(dataSnapshot.getValue(JobModel.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Toast.makeText(this, "Job Added To The Favorites", Toast.LENGTH_LONG).show();

        return super.onOptionsItemSelected(item);
    }

    public void callUs(View view) {
        String num=getIntent().getStringExtra("mobile");


        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", num, null)));

    }

    public void emailUs(View view) {
        String[] TO =new String[] {getIntent().getStringExtra("email")};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.setPackage("com.google.android.gm");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Test Mail");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(emailIntent);
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }


    public void whatsappUs(View view) {
        String num=getIntent().getStringExtra("mobile");
        Intent intent = new Intent(Intent.ACTION_VIEW);
if(num.charAt(0)=='0'&&num.charAt(1)=='0')
{
   num= num.replaceFirst("0","");
   num= num.replaceFirst("0","");
    num=num.trim();

}
        if(num.startsWith("+")){
            num.replace(num.charAt(0),' ');
            num.trim();

        }
        if(num.startsWith("0")&&num.charAt(1)!='0'){
            num="2"+num;
        }
        intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+num +"&text="+""));
        startActivity(intent);
    }

    public void messageUs(View view) {
        Intent intent=new Intent(this,SendSMS.class);
        intent.putExtra("phone",getIntent().getStringExtra("mobile"));
        startActivity(intent);
    }
}

