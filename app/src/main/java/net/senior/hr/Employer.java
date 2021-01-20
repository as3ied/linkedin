package net.senior.hr;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Employer extends AppCompatActivity {
    CircleImageView circleImageView;
    public   String compId,companyName ,hrName ,mobile,email,address;
    EditText hrName_field, email_field,companyName_field,password_field,confirm_password_field,mobile_field,address_field,registdate;
    String password,c_password, registdat ;
    String uId;
public      Uri photopath;
    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_registeration);
        initViews();

    }

    private void initViews() {

        auth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

         user = auth.getCurrentUser();
        if (user != null ) {
            databaseReference.child("Employer").child(user.getUid()).addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
{

    if(dataSnapshot1.getValue().equals(user.getUid())){
        Intent intent = new Intent(getApplicationContext(), EmployerStartActivity.class);

        startActivity(intent);

        finish();
    }

}
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }



            });

        }

        circleImageView = findViewById(R.id.profile_picture);
        email_field = findViewById(R.id.email);
        companyName_field = findViewById(R.id.companyName);
        hrName_field = findViewById(R.id.hrName);
        password_field = findViewById(R.id.password);
        confirm_password_field = findViewById(R.id.confirm_password);
        mobile_field = findViewById(R.id.editTextPhone);
        address_field = findViewById(R.id.editTextaddr);
        registdate = findViewById(R.id.registdat);
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                updateLabel();
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                registdate.setText(sdf.format(myCalendar.getTime()));
            }

        };

        registdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(Employer.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });




        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON_TOUCH)
                        .setAspectRatio(1, 1)
                        .start(Employer.this);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            photopath = result.getUri();

            Picasso.get()
                    .load(photopath)
                    .into(circleImageView);
        }
    }

    public void already(View view)
    {

        startActivity(new Intent(this, Login.class));
    }


    public void register(View view)
    {
        email = email_field.getText().toString();
        companyName = companyName_field.getText().toString();
        hrName = hrName_field.getText().toString();
        password = password_field.getText().toString();
        c_password = confirm_password_field.getText().toString();
        mobile = mobile_field.getText().toString();
        address = address_field.getText().toString();
        registdat = registdate.getText().toString();


        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(getApplicationContext(), "enter email", Toast.LENGTH_SHORT).show();
            email_field.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(companyName))
        {
            Toast.makeText(getApplicationContext(), "enter companyName", Toast.LENGTH_SHORT).show();
            companyName_field.requestFocus();
            return;
        }
  if (TextUtils.isEmpty(hrName))
        {
            Toast.makeText(getApplicationContext(), "enter HR Name", Toast.LENGTH_SHORT).show();
            hrName_field.requestFocus();
            return;
        }

        if (password.length() < 6)
        {
            Toast.makeText(getApplicationContext(), "password is too short", Toast.LENGTH_SHORT).show();
            password_field.requestFocus();
            return;
        }

        if (!c_password.equals(password))
        {
            Toast.makeText(getApplicationContext(), "password is not matching", Toast.LENGTH_SHORT).show();
            confirm_password_field.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(mobile))
        {
            Toast.makeText(getApplicationContext(), "enter mobile number", Toast.LENGTH_SHORT).show();
            mobile_field.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(address))
        {
            Toast.makeText(getApplicationContext(), "enter your address", Toast.LENGTH_SHORT).show();
            address_field.requestFocus();
            return;
        }

        if (photopath == null)
        {
            Toast.makeText(getApplicationContext(), "select photo", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog = new ProgressDialog(Employer.this);
        progressDialog.setMessage("Please Wait ...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

        createUser(email,password,companyName,mobile,address,hrName,registdat);

    }



    private void createUser(final String email, String password, final String companyName, final String mobile, final String address,final String hrName ,final  String bd )
    {

        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            uId = task.getResult().getUser().getUid();
compId=uId;
                            auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    uploadImage(email, companyName, mobile, address, uId, hrName, bd);

                                }
                            });
                        } else {

                            Toast.makeText(Employer.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
progressDialog.dismiss();
                        }
                    }

                            });
                        }


    private void uploadImage(final String email, final String companyName, final String mobile, final String address, final String uId,final String hrName ,final String bd )
    {
        UploadTask uploadTask;
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + photopath.getLastPathSegment());
        uploadTask = storageReference.putFile(photopath);

        Task<Uri> task = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>()
        {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
            {
                return storageReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>()
        {
            @Override
            public void onComplete(@NonNull Task<Uri> task)
            {
                if (task.isSuccessful())
                {
                    Uri image_uri = task.getResult();
                    String image_url = image_uri.toString();

                    addUser(email,companyName,mobile,address,uId,image_url,hrName,bd);
                }
            }
        });
    }


    private void addUser(String email, String companyName, String mobile, String address, String id,String photo,String hrName,String bd)
    {
        EmployerModel userModel = new EmployerModel(email,companyName,mobile,address,photo,id,hrName,bd);

        databaseReference.child("Employer").child(id).setValue(userModel);

        progressDialog.dismiss();
        Intent intent = new Intent(this, Login.class);
       intent.putExtra("id",compId);
       intent.putExtra("email",email);
       intent.putExtra("password",password);
        startActivity(intent);

        finish();
    }


}
