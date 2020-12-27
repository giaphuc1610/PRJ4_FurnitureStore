package pkg.Entities;

import java.util.List;
import javax.ejb.Local;

@Local
public interface OrdersDetailsFacadeLocal {

    void create(OrdersDetails paramOrdersDetails);

    void edit(OrdersDetails paramOrdersDetails);

    void remove(OrdersDetails paramOrdersDetails);

    OrdersDetails find(Object paramObject);

    List<OrdersDetails> findAll();

    List<OrdersDetails> findRange(int[] paramArrayOfInt);

    List<OrdersDetails> getOrdersDetails(String paramString);

    int count();

}
