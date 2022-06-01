import java.util.ArrayList;
import java.util.Objects;

public class ProductStorage {
    private ArrayList<Product> Products = new ArrayList<>();

    public ProductStorage(ArrayList<Product> products) {
        Products = products;
    }

    public ProductStorage() {
    }

    public ArrayList<Product> getProducts() {
        return Products;
    }

    public void setProducts(ArrayList<Product> products) {
        Products = products;
    }

    public void addNewProduct(Product product){
        Products.add(product);
    }
    public void reduceAmount(Product product) {
        Products.stream().filter((v) -> Objects.equals(v, product)).findFirst().get().reduceAmount();

    }
    public Product getProduct(String name) throws SoldOutException {

        Product product = Products.stream().filter((v) -> Objects.equals(v.getName(), name)).findFirst().orElse(null);

        if(product == null){
            throw new SoldOutException("This product doesn't exist or is sold out. Try other product");
        }
        else{
            return product;
        }
    }

}
