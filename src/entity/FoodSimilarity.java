package entity;

/**
 * Created by Jing Xiang on 2/4/2017.
 */
public class FoodSimilarity {
    private String itemID;
    private String itemDesc;
    private String itemID2;
    private String itemDesc2;
    private double similarityVal;

    public FoodSimilarity(String itemID, String itemDesc, String itemID2, String itemDesc2, double similarityVal) {
        this.itemID = itemID;
        this.itemDesc = itemDesc;
        this.itemID2 = itemID2;
        this.itemDesc2 = itemDesc2;
        this.similarityVal = similarityVal;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getItemID2() {
        return itemID2;
    }

    public void setItemID2(String itemID2) {
        this.itemID2 = itemID2;
    }

    public String getItemDesc2() {
        return itemDesc2;
    }

    public void setItemDesc2(String itemDesc2) {
        this.itemDesc2 = itemDesc2;
    }

    public double getSimilarityVal() {
        return similarityVal;
    }

    public void setSimilarityVal(double similarityVal) {
        this.similarityVal = similarityVal;
    }
}
