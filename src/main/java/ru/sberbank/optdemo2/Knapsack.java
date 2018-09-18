package ru.sberbank.optdemo2;

import java.util.ArrayList;
import java.util.List;

public class Knapsack {
    // items of our problem
    private Item[] items;
    // capacity of the bag
    private int capacity;

//    // we take the same instance of the problem displayed in the image
//    Item[] items = {new Item("Elt1", 4, 12),
//            new Item("Elt2", 2, 1),
//            new Item("Elt3", 2, 2),
//            new Item("Elt4", 1, 1),
//            new Item("Elt5", 10, 4)};
//
//    Knapsack knapsack = new Knapsack(items, 15);
//    String fullDisplay = knapsack.display();
//    Solution solution = knapsack.solve();
//		return fullDisplay + "\n" + solution.display();

    public Knapsack(Item[] items, int capacity) {
        this.items = items;
        this.capacity = capacity;
    }

    public String display() {
        String display = "";
        if (items != null  &&  items.length > 0) {
            display += "Knapsack problem\n";
            display += "Capacity : " + capacity + "\n";
            display += "Items :\n";

            for (Item item : items) {
                display += "- " + item.str() + "\n";
            }
        }
        return display;
    }

    // we write the solve algorithm
    public Solution solve() {
        int NB_ITEMS = items.length;
        // we use a matrix to store the max value at each n-th item
        int[][] matrix = new int[NB_ITEMS + 1][capacity + 1];

        // first line is initialized to 0
        for (int i = 0; i <= capacity; i++)
            matrix[0][i] = 0;

        // we iterate on items
        for (int i = 1; i <= NB_ITEMS; i++) {
            // we iterate on each capacity
            for (int j = 0; j <= capacity; j++) {
                if (items[i - 1].weight > j)
                    matrix[i][j] = matrix[i-1][j];
                else
                    // we maximize value at this rank in the matrix
                    matrix[i][j] = Math.max(matrix[i-1][j], matrix[i-1][j - items[i-1].weight]
                            + items[i-1].value);
            }
        }

        int res = matrix[NB_ITEMS][capacity];
        int w = capacity;
        List<Item> itemsSolution = new ArrayList<>();

        for (int i = NB_ITEMS; i > 0  &&  res > 0; i--) {
            if (res != matrix[i-1][w]) {
                itemsSolution.add(items[i-1]);
                // we remove items value and weight
                res -= items[i-1].value;
                w -= items[i-1].weight;
            }
        }

        return new Solution(itemsSolution, matrix[NB_ITEMS][capacity]);
    }
}
