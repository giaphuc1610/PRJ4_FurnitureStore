package pkg.Entities;

import java.util.List;
import javax.ejb.Local;

@Local
public interface AdminsFacadeLocal {

    void create(Admins admins);

    void edit(Admins admins);

    void remove(Admins admins);

    Admins find(Object id);

    List<Admins> findAll();

    List<Admins> findRange(int[] range);

    int count();
    
    Admins checkLogin(final String p0, final String p1);
    
}
