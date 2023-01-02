package com.example.projectinsurance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projectinsurance.adapter.UserPoliciesAdapter;
import com.example.projectinsurance.models.PackageModel;
import com.example.projectinsurance.models.UserPoliciesModel;
import com.example.projectinsurance.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class PaymentMethod extends AppCompatActivity {

    EditText dueDateReadText, insuranceDateReadText, cardNumber;
    Button btnPayNow, btnBuyNow;
    String Plane_id, Duration, policy_id;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    public static String HOLDER_ID = "";
    public static String HOLDER_NAME = "";
    public static String CATEGORY = "";
    public static String PRODUCT_NAME = "";
    UserPoliciesModel policiesModel;
    ImageView backicon2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        dueDateReadText = findViewById(R.id.dueDateReadText);
        insuranceDateReadText = findViewById(R.id.insuranceDateReadText);
        cardNumber = findViewById(R.id.cardNumber);
        btnPayNow = findViewById(R.id.btnPayNow);
        btnBuyNow = findViewById(R.id.btnBuyNow);
        backicon2=findViewById(R.id.backicon2);

        backicon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentMethod.this,PackageList.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        if (intent.getStringExtra("plane_id") != null) {
            Plane_id = (intent.getStringExtra("plane_id"));
            Duration = (intent.getStringExtra("duration"));
            policy_id = (intent.getStringExtra("policy_id"));
        }


        DatabaseReference reference = firebaseDatabase.getReference("Plans");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot != null) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        String pid = data.child("plans_id").getValue().toString();
                        Log.d("Plans Id ", pid);
                        if (pid.equals(Plane_id)) {
                            String duration = data.child("duration").getValue().toString();
                            Log.d("Duration ", duration);
                            LocalDate today = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                today = LocalDate.now();

                                Log.d("today", today.toString());
                                insuranceDateReadText.setText(today.toString());
                                String exp = "";
                                LocalDate expire = today.plusYears(Long.parseLong(duration));
                                exp = expire.toString();
                                dueDateReadText.setText(exp);
                            }
                        }
                    }

                } else {
                    Toast.makeText(PaymentMethod.this, "No Data Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cardNo = cardNumber.getText().toString();
                String updateDate = dueDateReadText.getText().toString();
                String currentDate = insuranceDateReadText.getText().toString();

//                Toast.makeText(PaymentMethod.this, ""+PackageList.PolId, Toast.LENGTH_SHORT).show();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User Policies");
                reference.addValueEventListener(new ValueEventListener() {
                    String hi;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data : snapshot.getChildren()) {
//                            model = new UserPoliciesModel(String.valueOf(data.child("policy_id").getValue()),String.valueOf(data.child("policy_no").getValue()),String.valueOf(data.child("holderID").getValue()),String.valueOf(data.child("holderName").getValue()),String.valueOf(data.child("insurance_date").getValue()),String.valueOf(data.child("due_date").getValue()),String.valueOf(data.child("category").getValue()),String.valueOf(data.child("product").getValue()));;
                            hi = String.valueOf(data.child("holderID").getValue());
                             policy_id = String.valueOf(data.child("policy_id").getValue());
                            Log.d("Holder id", hi);
                            Log.d("Policy id", policy_id);
                            Log.d("user id", user.getUid());
                            Log.d("Pol No", PackageList.PolNo);

                            if (hi.equals(user.getUid()) && policy_id.equals(data.getKey())) {
                                Toast.makeText(PaymentMethod.this, "Data Getting", Toast.LENGTH_SHORT).show();
                                HOLDER_ID = data.child("holderID").getValue().toString();
                                HOLDER_NAME = data.child("holderName").getValue().toString();
                                CATEGORY = data.child("category").getValue().toString();
                                PRODUCT_NAME = data.child("product").getValue().toString();
                                //                                Toast.makeText(PaymentMethod.this, "Searching Data hoder id "+HOLDER_ID+" holderName "+HOLDER_NAME+"category"+CATEGORY+"product"+PRODUCT_NAME, Toast.LENGTH_SHORT).show();
                            }
                        }

                        policiesModel = new UserPoliciesModel(policy_id, PackageList.PolNo, HOLDER_ID, HOLDER_NAME, currentDate, updateDate, CATEGORY, PRODUCT_NAME);

                        Log.d("My data policy ", policy_id);
                        Log.d("My data policy ",HOLDER_NAME);
                        Log.d("My data policy ", PRODUCT_NAME);

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Updated_User_Policy");
                        ref.push().setValue(policiesModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(PaymentMethod.this, "Your POLICY is Updated", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(PaymentMethod.this, MainActivity.class));
                                } else {
                                    Toast.makeText(PaymentMethod.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(PaymentMethod.this, HomeFragment.class));
                                }
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(PaymentMethod.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }
}