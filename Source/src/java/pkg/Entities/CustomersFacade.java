package pkg.Entities;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class CustomersFacade extends AbstractFacade<Customers> implements CustomersFacadeLocal {

    @PersistenceContext(unitName = "MarcFashionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomersFacade() {
        super(Customers.class);
    }

    @Override
    public Customers checkLogin(String username, String password) {
        Query q = this.em.createQuery("SELECT c FROM Customers c WHERE c.email = :email and c.password = :password and c.customerState = 1");
        q.setParameter("email", username);
        q.setParameter("password", password);
        try {
            return (Customers) q.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

}
