package net.senior.hr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
FirebaseAuth auth;
    String id;
    EditText email,password;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
      auth=FirebaseAuth.getInstance();
      email=findViewById(R.id.email);
      password=findViewById(R.id.password);
      email.setText(getIntent().getStringExtra("email"));
      password.setText(getIntent().getStringExtra("password"));
firebaseDatabase=FirebaseDatabase.getInstance();
databaseReference=firebaseDatabase.getReference();
  }

    public void login(View view) {

        auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (auth.getCurrentUser().isEmailVerified()) {
                        databaseReference.child("Employer").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                                    if (dataSnapshot1.getValue().equals(auth.getCurrentUser().getUid())) {
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
                        databaseReference.child("Employee").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                                    if (dataSnapshot1.getValue().equals(auth.getCurrentUser().getUid())) {
                                        Intent intent = new Intent(getApplicationContext(), EmployeeStartActivity.class);

                                        startActivity(intent);

                                        finish();
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }


                        });
                    }else {
                        Toast.makeText(Login.this, "Please verify your email", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(Login.this, "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        });


    }
}
