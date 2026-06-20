package com.ebanking.model;

public class Feature {
    private String featureCode;
    private String featureName;
    private double fee;

    public Feature() {}

    public Feature(String featureCode, String featureName, double fee) {
        this.featureCode = featureCode;
        this.featureName = featureName;
        this.fee = fee;
    }

    public String getFeatureCode() { return featureCode; }
    public void setFeatureCode(String featureCode) { this.featureCode = featureCode; }

    public String getFeatureName() { return featureName; }
    public void setFeatureName(String featureName) { this.featureName = featureName; }

    public double getFee() { return fee; }
    public void setFee(double fee) { this.fee = fee; }
}
