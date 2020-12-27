package pkg.Models;

public class BrandReport {

    private String brand;

    public BrandReport(String brand, double income) {
        this.brand = brand;
        this.income = income;
    }
    private double income;

    public BrandReport() {
    }

    public double getIncome() {
        return this.income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
