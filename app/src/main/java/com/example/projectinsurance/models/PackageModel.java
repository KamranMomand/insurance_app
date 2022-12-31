package com.example.projectinsurance.models;

public class PackageModel {
    String plans_id, policy_type, other_payment, monthly_amount, duration, benefits;

    public PackageModel(String plans_id, String policy_type, String other_payment, String monthly_amount, String duration, String benefits) {
        this.plans_id = plans_id;
        this.policy_type = policy_type;
        this.other_payment = other_payment;
        this.monthly_amount = monthly_amount;
        this.duration = duration;
        this.benefits = benefits;
    }

    public String getPlans_id() {
        return plans_id;
    }

    public void setPlans_id(String plans_id) {
        this.plans_id = plans_id;
    }

    public String getPolicy_type() {
        return policy_type;
    }

    public void setPolicy_type(String policy_type) {
        this.policy_type = policy_type;
    }

    public String getOther_payment() {
        return other_payment;
    }

    public void setOther_payment(String other_payment) {
        this.other_payment = other_payment;
    }

    public String getMonthly_amount() {
        return monthly_amount;
    }

    public void setMonthly_amount(String monthly_amount) {
        this.monthly_amount = monthly_amount;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }
}