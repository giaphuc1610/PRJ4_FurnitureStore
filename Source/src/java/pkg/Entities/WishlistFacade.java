package pkg.Entities;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class WishlistFacade extends AbstractFacade<Wishlist> implements WishlistFacadeLocal {

    @PersistenceContext(unitName = "MarcFashionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WishlistFacade() {
        super(Wishlist.class);
    }

    @Override
    public List<Wishlist> getWishlist(String email) {
        Query q = this.em.createQuery("SELECT w FROM Wishlist w WHERE w.customerEmail.email = :email");
        q.setParameter("email", email);
        return q.getResultList();
    }

    @Override
    public void removeAll(String email) {
        Query q = this.em.createQuery("DELETE FROM Wishlist w WHERE w.customerEmail.email = :email");
        q.setParameter("email", email);
        q.executeUpdate();
    }

    @Override
    public Wishlist find(String email, String productID) {
        Query q = this.em.createQuery("SELECT w FROM Wishlist w WHERE w.customerEmail.email = :email and w.productID.productID = :productID");
        q.setParameter("email", email);
        q.setParameter("productID", productID);
        Wishlist item = null;
        try {
            item = (Wishlist) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return item;
    }
}
