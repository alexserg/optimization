package ru.sberbank.optdemo2;


import java.util.List;

public class Solution {

    // list of items to put in the bag to have the maximal value
    public List<Item> items;
    // maximal value possible
    public int value;

    public Solution(List<Item> items, int value) {
        this.items = items;
        this.value = value;
    }

    public String display() {
        String display = "";
        if (items != null  &&  !items.isEmpty()){
            display += "Knapsack solution\n";
            display += "Value = " + value + "\n";
            display += "Items to pick :\n";

            for (Item item : items) {
                display += "- " + item.str() + "\n";
            }
        }
        return display;
    }

}
