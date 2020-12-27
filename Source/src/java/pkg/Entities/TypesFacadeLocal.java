package pkg.Entities;

import java.util.List;
import javax.ejb.Local;

@Local
public interface TypesFacadeLocal {

    void create(Types paramTypes);

    void edit(Types paramTypes);

    void remove(Types paramTypes);

    Types find(Object paramObject);

    List<Types> findAll();

    List<Types> findRange(int[] paramArrayOfInt);

    int count();

    boolean containCat(Types paramTypes, String paramString);

}
