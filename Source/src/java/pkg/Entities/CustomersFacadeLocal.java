package pkg.Entities;

import java.util.List;
import javax.ejb.Local;

@Local
public interface CustomersFacadeLocal {

    void create(Customers paramCustomers);

    void edit(Customers paramCustomers);

    void remove(Customers paramCustomers);

    Customers find(Object paramObject);

    List<Customers> findAll();

    List<Customers> findRange(int[] paramArrayOfInt);

    int count();

    Customers checkLogin(String paramString1, String paramString2);
}
