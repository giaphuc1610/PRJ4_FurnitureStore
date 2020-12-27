package pkg.Entities;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class TypesFacade extends AbstractFacade<Types> implements TypesFacadeLocal {

    @PersistenceContext(unitName = "MarcFashionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TypesFacade() {
        super(Types.class);
    }

    @Override
    public boolean containCat(Types type, String catID) {
        for (Products p : type.getProductsCollection()) {
            if (p.getCategoryID().getCategoryID().equals(catID)) {
                return true;
            }
        }
        return false;
    }

}
