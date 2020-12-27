package pkg.Entities;

import java.util.List;

public class CustomersHelper {

    public static Customers findCustomer(List<Customers> customers, String customerId) {
        for (Customers c : customers) {
            if (c.getEmail().equals(customerId)) {
                return c;
            }
        }
        return null;
    }
}
