package com.example.panemirates;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ActivityDetail extends AppCompatActivity {

    TextView detailDesc19f19478, detailProductName, detailPrice;
    ImageView detailImage19f19478;
    FloatingActionButton deleteButton19f19478, editButton19f19478;
    String key19f19478 = "";
    String imageUrl19f19478 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailDesc19f19478 = findViewById(R.id.detailDesc19f19478);
        detailImage19f19478 = findViewById(R.id.detailImage);
        detailProductName = findViewById(R.id.detailProductName);
        deleteButton19f19478 = findViewById(R.id.deleteButton);
        editButton19f19478 = findViewById(R.id.editButton);
        detailPrice = findViewById(R.id.detailPrice);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailDesc19f19478.setText(bundle.getString("Description"));
            detailProductName.setText(bundle.getString("Product Name"));
            detailPrice.setText(bundle.getString("Price"));
            key19f19478 = bundle.getString("Key");
            imageUrl19f19478 = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(detailImage19f19478);
        }
        deleteButton19f19478.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Pan Emirates");
                FirebaseStorage storage = FirebaseStorage.getInstance();

                StorageReference storageReference = storage.getReferenceFromUrl(imageUrl19f19478);
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        reference.child(key19f19478).removeValue();
                        Toast.makeText(ActivityDetail.this, "Deleted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), AddFurniture1.class));
                        finish();
                    }
                });
            }
        });
        editButton19f19478.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent19f19478 = new Intent(ActivityDetail.this, Update.class)
                        .putExtra("Prodcut Name", detailProductName.getText().toString())
                        .putExtra("Description", detailDesc19f19478.getText().toString())
                        .putExtra("Price", detailPrice.getText().toString())
                        .putExtra("Image", imageUrl19f19478)
                        .putExtra("Key", key19f19478);
                startActivity(intent19f19478);
            }
        });
    }
}