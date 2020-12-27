package pkg.Entities;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class BrandsFacade extends AbstractFacade<Brands> implements BrandsFacadeLocal {
    @PersistenceContext(unitName = "MarcFashionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BrandsFacade() {
        super(Brands.class);
    }
    
}
