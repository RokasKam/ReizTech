import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CashRegister {
    private TreeMap<Float, Integer> Cash = new TreeMap<>(Collections.reverseOrder());
    private TreeMap<Float, Integer> CopyOfCash;

    public CashRegister(TreeMap<Float, Integer> cash){
        Cash = cash;
    }

    public CashRegister() {
    }

    public TreeMap<Float, Integer> getCash() {
        return Cash;
    }

    public void setCash(TreeMap<Float, Integer> cash) {
        Cash = cash;
    }

    public void setNewCash(Float nominal, int amount){
        Cash.put(nominal, amount);
    }


    public HashMap<Float, Integer> CountChange(float wholeChange) throws NotEnoughChangeException {

        float leftChange = wholeChange;
        HashMap<Float, Integer> change = new HashMap<>();

        for (Map.Entry<Float, Integer> entry : CopyOfCash.entrySet()) {
            int howMuch = (int)(leftChange / entry.getKey());
            if(howMuch != 0) {
                if(howMuch> entry.getValue()) {
                    change.put(entry.getKey(), entry.getValue());
                    entry.setValue(0);
                    leftChange -= (entry.getKey() * entry.getValue());
                }
                else {
                    change.put(entry.getKey(), howMuch);
                    entry.setValue(entry.getValue() - howMuch);
                    leftChange = Math.round((leftChange - (entry.getKey() * howMuch)) * 10.0)/10.0f;
                }
            }
        }

        if(leftChange == 0){
            Cash = new TreeMap<>(CopyOfCash);
            return change;
        }
        else {
            throw new NotEnoughChangeException("Not enough change");
        }
    }
    public void addCash(float cash) throws PayNotAcceptedException {
        CopyOfCash = new TreeMap<>(Cash);
        if(Cash.containsKey(cash)){
            CopyOfCash.replace(cash, CopyOfCash.get(cash) + 1);
        }
        else{
            throw new PayNotAcceptedException("Payment is not acceptable");
        }
    }
}
