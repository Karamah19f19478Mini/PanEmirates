package com.example.panemirates;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText useremail19f19478,userpassword19f19478;
    Button loginBtn19f19478;
    //Button gotoRegister;
    boolean valid = true;
    FirebaseAuth fAuth219f19478;
    FirebaseFirestore fStore219f19478;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        getSupportActionBar().setTitle("Log In");

        fAuth219f19478 =FirebaseAuth.getInstance();
        fStore219f19478=FirebaseFirestore.getInstance();


        useremail19f19478 = findViewById(R.id.email19f19478);
        userpassword19f19478 = findViewById(R.id.password19f19478);
        loginBtn19f19478 = findViewById(R.id.Login19f19478);
        //gotoRegister = findViewById(R.id.gotoRegister);

        loginBtn19f19478.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkField(useremail19f19478);
                checkField(userpassword19f19478);
                Log.d("TAG","OnClick: "+ useremail19f19478.getText().toString());

                if(valid){
                    fAuth219f19478.signInWithEmailAndPassword(useremail19f19478.getText().toString(),userpassword19f19478.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(Login.this, "Successful Login",Toast.LENGTH_SHORT).show();checkUserAccessLevel(authResult.getUser().getUid());

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Login.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    });

                }

            }
        });
        ActionBar aBar;
        aBar= getSupportActionBar();
        ColorDrawable cd = new ColorDrawable(Color.parseColor("#445280"));
        aBar.setBackgroundDrawable(cd);


    }

    private void checkUserAccessLevel(String uid) {
        DocumentReference df= fStore219f19478.collection("Users").document(uid);
        //get the data from the doc
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","onSuccess: "+ documentSnapshot.getData());
                //define the user access method
                if(documentSnapshot.getString("isAdmin") != null) {
                    //in case it is admin
                    startActivity(new Intent(getApplicationContext(), AddFurniture1.class));
                    finish();
                }
                if(documentSnapshot.getString("isUser") != null){

                    startActivity(new Intent(getApplicationContext(), HomePage.class));
                    finish();
                }
            }
        });
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

