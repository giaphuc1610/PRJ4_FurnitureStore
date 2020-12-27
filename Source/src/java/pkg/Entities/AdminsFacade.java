package pkg.Entities;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class AdminsFacade extends AbstractFacade<Admins> implements AdminsFacadeLocal {

    @PersistenceContext(unitName = "MarcFashionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdminsFacade() {
        super(Admins.class);
    }

    @Override
    public Admins checkLogin(final String email, final String password) {
        final Query q = this.em.createQuery("SELECT a FROM Admins a WHERE a.email = :email and a.password = :password");
        q.setParameter("email", (Object) email);
        q.setParameter("password", (Object) password);
        try {
            return (Admins) q.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

}
