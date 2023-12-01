package test.service;

import model.Admin;
import model.Product;
import service.ProductService;

import java.util.List;

public class TestProductService {

    public TestProductService() {
    }

    public void Test_AddNewProduct() {

        Admin admin = new Admin("tranminhtuyen", "123", 1, 1, "Trần Minh Tuyên");
        Product p = new Product("img_2", "tranminhtuyen", "tuyen_kun", 10, 200000, 100000, 1, 1, 1);
        System.out.println(ProductService.addNewProduct(p, admin));
    }

    public void Test_GetSubTypeAndStatusAndSupplierForProduct() {
        List<Object> listResult = ProductService.getSubTypeAndStatusAndSupplierForProduct();
        for (Object o : listResult) {
            System.out.println(o);
        }
    }

    public void Test_GetAllProducts() {
        System.out.println(ProductService.getAllProducts());
    }

    public void Test_DeleteProduct() {
        System.out.println(ProductService.deleteProductById(1));
    }

    public void Test_UpdateProduct() {
        Admin admin = new Admin("tuyen_kun", "123");
        Product p = new Product(2, "Tuyen-Acid", 9999, 789000, 81000, 2, 3);
        System.out.println(ProductService.updateProductById(p, admin));
    }

    public static void main(String[] args) {

        TestProductService test_service = new TestProductService();
        // test_service.Test_AddNewProduct();
        // test_service.Test_GetSubTypeAndStatusAndSupplierForProduct();
        //test_service.Test_DeleteProduct();
        //test_service.Test_GetAllProducts();
        test_service.Test_UpdateProduct();
    }
}
