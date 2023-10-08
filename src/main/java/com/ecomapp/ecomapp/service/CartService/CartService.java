package com.ecomapp.ecomapp.service.CartService;

import com.ecomapp.ecomapp.model.Cart;
import com.ecomapp.ecomapp.model.CartItem;
import com.ecomapp.ecomapp.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartService {
    List<Cart> cartList(Cart cart);

    void addToCartItem(String email, UUID productId);
    void  addQuantity(String username, UUID cartId, int quantity);

    List<Cart>  getCartItems(String username);

    double getTotalPrice(String username);

    void removeCart(String username);

    Cart save (Cart cart);




}


