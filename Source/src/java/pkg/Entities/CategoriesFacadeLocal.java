package pkg.Entities;

import java.util.List;
import javax.ejb.Local;

@Local
public interface CategoriesFacadeLocal {

    void create(Categories paramCategories);

    void edit(Categories paramCategories);

    void remove(Categories paramCategories);

    Categories find(Object paramObject);

    List<Categories> findAll();

    List<Categories> findRange(int[] paramArrayOfInt);

    int count();

    String getFirstType(String paramString);

}
