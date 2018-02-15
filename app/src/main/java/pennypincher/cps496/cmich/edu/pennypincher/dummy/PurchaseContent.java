package pennypincher.cps496.cmich.edu.pennypincher.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import pennypincher.cps496.cmich.edu.pennypincher.Purchase;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class PurchaseContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Purchase> ITEMS = new ArrayList<Purchase>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Purchase> ITEM_MAP = new HashMap<String, Purchase>();

    private static void addItem(Purchase item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.GetTOP(), item);
    }

    private static void clear(){
        ITEMS.clear();
    }

    private static PurchaseItem createPurchaseItem(PurchaseItem PItem) {
        return PItem;
    }

    public static class PurchaseItem {
        public final Double Amount;
        public final String Category;
        public final byte[] ImPath;
        public final String TOP;

        public PurchaseItem(Double Amount, String Category, byte[] ImPath, String TOP) {
            this.Amount = Amount;
            this.Category = Category;
            this.ImPath = ImPath;
            this.TOP = TOP;
        }

        @Override
        public String toString() {
            return "Amount: " + this.Amount + " Category: " + this.Category + " ImPath: " + this.ImPath
                    + " TOP: " + this.TOP;
        }
    }
}