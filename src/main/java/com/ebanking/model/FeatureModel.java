/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ebanking.model;

/**
 *
 * @author natan
 */
public class FeatureModel {
    private String featureCode;
    private String featureName;
    private double fee;

    public FeatureModel(String featureCode, String featureName, double fee) {
        this.featureCode = featureCode;
        this.featureName = featureName;
        this.fee = fee;
    }

    public String getFeatureCode() { return featureCode; }
    public String getFeatureName() { return featureName; }
    public double getFee() { return fee; }

    // Di-override agar teks ini yang muncul di dalam JComboBox
    @Override
    public String toString() {
        return featureName + " (Biaya: Rp " + fee + ")";
    }
}
