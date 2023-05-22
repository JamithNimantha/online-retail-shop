package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Jamith Nimantha
 */
public class Shopper {

    String shopperId;
    Map<String, Integer> cart = new HashMap<>();

    public Shopper() {
        this.shopperId = UUID.randomUUID().toString();
    }

    public String getShopperId() {
        return shopperId;
    }

    public void setShopperId(String shopperId) {
        this.shopperId = shopperId;
    }

    public Map<String, Integer> getCart() {
        return cart;
    }

    public void setCart(Map<String, Integer> cart) {
        this.cart = cart;
    }

    public void addToCart(String itemId, int quantity) {
        cart.put(itemId, quantity);
    }

    public void removeFromCart(String itemId) {
        cart.remove(itemId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shopper shopper = (Shopper) o;
        return Objects.equals(shopperId, shopper.shopperId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shopperId);
    }
}
