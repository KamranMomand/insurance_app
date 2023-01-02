package com.example.projectinsurance.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectinsurance.PackageList;
import com.example.projectinsurance.PaymentMethod;
import com.example.projectinsurance.R;
import com.example.projectinsurance.models.PackageModel;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.PackageViewHolder> {
    private Context context;
    private int resource;
    private ArrayList<PackageModel> packageModelArrayList;

    public PackageAdapter(Context context, int resource, ArrayList<PackageModel> packageModelArrayList) {
        this.context = context;
        this.resource = resource;
        this.packageModelArrayList = packageModelArrayList;
    }

    @NonNull
    @Override
    public PackageAdapter.PackageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView=inflater.inflate(resource,parent,false);
        PackageViewHolder holder=new PackageViewHolder(rowView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PackageAdapter.PackageViewHolder holder, int position) {
        String planeId = String.valueOf(packageModelArrayList.get(position).getPlans_id());
        String duration= String .valueOf(packageModelArrayList.get(position).getDuration());
        holder.planeId.setText(String.format("id %s",planeId));
        holder.healthInsurance.setText(String.format("POLICY TYPE : %s",packageModelArrayList.get(position).getPolicy_type()));
        holder.duration.setText(String.format("DURATION : %s",duration));
        holder.monthlyAmount.setText(String.format("MONTHLY AMOUNT : %s",packageModelArrayList.get(position).getMonthly_amount()));
        holder.otherAmount.setText(String.format("OTHER AMOUNT : %s",packageModelArrayList.get(position).getOther_payment()));
        holder.benifits.setText(String.format("BENEFITS : %s:",packageModelArrayList.get(position).getBenefits()));

        holder.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PaymentMethod.class);
                intent.putExtra("plane_id",planeId);
                intent.putExtra("duration",duration);
                intent.putExtra("policyno",PackageList.PolNo);
                intent.putExtra("policyid",PackageList.PolId);
                intent.putExtra("holderID",PackageList.HOLDER_ID);
                intent.putExtra("holderName",PackageList.HOLDER_NAME);
                intent.putExtra("category",PackageList.CATEGORY);
                intent.putExtra("product",PackageList.PRODUCT_NAME);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return packageModelArrayList.size();
    }

    public class PackageViewHolder extends RecyclerView.ViewHolder {
        TextView planeId,healthInsurance,duration,monthlyAmount,otherAmount,benifits;
        Button btnBuy;
        public PackageViewHolder(@NonNull View itemView) {
            super(itemView);
            planeId=itemView.findViewById(R.id.planeID);
            healthInsurance=itemView.findViewById(R.id.healthInsurance);
            duration=itemView.findViewById(R.id.duration);
            monthlyAmount=itemView.findViewById(R.id.monthlyAmount);
            otherAmount= itemView.findViewById(R.id.otherPayments);
            benifits= itemView.findViewById(R.id.benefits);
            btnBuy=itemView.findViewById(R.id.btnBuyNow);
        }
    }
}
