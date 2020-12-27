package pkg.Entities;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import lib.Result;

@Stateless
public class ProductsFacade extends AbstractFacade<Products> implements ProductsFacadeLocal {

    @PersistenceContext(unitName = "MarcFashionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductsFacade() {
        super(Products.class);
    }

    @Override
    public List<Products> getProductsForGuest(Result result) {
        List<Products> allProducts = findAll();
        List<Products> productsForGuest = new ArrayList<Products>();
        try {
            List<String> topYmean = result.getListOfItemsForGuest(allProducts.size() - 1);
            for (String id : topYmean) {
                for (Products p : allProducts) {
                    if (p.getProductState().booleanValue() && p.getProductID().equals(id)) {
                        productsForGuest.add(p);
                        if (productsForGuest.size() == 10) {
                            return productsForGuest;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productsForGuest;
    }

    @Override
    public List<Products> getProductsForUser(Result result, String userEmail, String categoryID) {
        List<Products> allProducts = findAll();
        List<Products> productsForUser = new ArrayList<Products>();
        try {
            List<String> topYmean = result.getListOfItemsForUser(allProducts.size() - 1, userEmail);
            for (String id : topYmean) {
                for (Products p : allProducts) {
                    if (p.getProductState().booleanValue() && p.getProductID().equals(id) && p.getCategoryID().getCategoryID().equals(categoryID)) {
                        productsForUser.add(p);
                        if (productsForUser.size() == 10) {
                            return productsForUser;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productsForUser;
    }

    @Override
    public List<Products> getFeaturedProducts(String feature) {
        Query q = this.em.createQuery("SELECT p FROM Products p WHERE p.feature = :feature AND p.productState = TRUE ORDER BY p.price DESC");
        q.setParameter("feature", feature);
        q.setMaxResults(3);
        return q.getResultList();
    }

    @Override
    public List<Products> getRelatedProducts(Result result, String productID) {
        List<Products> allProducts = findAll();
        List<Products> relatedProducts = new ArrayList<Products>();
        try {
            Products selected = (Products) find(productID);
            for (String id : result.getRelatedItems(allProducts.size() - 1, productID)) {
                for (Products p : allProducts) {
                    if (p.getProductState().booleanValue() && p.getProductID().equals(id) && p.getTypeID().getTypeID().equals(selected.getTypeID().getTypeID()) && p.getCategoryID().getCategoryID().equals(selected.getCategoryID().getCategoryID())) {
                        relatedProducts.add(p);
                        if (relatedProducts.size() == 4) {
                            return relatedProducts;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return relatedProducts;
    }

    @Override
    public Long getMinPrice() {
        return (Long) this.em.createQuery("SELECT MIN(p.price) FROM Products p").getSingleResult();
    }

    @Override
    public Long getMaxPrice() {
        return (Long) this.em.createQuery("SELECT MAX(p.price) FROM Products p").getSingleResult();
    }

    @Override
    public List<Products> searchByPrice(int from, int to) {
        Query q = this.em.createQuery("SELECT p FROM Products p WHERE p.price BETWEEN :from AND :to AND p.productState = TRUE ORDER BY p.price ASC");
        q.setParameter("from", Integer.valueOf(from));
        q.setParameter("to", Integer.valueOf(to));
        return q.getResultList();
    }

    @Override
    public List<Products> searchByCatType(Categories cat, Types type) {
        Query q = this.em.createQuery("SELECT p FROM Products p WHERE p.categoryID = :cat AND p.typeID = :type AND p.productState = TRUE ORDER BY p.price ASC");
        q.setParameter("cat", cat);
        q.setParameter("type", type);
        return q.getResultList();
    }

    @Override
    public long byCatType(String cat, String type) {
        Query q = null;
        if (!type.equals("all")) {
            q = this.em.createQuery("SELECT COUNT(p.productID) FROM Products p WHERE p.categoryID.categoryID = :cat AND p.typeID.typeID = :type AND p.productState = TRUE");
            q.setParameter("type", type);
        } else {
            q = this.em.createQuery("SELECT COUNT(p.productID) FROM Products p WHERE p.categoryID.categoryID = :cat AND p.productState = TRUE");
        }
        q.setParameter("cat", cat);
        return ((Long) q.getSingleResult()).longValue();
    }

    @Override
    public List<String> getProductNames() {
        return this.em.createQuery("SELECT p.productName FROM Products p WHERE p.productState = TRUE").getResultList();
    }

    @Override
    public List<Products> searchByName(String search) {
        Query q = this.em.createQuery("SELECT p FROM Products p WHERE p.productName LIKE :search AND p.productState = TRUE ORDER BY p.price ASC");
        q.setParameter("search", "%" + search + "%");
        return q.getResultList();
    }
}
