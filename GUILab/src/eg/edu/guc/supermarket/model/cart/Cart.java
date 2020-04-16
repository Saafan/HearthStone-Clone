package eg.edu.guc.supermarket.model.cart;

import java.util.ArrayList;

import eg.edu.guc.supermarket.model.product.GroceryProduct;
import eg.edu.guc.supermarket.model.product.GroceryProductListener;

public class Cart implements GroceryProductListener {
	private ArrayList<GroceryProduct> products;
	private CartListener listener;

	public Cart() {
		products = new ArrayList<>();
	}

	public ArrayList<GroceryProduct> getProducts() {
		return products;
	}

	public void setListener(CartListener listener) {
		this.listener = listener;
	}

	public double getTotal() {
		double total = 0;
		for (GroceryProduct product : products) {
			total += product.getActualPrice();
		}
		return total;
	}

	@Override
	public void onBought(GroceryProduct product) {
		products.add(product);

		if (listener != null) {
			listener.onCartUpdated();
		}
	}
}
