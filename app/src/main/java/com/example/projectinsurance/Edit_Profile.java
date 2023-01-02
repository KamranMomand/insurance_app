package com.example.projectinsurance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.projectinsurance.databinding.ActivityEditProfileBinding;

public class Edit_Profile extends AppCompatActivity {

    ImageView backicon3;

    ActivityEditProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

                binding= ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        backicon3=findViewById(R.id.backicon3);

        backicon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace()
                Intent intent = new Intent(Edit_Profile.this,MainActivity.class);
                intent.putExtra("fragment","profile");
                startActivity(intent);
//                Fragment fragment = new ProfileFragment();
//                FragmentManager manager = getSupportFragmentManager();
//                manager.beginTransaction().replace(R.id.editProfile,fragment).commit();

            }
        });

    }
}