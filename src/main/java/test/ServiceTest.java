package test;

import service.AdminService;
import service.ProductService;

public class ServiceTest {
    public static void main(String[] args) {
//        System.out.println(ProductService.getAddedProductIn(1).size());
        System.out.println(AdminService.getAllAccounts().size());
    }
}
