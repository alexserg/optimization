package ru.sberbank.optdemo2;

import com.sun.tools.javac.jvm.Items;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo2")
public class Opdemo2Controller {

	@RequestMapping("/fibonacci")
	public @ResponseBody long fibonacci(@RequestParam("of") long n) {
		return fib(n);
	}

	private long fib(long n) {
		if (n == 0) return 0;
		if (n == 1) return 1;
		return fib(n - 1) + fib(n - 2);
	}

	/**   knapsack    **/

	static int[] values = new int[] {894, 260, 392, 281, 27};
	static int[] weights = new int[] {8, 6, 4, 0, 21};

	@RequestMapping("/knapsack")
	public @ResponseBody int knapsack(@RequestParam("weight") int weight) {
		Item[] items = {
				new Item("Item1", 4, 12),
				new Item("Item2", 2, 1),
				new Item("Item3", 2, 2),
				new Item("Item4", 6, 1),
				new Item("Item5", 10, 4),
				new Item("Item1", 4, 12),
				new Item("Item2", 2, 1),
				new Item("Item3", 2, 9),
				new Item("Item4", 1, 1),
				new Item("Item5", 10, 12),
				new Item("Item1", 4, 12),
				new Item("Item2", 2, 1),
				new Item("Item3", 5, 5),
				new Item("Item4", 1, 8),
				new Item("Item5", 10, 4),
				new Item("Item1", 4, 12),
				new Item("Item2", 2, 1),
				new Item("Item3", 2, 2),
				new Item("Item4", 6, 1),
				new Item("Item5", 10, 4),
				new Item("Item1", 4, 12),
				new Item("Item2", 2, 1),
				new Item("Item3", 2, 9),
				new Item("Item4", 1, 1),
				new Item("Item5", 10, 12),
				new Item("Item1", 4, 12),
				new Item("Item2", 2, 1),
				new Item("Item3", 5, 5),
				new Item("Item4", 1, 8),
				new Item("Item5", 10, 4)
		};
		int result = knapsack(items,items.length - 1, weight);
		return result;
	}

	private int knapsack(Item[] items, int i, int W) {
		if (i < 0) {
			return 0;
		}
		if (items[i].weight > W) {
			return knapsack(items, i-1, W);
		} else {
			int weDoNotPutItem = knapsack(items, i-1, W);
			int wePutItem = knapsack(items, i-1, W - items[i].weight) + items[i].value;
			if (weDoNotPutItem > wePutItem) {
				return weDoNotPutItem;
			} else {
				return wePutItem;
			}
		}
	}
}

