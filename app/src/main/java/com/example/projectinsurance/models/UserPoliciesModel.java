package com.example.projectinsurance.models;

public class UserPoliciesModel {
    String policyID, policyNumber, holderID, holderName, insuranceDate, dueDate, policyType, productName;


    public UserPoliciesModel(){

    }

    public UserPoliciesModel(String policyID, String policyNumber, String holderID, String holderName, String insuranceDate, String dueDate, String policyType, String productName) {
        this.policyID = policyID;
        this.policyNumber = policyNumber;
        this.holderID = holderID;
        this.holderName = holderName;
        this.insuranceDate = insuranceDate;
        this.dueDate = dueDate;
        this.policyType = policyType;
        this.productName = productName;
    }

    public String getPolicyID() {
        return policyID;
    }

    public void setPolicyID(String policyID) {
        this.policyID = policyID;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getHolderID() {
        return holderID;
    }

    public void setHolderID(String holderID) {
        this.holderID = holderID;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getInsuranceDate() {
        return insuranceDate;
    }

    public void setInsuranceDate(String insuranceDate) {
        this.insuranceDate = insuranceDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}