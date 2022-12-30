package com.example.projectinsurance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectinsurance.R;
import com.example.projectinsurance.models.UserPoliciesModel;

import java.util.ArrayList;

public class UserPoliciesAdapter extends RecyclerView.Adapter<UserPoliciesAdapter.UserPoliciesViewHolder> {
    Context context;
    int resource;
    ArrayList<UserPoliciesModel> userPoliciesModelArrayList = new ArrayList<>();

    public UserPoliciesAdapter(Context context, int resource, ArrayList<UserPoliciesModel> userPoliciesModelArrayList) {
        this.context = context;
        this.resource = resource;
        this.userPoliciesModelArrayList = userPoliciesModelArrayList;
    }

    @NonNull
    @Override
    public UserPoliciesAdapter.UserPoliciesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView=inflater.inflate(resource,parent,false);
        UserPoliciesAdapter.UserPoliciesViewHolder holder=new UserPoliciesAdapter.UserPoliciesViewHolder(rowView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserPoliciesAdapter.UserPoliciesViewHolder holder, int position) {
        holder.holderID.setText(userPoliciesModelArrayList.get(position).getHolderID());
        holder.holderName.setText(String.format("HOLDER NAME : %s",userPoliciesModelArrayList.get(position).getHolderName()));
        holder.policyNumberView.setText(String.format("POLICY NO : %s",userPoliciesModelArrayList.get(position).getPolicyNumber()));
        holder.categoryView.setText(String.format("POLICY TYPE : %s",userPoliciesModelArrayList.get(position).getPolicyType()));
        holder.insuranceDateView.setText(String.format("INSURANCE DATE : %s",userPoliciesModelArrayList.get(position).getInsuranceDate()));
        holder.dueDateView.setText(String.format("DUE DATE : %s",userPoliciesModelArrayList.get(position).getDueDate()));
        holder.productView.setText(String.format("COMPANY NAME : %s",userPoliciesModelArrayList.get(position).getProductName()));
    }

    @Override
    public int getItemCount() {
        return userPoliciesModelArrayList.size();
    }

    public class UserPoliciesViewHolder extends RecyclerView.ViewHolder {
        TextView holderID,holderName,policyNumberView,categoryView,insuranceDateView,dueDateView,productView;
        public UserPoliciesViewHolder(@NonNull View itemView) {
            super(itemView);
            holderID=itemView.findViewById(R.id.holderID);
            holderName=itemView.findViewById(R.id.holderNameView);
            policyNumberView=itemView.findViewById(R.id.policyNumberView);
            categoryView=itemView.findViewById(R.id.categoryView);
            insuranceDateView=itemView.findViewById(R.id.insuranceDateView);
            dueDateView=itemView.findViewById(R.id.dueDateView);
            productView=itemView.findViewById(R.id.productView);
        }
    }
}
