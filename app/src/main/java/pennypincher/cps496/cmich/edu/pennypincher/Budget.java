package pennypincher.cps496.cmich.edu.pennypincher;

/**
 * Created by noah on 11/30/17.
 */

public class Budget {

    public double amount;
    public String date;

    public Budget() {};
    public Budget(double amount, String date) {
        this.amount = amount;
        this.date = date;
    }
    public void SetAmount(double amount){this.amount = amount;}

    public double GetAmount(){return amount;}

    public void SetDate(String date){this.date = date;}

    public String GetDate(){return date;}
}
