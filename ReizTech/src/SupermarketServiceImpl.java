import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class SupermarketServiceImpl implements SupermarketService{
    private static final SupermarketServiceImpl supermarketService = new SupermarketServiceImpl();
    private final ProductStorage productStorage = new ProductStorage();
    private final CashRegister cashRegister = new CashRegister();
    private Product chosenProduct;
    private final BufferedReader bw = new BufferedReader(new InputStreamReader(System.in));
    private  float wholeChange = 0.0f;

    private SupermarketServiceImpl() {
        productStorage.addNewProduct(new Product("SODA", 10, 2.3f));
        productStorage.addNewProduct(new Product("BREAD", 10, 1.1f));
        productStorage.addNewProduct(new Product("WINE", 10, 2.7f));

        cashRegister.setNewCash(0.1f,50);
        cashRegister.setNewCash(0.5f,50);
        cashRegister.setNewCash(1f,50);
        cashRegister.setNewCash(2f,50);
        cashRegister.setNewCash(5f,50);


    }

    public static SupermarketServiceImpl getInstance(){
        return supermarketService;
    }

    @Override
    public void printData(boolean initial){
        System.out.println("--------------------");
        if(initial)
            System.out.println("Initial Product Inventory:");
        else
            System.out.println("Updated Product Inventory:");

        productStorage.getProducts().stream().forEach((v) ->
            System.out.println(v.getName() + " Quantity: " + v.getAmount())
        );

        if(initial)
            System.out.println("Initial Cash Inventory:");
        else
            System.out.println("Updated Cash Inventory:");

        for(Map.Entry<Float, Integer> entry : cashRegister.getCash().entrySet()){
            System.out.println("Value: " + entry.getKey() + ", quantity: " + entry.getValue());
        }
        System.out.println("--------------------");
    }

    @Override
    public void chooseProduct() throws IOException {
        chosenProduct = new Product();
        System.out.println("What would you like to buy? Type in the name of the desired product.");
        StringBuilder prices = new StringBuilder();
        productStorage.getProducts().stream().forEach((v) ->
            prices.append(v.getName()).append(" (price: ").append(v.getPrice()).append(") ")
        );
        System.out.println(prices);
        while(!productStorage.getProducts().contains(chosenProduct)){
            String name = bw.readLine();
            try{
                chosenProduct = productStorage.getProduct(name);

            }
            catch (SoldOutException e){
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void Payment() throws IOException {
        wholeChange = -1*chosenProduct.getPrice();
        float paidMoney = 0;

        System.out.println("You are trying to buy " + chosenProduct.getName() + ". You need to pay " + chosenProduct.getPrice());
        StringBuilder nominal = new StringBuilder("Provide bill or coin (accepted values: ");
        for(Map.Entry<Float, Integer> entry : cashRegister.getCash().entrySet()){
           nominal.append(entry.getKey()).append(", ");
        }
        nominal.delete(nominal.length()-2, nominal.length()).append(")");
        System.out.println(nominal);
        while (wholeChange < 0){
            if(paidMoney != 0){
                System.out.println("You paid " + paidMoney + " in total. You still need to pay " + Math.round(wholeChange * (-10.0)) / 10.0f);
                System.out.println(nominal);
            }
            float money = Float.parseFloat(bw.readLine());
            try {
                cashRegister.addCash(money);
                wholeChange += money;
                paidMoney += money;
            }
            catch (PayNotAcceptedException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("You paid " + paidMoney + " in total. Your change will be " + Math.round(wholeChange * 10.0) / 10.0f);
    }

    @Override
    public void Change(){
        HashMap<Float, Integer> change;
        try {
            change = cashRegister.CountChange(wholeChange);
            productStorage.reduceAmount(chosenProduct);
            System.out.println("Here is you change:");
            for(Map.Entry<Float, Integer> entry : change.entrySet()){
                System.out.println("Value: " + entry.getKey() + ", quantity: " + entry.getValue());
            }
            System.out.println("Here is your product: " + chosenProduct.getName());
        }
        catch (NotEnoughChangeException e){
            System.out.println(e.getMessage());
        }
    }

}
