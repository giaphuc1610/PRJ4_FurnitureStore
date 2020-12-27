package pkg.Entities;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class OrdersFacade extends AbstractFacade<Orders> implements OrdersFacadeLocal {

    @PersistenceContext(unitName = "MarcFashionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrdersFacade() {
        super(Orders.class);
    }

    @Override
    public String getNewID() {
        Query q = this.em.createQuery("SELECT o.orderID FROM Orders o");
        List<String> orderIDs = q.getResultList();
        int max = 0;
        for (String id : orderIDs) {
            int idNum = Integer.valueOf(id).intValue();
            if (max < idNum) {
                max = idNum;
            }
        }
        return (max + 1) + "";
    }

    @Override
    public List<Orders> getOrdersByUser(String email) {
        Query q = this.em.createQuery("SELECT o FROM Orders o WHERE o.customerEmail.email = :email AND o.orderState = true");
        q.setParameter("email", email);
        return q.getResultList();
    }

    @Override
    public List<Orders> getUncompletedOrdersByUser(String email) {
        Query q = this.em.createQuery("SELECT o FROM Orders o WHERE o.customerEmail.email = :email and o.processStatus != 'Completed' and o.orderState = TRUE");
        q.setParameter("email", email);
        return q.getResultList();
    }

}
