package pkg.Entities;

import java.util.List;
import javax.ejb.Local;

@Local
public interface WishlistFacadeLocal {

    void create(Wishlist paramWishlist);

    void edit(Wishlist paramWishlist);

    void remove(Wishlist paramWishlist);

    void removeAll(String paramString);

    Wishlist find(Object paramObject);

    Wishlist find(String paramString1, String paramString2);

    List<Wishlist> findAll();

    List<Wishlist> findRange(int[] paramArrayOfInt);

    List<Wishlist> getWishlist(String paramString);

    int count();

}
