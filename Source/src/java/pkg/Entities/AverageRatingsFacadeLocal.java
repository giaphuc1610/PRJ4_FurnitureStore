package pkg.Entities;

import java.util.List;
import javax.ejb.Local;

@Local
public interface AverageRatingsFacadeLocal {

    void create(AverageRatings paramAverageRatings);

    void edit(AverageRatings paramAverageRatings);

    void remove(AverageRatings paramAverageRatings);

    AverageRatings find(Object paramObject);

    List<AverageRatings> findAll();

    List<AverageRatings> findRange(int[] paramArrayOfInt);

    int count();

    double getAverageRating(String paramString);

}
