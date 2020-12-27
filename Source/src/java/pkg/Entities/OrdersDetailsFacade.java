package pkg.Entities;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class OrdersDetailsFacade extends AbstractFacade<OrdersDetails> implements OrdersDetailsFacadeLocal {

    @PersistenceContext(unitName = "MarcFashionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrdersDetailsFacade() {
        super(OrdersDetails.class);
    }

    @Override
    public List<OrdersDetails> getOrdersDetails(String orderID) {
        Query q = this.em.createQuery("SELECT o FROM OrdersDetails o WHERE o.orderID.orderID = :orderID");
        q.setParameter("orderID", orderID);
        return q.getResultList();
    }

}
