package pkg.Entities;

import java.util.List;
import javax.ejb.Local;

@Local
public interface RatingsFacadeLocal {

void create(Ratings paramRatings);
  
  void edit(Ratings paramRatings);
  
  void remove(Ratings paramRatings);
  
  Ratings find(Object paramObject);
  
  List<Ratings> findAll();
  
  List<Ratings> findRange(int[] paramArrayOfInt);
  
  List<Ratings> getByCustomer(String paramString);
  
  int count();
  
  double getAverageRatings(String paramString1, String paramString2);
  
  int getNumberOfRatings(String paramString1, String paramString2);
  
  int getNumberOfRatings(String paramString);
  
  Ratings getRating(String paramString1, String paramString2);
}
