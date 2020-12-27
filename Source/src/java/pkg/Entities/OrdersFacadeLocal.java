package pkg.Entities;

import java.util.List;
import javax.ejb.Local;

@Local
public interface OrdersFacadeLocal {

    void create(Orders paramOrders);

    void edit(Orders paramOrders);

    void remove(Orders paramOrders);

    Orders find(Object paramObject);

    List<Orders> findAll();

    List<Orders> findRange(int[] paramArrayOfInt);

    List<Orders> getOrdersByUser(String paramString);

    List<Orders> getUncompletedOrdersByUser(String paramString);

    int count();

    String getNewID();
}
