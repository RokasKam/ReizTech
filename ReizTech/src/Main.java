import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        SupermarketServiceImpl.getInstance().printData(true);
        while(true) {
            SupermarketServiceImpl.getInstance().chooseProduct();
            SupermarketServiceImpl.getInstance().Payment();
            SupermarketServiceImpl.getInstance().Change();
            SupermarketServiceImpl.getInstance().printData(false);
        }
    }

}
