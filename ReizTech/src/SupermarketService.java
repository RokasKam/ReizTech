import java.io.IOException;

public interface SupermarketService {
    void printData (boolean initial);
    void chooseProduct() throws IOException;
    void Payment() throws IOException;
    void Change();
}
