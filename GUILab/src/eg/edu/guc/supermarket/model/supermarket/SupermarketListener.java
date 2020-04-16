package eg.edu.guc.supermarket.model.supermarket;

import eg.edu.guc.supermarket.model.cart.Cart;

public interface SupermarketListener {
	void onCartUpdated(Cart cart);
}
