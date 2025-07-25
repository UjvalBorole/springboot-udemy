package com.ecommerce.order.services;


import com.ecommerce.order.Dto.CartItemRequest;
import com.ecommerce.order.Repositories.CartItemRepository;
import com.ecommerce.order.entities.CartItem;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
//    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
//    private final UserRepository userRepository;
    public boolean addToCart(String userId, CartItemRequest request) {
//        Optional<Product> productOpt = productRepository.findById(request.getProductId());
//        if(productOpt.isEmpty())return false;
//        Product product = productOpt.get();
//        if(product.getStockQuantity() < request.getQuantity())return false;
//        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
//        if(userOpt.isEmpty())return false;
//        User user = userOpt.get();
        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(userId,request.getProductId());
        if(existingCartItem != null){
            //Update the quantity
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(existingCartItem);
        }else{
            //create the new cart item
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(request.getProductId());
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(cartItem);
        }
        return true;
    }

    public boolean deleteItemFromCart(String userId, String productId) {
//        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
//        Optional<Product> productOpt = productRepository.findById(productId);

//        if (userOpt.isPresent() && productOpt.isPresent()) {

        CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId,productId);
        if(cartItem != null ){
            cartItemRepository.delete(cartItem);
            return true;
        }
        return false;
    }

    public List<CartItem> getCart(String userId) {
         return cartItemRepository.findByUserId(userId);
    }

    public void clearOrder(String userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}
