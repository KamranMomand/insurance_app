package com.example.projectinsurance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
    Policy_No model;
    PackageModel model1;
    //    public static String
    public static String PolNo = "";
    public static String PolType = "";
    public static String PolId = "";
    public static String HOLDER_ID = "";
    public static String HOLDER_NAME = "";
    public static String CATEGORY = "";
    public static String PRODUCT_NAME = "";

    ImageView backicon1;

    ArrayList<PackageModel> packageModelArrayList = new ArrayList<PackageModel>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_list);

        backicon1=findViewById(R.id.backicon1);
        backicon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PackageList.this,RenewActivity.class);
                startActivity(intent);
            }
        });

        recView = findViewById(R.id.packageListRecView);
        Intent intent = getIntent();
        if (intent.getStringExtra("SelectPolNo") != "") {

            PolNo = intent.getStringExtra("SelectPolNo");
//            Log.d("packagelist poll",PolNo);
        }
        if (PolNo.equals("")) {
            Toast.makeText(this, "Policy No is Null", Toast.LENGTH_SHORT).show();
        } else {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User Policies");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        if (String.valueOf(data.child("policy_no").getValue()).equals(PolNo)) {
                            PolType = String.valueOf(data.child("category").getValue());
                            PolId = String.valueOf(data.child("policy_id").getValue());
//                           // Kamran///
                            HOLDER_ID = String.valueOf(data.child("holderID").getValue());
                            HOLDER_NAME = String.valueOf(data.child("holderName").getValue());
                            CATEGORY = String.valueOf(data.child("policyType").getValue());
                            PRODUCT_NAME = String.valueOf(data.child("product").getValue());
                            if (PolType.equals("")) {
                                Toast.makeText(PackageList.this, "getPackageList polType is Null", Toast.LENGTH_SHORT).show();
                            } else {
                                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Plans");
                                reference1.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                        Log.d("loop1", "hello1");
                                        if (snapshot != null) {
                                            for (DataSnapshot data : snapshot.getChildren()) {
//                                                Log.d("loop", "hello");
                                                String pT = String.valueOf(data.child("policy_type").getValue());
//                                                Log.d("PT", pT);
//                                                Log.d("polType", PolType);
                                                if (pT.equals(PolType)) {
                                                    model1 = new PackageModel(String.valueOf(data.child("plans_id").getValue()), String.valueOf(data.child("policy_type").getValue()), String.valueOf(data.child("other_payment").getValue()), String.valueOf(data.child("monthly_amount").getValue()), String.valueOf(data.child("duration").getValue()), String.valueOf(data.child("benefits").getValue()));
                                                    packageModelArrayList.add(model1);
                                                }
                                            }
                                        }
                                        adapter = new PackageAdapter(PackageList.this, R.layout.package_list_view, packageModelArrayList);
                                        recView.setHasFixedSize(true);
                                        recView.setLayoutManager(new LinearLayoutManager(PackageList.this));
                                        recView.setAdapter(adapter);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(PackageList.this, "on cancel Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(PackageList.this, "Poll Type Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}