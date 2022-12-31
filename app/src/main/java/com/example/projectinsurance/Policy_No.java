package com.example.projectinsurance;

public class Policy_No {
    private String policy_id;
    private String policy_no;
    private String policy_holder;
    private String product;
    private String insurance_date;
    private String due_date;
    private String category;

    public Policy_No() {

    }

    public Policy_No(String policy_no, String category) {
        this.policy_no = policy_no;
        this.category = category;
    }

    public Policy_No(String policy_id, String policy_no, String policy_holder, String product, String insurance_date, String due_date, String category) {
        this.policy_id = policy_id;
        this.policy_no = policy_no;
        this.policy_holder = policy_holder;
        this.product = product;
        this.insurance_date = insurance_date;
        this.due_date = due_date;
        this.category = category;
    }

    public String getPolicy_id() {
        return policy_id;
    }

    public void setPolicy_id(String policy_id) {
        this.policy_id = policy_id;
    }

    public String getPolicy_no() {
        return policy_no;
    }

    public void setPolicy_no(String policy_no) {
        this.policy_no = policy_no;
    }

    public String getPolicy_holder() {
        return policy_holder;
    }

    public void setPolicy_holder(String policy_holder) {
        this.policy_holder = policy_holder;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getInsurance_date() {
        return insurance_date;
    }

    public void setInsurance_date(String insurance_date) {
        this.insurance_date = insurance_date;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}