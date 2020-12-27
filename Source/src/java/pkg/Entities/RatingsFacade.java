package pkg.Entities;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class RatingsFacade extends AbstractFacade<Ratings> implements RatingsFacadeLocal {

    @PersistenceContext(unitName = "MarcFashionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RatingsFacade() {
        super(Ratings.class);
    }

    @Override
    public List<Ratings> getByCustomer(String email) {
        Query q = this.em.createQuery("SELECT r FROM Ratings r WHERE r.customerEmail.email = :email");
        q.setParameter("email", email);
        return q.getResultList();
    }

    @Override
    public double getAverageRatings(String custEmail, String categoryID) {
        List<Ratings> list = getByCustomer(custEmail);
        int sum = 0, count = 0;
        for (Ratings r : list) {
            if (r.getProductID().getCategoryID().equals(categoryID)) {
                sum += r.getRate(); 
                count++; 
            }
        }
        return sum / count; 
    }

    @Override
    public Ratings getRating(String custEmail, String productID) {
        Ratings result;
        Query q = this.em.createQuery("SELECT r FROM Ratings r WHERE r.customerEmail.email = :custEmail and r.productID.productID = :productID");
        q.setParameter("custEmail", custEmail);
        q.setParameter("productID", productID);

        try {
            result = (Ratings) q.getSingleResult();
        } catch (NoResultException e) {
            result = null;
        }
        return result;
    }

    @Override
    public int getNumberOfRatings(String custEmail, String categoryID) {
        List<Ratings> list = getByCustomer(custEmail);
        int count = 0;
        for (Ratings r : list) {
            if (r.getProductID().getCategoryID().getCategoryID().equals(categoryID)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int getNumberOfRatings(String productID) {
        Query q = this.em.createQuery("SELECT COUNT(r) FROM Ratings r WHERE r.productID.productID = :productID");
        q.setParameter("productID", productID);
        return Integer.parseInt(q.getSingleResult().toString());
    }
}
