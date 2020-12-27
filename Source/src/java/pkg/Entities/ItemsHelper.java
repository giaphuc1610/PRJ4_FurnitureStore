package pkg.Entities;

import java.util.ArrayList;
import java.util.List;

public class ItemsHelper {

    public static List<AverageRatings> findItems(List<AverageRatings> items, List<String> movieIds) {
        List<AverageRatings> list = new ArrayList<AverageRatings>();
        for (String movieId : movieIds) {
            list.add(findItem(items, movieId));
        }
        return list;
    }

    public static AverageRatings findItem(List<AverageRatings> items, String movieId) {
        for (AverageRatings m : items) {
            if (m.getProductID().equals(movieId)) {
                return m;
            }
        }
        return null;
    }
}
