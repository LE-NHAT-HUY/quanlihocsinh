package com.quanlihocsinh.model;

public class ChartData {
    private String label; // Nhãn hiển thị (ví dụ: "Lớp 10A1", "Giỏi")
    private double value; // Giá trị số liệu

    public ChartData() {
    }

    public ChartData(String label, double value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}