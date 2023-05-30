package com.example.panemirates;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Update extends AppCompatActivity {

    String key19f19478="", oldImageURL19f19478="";
    Uri uri19f19478;

    EditText updateDesc19f19478, updateProductName, updatePrice;
    String Pname, desc, Price;
    String imageUrl19f19478;

    DatabaseReference databaseReference;
    StorageReference storageReference;
    ImageView updateImage19f19478;
    Button updateButton19f19478;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update2);

        updateButton19f19478 = findViewById(R.id.updateButton19f19478);
        updateDesc19f19478 = findViewById(R.id.updateDesc19f19478);
        updateImage19f19478 = findViewById(R.id.updateImage19f19478);
        updatePrice = findViewById(R.id.updatePrice);
        updateProductName= findViewById(R.id.updateProductName);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri19f19478 = data.getData();
                            updateImage19f19478.setImageURI(uri19f19478);
                        } else {
                            Toast.makeText(Update.this, "Need to select an image", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            Glide.with(Update.this).load(bundle.getString("Image")).into(updateImage19f19478);
            updateProductName.setText(bundle.getString("Product Name"));
            updateDesc19f19478.setText(bundle.getString("Description"));
            updatePrice.setText(bundle.getString("Price"));
            key19f19478 = bundle.getString("Key");
            oldImageURL19f19478 = bundle.getString("Image");
        }
        databaseReference = FirebaseDatabase.getInstance().getReference("Android Tutorials").child(key19f19478);

        updateImage19f19478.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
        updateButton19f19478.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                Intent intent = new Intent(Update.this, AddFurniture1.class);
                startActivity(intent);
            }
        });
    }
    public void saveData(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference().child("Android Images").child(uri19f19478.getLastPathSegment());

        AlertDialog.Builder builder = new AlertDialog.Builder(Update.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(uri19f19478).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageUrl19f19478 = urlImage.toString();
                updateData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }
    public void updateData(){
        Pname = updateProductName.getText().toString().trim();
        desc = updateDesc19f19478.getText().toString().trim();
        Price = updatePrice.getText().toString();

        dataClass dataClass = new dataClass(Pname, desc, Price, imageUrl19f19478);

        databaseReference.setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(oldImageURL19f19478);
                    reference.delete();
                    Toast.makeText(Update.this, "Updated", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Update.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}