package com.ecomapp.ecomapp.service.CartService;

import com.ecomapp.ecomapp.model.Cart;
import com.ecomapp.ecomapp.model.CartItem;
import com.ecomapp.ecomapp.model.User;
import com.ecomapp.ecomapp.repository.CartItemRepository;
import com.ecomapp.ecomapp.repository.CartRepository;
import com.ecomapp.ecomapp.repository.ProductRepo.ProductRepository;
import com.ecomapp.ecomapp.repository.UserRepository;
import com.ecomapp.ecomapp.service.ProductService.ProductService;
import com.ecomapp.ecomapp.service.UserService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    UserService userService;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }


    @Override
    public List<Cart> cartList(Cart cart) {
        return cartRepository.findAll();
    }






    @Override
    public void addToCartItem(String email, UUID productId) {
        System.out.println("======================================================================================================");
        User user = userRepository.findByEmail(email);


        Optional<Cart> existingCart = cartRepository.findByUserAndProduct(user, productRepository.findById(productId).orElse(null));

        if (existingCart.isPresent()) {

            Cart cart = existingCart.get();
            cart.setQuantity(cart.getQuantity() + 1);
            cartRepository.save(cart);
        } else {

            Cart cart = new Cart();
            cart.setUser(user);
            cart.setProduct(productRepository.findById(productId).orElse(null));
            cart.setQuantity(1);
            cartRepository.save(cart);
        }
    }

    @Override
    public void addQuantity(String username, UUID cartId, int quantity) {



        User user=userRepository.findByEmail(username);
        Cart cart1=cartRepository.findById(cartId).get();
        cart1.setQuantity(quantity);
        cart1.setUser(user);
        cartRepository.save(cart1);

    }

    @Override
    public List<Cart> getCartItems(String username) {
        return null;
    }

    @Override
    public double getTotalPrice(String username) {

        List<Cart> cartItems = cartRepository.findByUser_Email(username);

        return cartItems.stream()
                .mapToDouble(cartItem -> cartItem.getProduct().getPrice() * cartItem.getQuantity())
                .sum();



    }

    @Override
    public void removeCart(String username) {


    }



//    @Override
//    public Cart checkOut(String userName) {
//        User user =userRepository.findByEmail(userName);
//
//        if (user != null) {
//            List<Cart> carts = cartRepository.findAll();
//
//            Cart cartUser = null;
//            for (Cart cart : carts) {
//                if (cart.getUser().getId().equals(user.getId())) {
//                    cartUser = cart;
//                    System.out.println(cartUser);
//                }
//            }
//
//
//            return cartUser;
//        }
//
//
//        return null;
//    }



    @Override
    public Cart save(Cart cart) {
        return null;
    }
}
