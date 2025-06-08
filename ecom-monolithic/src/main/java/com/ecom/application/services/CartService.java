package com.ecom.application.services;

import com.ecom.application.Dto.CartItemRequest;
import com.ecom.application.Repository.CartItemRepository;
import com.ecom.application.Repository.ProductRepository;
import com.ecom.application.Repository.UserRepository;
import com.ecom.application.entities.CartItem;
import com.ecom.application.entities.Product;
import com.ecom.application.entities.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.emitter.ScalarAnalysis;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    public boolean addToCart(String userId, CartItemRequest request) {
        Optional<Product> productOpt = productRepository.findById(request.getProductId());
        if(productOpt.isEmpty())return false;
        Product product = productOpt.get();
        if(product.getStockQuantity() < request.getQuantity())return false;
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if(userOpt.isEmpty())return false;
        User user = userOpt.get();
        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user,product);
        if(existingCartItem != null){
            //Update the quantity
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepository.save(existingCartItem);
        }else{
            //create the new cart item
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(cartItem);
        }
        return true;
    }

    public boolean deleteItemFromCart(String userId, Long productId) {
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        Optional<Product> productOpt = productRepository.findById(productId);

        if (userOpt.isPresent() && productOpt.isPresent()) {
            cartItemRepository.deleteByUserAndProduct(userOpt.get(), productOpt.get());
            return true;
        }
        return false;
    }

    public List<CartItem> getCart(String userId) {
         return userRepository.findById(Long.valueOf(userId))
                 .map(cartItemRepository::findByUser)
                 .orElseGet(List::of);
    }

    public void clearOrder(String userId) {
        userRepository.findById(Long.valueOf(userId)).ifPresent(user ->
                cartItemRepository.deleteByUser(user));
    }
}
