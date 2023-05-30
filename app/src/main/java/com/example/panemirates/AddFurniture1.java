package com.example.panemirates;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;


public class AddFurniture1 extends AppCompatActivity {
    FloatingActionButton fab19f19478;
    DatabaseReference databaseReference19f19478;
    ValueEventListener eventListener19f19478;
    RecyclerView recyclerView19f19478;
    List<dataClass> dataList19f19478;
    Adapter adapter19f19478;
    // SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_furniture1);
        recyclerView19f19478 = findViewById(R.id.recyclerView);
        fab19f19478 = findViewById(R.id.fabkaramah);
        // searchView = findViewById(R.id.search);
        //   searchView.clearFocus();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(AddFurniture1.this, 1);
        recyclerView19f19478.setLayoutManager(gridLayoutManager);
        AlertDialog.Builder builder = new AlertDialog.Builder(AddFurniture1.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress);
        AlertDialog dialog = builder.create();
        dialog.show();
        dataList19f19478 = new ArrayList<>();
        adapter19f19478 = new Adapter(AddFurniture1.this, dataList19f19478);
        recyclerView19f19478.setAdapter(adapter19f19478);
        databaseReference19f19478 = FirebaseDatabase.getInstance().getReference("Pan Emirates");
        dialog.show();
        eventListener19f19478 = databaseReference19f19478.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList19f19478.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    dataClass dataClass = itemSnapshot.getValue(dataClass.class);
                    dataClass.setKey19f19478(itemSnapshot.getKey());
                    dataList19f19478.add(dataClass);
                }
                adapter19f19478.notifyDataSetChanged();
                dialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });
        fab19f19478.setOnClickListener(view -> {
            Intent intent = new Intent(AddFurniture1.this, Adfurniture2.class);
            startActivity(intent);
        });
    }
}
