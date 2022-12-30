package com.example.projectinsurance.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectinsurance.R;
import com.example.projectinsurance.User;
import com.example.projectinsurance.adapter.UserPoliciesAdapter;
import com.example.projectinsurance.databinding.FragmentGalleryBinding;
import com.example.projectinsurance.models.UserPoliciesModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {
    ArrayList<UserPoliciesModel> userPoliciesModelArrayList;
    UserPoliciesAdapter adapter;
    private FragmentGalleryBinding binding;
    UserPoliciesModel  model;
//    RecyclerView recView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel = new ViewModelProvider(this).get(GalleryViewModel.class);
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//        recView = getView().findViewById(R.id.userPoliciesRecView);
        userPoliciesModelArrayList = new ArrayList<>();
        adapter = new UserPoliciesAdapter(getContext(), R.layout.user_policies_list_view,userPoliciesModelArrayList);
        binding.userPoliciesRecView.setHasFixedSize(true);
        binding.userPoliciesRecView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.userPoliciesRecView.setAdapter(adapter);
        getUserPolicies();
        return root;
    }

    private void getUserPolicies() {
        FirebaseAuth auth =FirebaseAuth.getInstance();
        FirebaseUser user =auth.getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User Policies");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()){
//                    model = data.getValue(UserPoliciesModel.class);
                    model = new UserPoliciesModel(String.valueOf(data.child("policy_id").getValue()),String.valueOf(data.child("policy_no").getValue()),String.valueOf(data.child("holderID").getValue()),String.valueOf(data.child("holderName").getValue()),String.valueOf(data.child("insurance_date").getValue()),String.valueOf(data.child("due_date").getValue()),String.valueOf(data.child("category").getValue()),String.valueOf(data.child("product").getValue()));;
                    if(model.getHolderID().equals(user.getUid())){
                        userPoliciesModelArrayList.add(model);
//                        Toast.makeText(getContext(), "tjyfjfyj", Toast.LENGTH_SHORT).show();
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}