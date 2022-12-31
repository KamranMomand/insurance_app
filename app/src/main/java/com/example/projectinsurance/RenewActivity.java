package com.example.projectinsurance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projectinsurance.ui.home.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RenewActivity extends AppCompatActivity {

    ImageView backicon;
    ArrayAdapter adapter;
    Button btnPolicyNumber;
    Spinner spinner;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ArrayList<String> policy_noArrayList;
    Policy_No policy_noModel;
    private DatabaseReference reference;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    String policynumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renew);
        backicon=findViewById(R.id.backicon);
        btnPolicyNumber=findViewById(R.id.btnPolicyNumber);
        spinner=findViewById(R.id.spinnerPolicy);
        policy_noArrayList=new ArrayList<String>();
        database = FirebaseDatabase.getInstance();
//        getUserProfile(user.getUid());


        getPolicy();

        backicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace()
                Fragment fragment = new HomeFragment();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.renew,fragment).commit();

            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                policynumber  = spinner.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnPolicyNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PackageList.class);
//                Log.d("Renew  poll",policynumber);
                intent.putExtra("SelectPolNo",policynumber);
                startActivity(intent);
            }
        });
    }

    private void getPolicy() {
        reference = database.getReference("User Policies");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot != null){
                    for(DataSnapshot data: snapshot.getChildren()){
                        if(data.child("holderID").getValue().toString().equals(user.getUid())){
                            policy_noArrayList.add(String.valueOf(data.child("policy_no").getValue()));
                        }

                    }
                }else {
                    Toast.makeText(RenewActivity.this, "Data Not Founded", Toast.LENGTH_SHORT).show();
                }
                adapter = new ArrayAdapter(RenewActivity.this, android.R.layout.simple_spinner_item, policy_noArrayList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
//
//    private void getUserProfile(String uid) {
//    }
}