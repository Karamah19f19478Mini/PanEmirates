package com.example.panemirates;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    Button signupsendbut19f19478, Logginn19f19478;
    EditText firstname19f19478;
    EditText lastname19f19478;
    EditText phonenumb19f19478, email19f19478, passw19f19478;
    boolean valid = true;
    FirebaseAuth fAuth19f19478;
    FirebaseFirestore fstore19f19478;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setTitle("Sign Up");

        fAuth19f19478 = FirebaseAuth.getInstance();
        fstore19f19478 = FirebaseFirestore.getInstance();
        Logginn19f19478 = findViewById(R.id.LoginAsUser19f19478);
        firstname19f19478 = findViewById(R.id.firstName19f19478);
        lastname19f19478=findViewById(R.id.secondNameText);
        phonenumb19f19478 = findViewById(R.id.phone19f19478);
        email19f19478 = findViewById(R.id.email19f19478);
        passw19f19478 = findViewById(R.id.setPass19f19478);

        signupsendbut19f19478 = findViewById(R.id.SignUpButtonSend19f19478);

        signupsendbut19f19478.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkField(firstname19f19478);
                checkField(lastname19f19478);
                checkField(phonenumb19f19478);
                checkField(email19f19478);
                checkField(passw19f19478);


                if (valid) {
                    fAuth19f19478.createUserWithEmailAndPassword(email19f19478.getText().toString(), passw19f19478.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = fAuth19f19478.getCurrentUser();
                            Toast.makeText(SignUp.this, "Account Created", Toast.LENGTH_SHORT).show();
                            DocumentReference df = fstore19f19478.collection("Users").document(user.getUid());
                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("FirstName", firstname19f19478.getText().toString());
                            userInfo.put("LastName", lastname19f19478.getText().toString());
                            userInfo.put("UserEmail", email19f19478.getText().toString());
                            userInfo.put("PhoneNumber", phonenumb19f19478.getText().toString());

                            userInfo.put("isUser", "1");
                            df.set(userInfo);
                            startActivity(new Intent(getApplicationContext(), Login.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUp.this, "Account is not Created", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });
        Logginn19f19478.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
            }

        });
        ActionBar aBar;
        aBar= getSupportActionBar();
        ColorDrawable cd = new ColorDrawable(Color.parseColor("#445280"));
        aBar.setBackgroundDrawable(cd);

    }

    public boolean checkField(EditText textField){
        if(textField.getText().toString().isEmpty()){
            textField.setError("Error");
            valid = false;
        }else {
            valid = true;
        }
        return valid;
    }
}
