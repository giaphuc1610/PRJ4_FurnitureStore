package pkg.Entities;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CategoriesFacade extends AbstractFacade<Categories> implements CategoriesFacadeLocal {

    @PersistenceContext(unitName = "MarcFashionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoriesFacade() {
        super(Categories.class);
    }

    @Override
    public String getFirstType(String categoryID) {
        for (Products p : ((Categories) find(categoryID)).getProductsCollection()) {
            if (p.getProductState().booleanValue()) {
                return p.getTypeID().getTypeID();
            }
        }
        return "all";
    }

}
