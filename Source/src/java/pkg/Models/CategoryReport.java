package pkg.Models;

public class CategoryReport
{
  private String category;
  private double income;
  
  public CategoryReport(String category, double income) {
    this.category = category;
    this.income = income;
  }
  
  private double proportion;
  public CategoryReport() {}
  public String getCategory() { return this.category; }


  
  public void setCategory(String category) { this.category = category; }


  
  public double getIncome() { return this.income; }


  
  public void setIncome(double income) { this.income = income; }


  
  public double getProportion() { return this.proportion; }


  
  public void setProportion(double proportion) { this.proportion = proportion; }
}
