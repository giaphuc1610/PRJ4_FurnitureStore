package pkg.Entities;

import java.util.List;
import javax.ejb.Local;

@Local
public interface BrandsFacadeLocal {

    void create(Brands paramBrands);

    void edit(Brands paramBrands);

    void remove(Brands paramBrands);

    Brands find(Object paramObject);

    List<Brands> findAll();

    List<Brands> findRange(int[] paramArrayOfInt);

    int count();

}
