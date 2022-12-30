package com.example.projectinsurance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import com.example.projectinsurance.adapter.PackageAdapter;
import com.example.projectinsurance.models.PackageModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PackageList extends AppCompatActivity {
    private RecyclerView recView;
    PackageAdapter adapter;
    ArrayList<PackageModel> packageModelArrayList = new ArrayList<>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_list);
        recView = findViewById(R.id.packageListRecView);
        adapter = new PackageAdapter(this,R.layout.package_list_view,packageModelArrayList);
//        recView.setAdapter(adapter);
        recView.setHasFixedSize(true);
        recView.setLayoutManager(new LinearLayoutManager(this));
        recView.setAdapter(adapter);
        getPackageList();
    }

    private void getPackageList() {
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("Plans");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot != null){
                    for (DataSnapshot data:snapshot.getChildren()){
                        PackageModel model=new PackageModel(String.valueOf(data.child("plans_id").getValue()),String.valueOf(data.child("policy_type").getValue()),String.valueOf(data.child("other_payment").getValue()),String.valueOf(data.child("monthly_amount").getValue()),String.valueOf(data.child("duration").getValue()),String.valueOf(data.child("benefits").getValue()));
                        packageModelArrayList.add(model);
                    }
//                    if(model != null){
//                    }else {
//                        Toast.makeText(PackageList.this, "No Data Founded", Toast.LENGTH_SHORT).show();
//                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PackageList.this, "on cancel Error"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}