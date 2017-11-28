package pennypincher.cps496.cmich.edu.pennypincher;

/**
 * Created by tony on 11/27/2017.
 */

public class CategoryInfo {
    private String category;
    private Double amount;

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory(){
        return category;
    }

    public Double getAmount(){
        return amount;
    }

    public void clear(){
        category = "";
        amount = 0.00;
    }
}
