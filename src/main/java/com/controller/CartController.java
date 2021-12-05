package com.controller;

import com.entity.dto.AuthorizedUserDto;
import com.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    protected String getCartPage(@RequestAttribute("user") AuthorizedUserDto authUser,
                                 Model model) {
        model.addAttribute("products", cartService.findAllProductInCartDto(authUser));
        model.addAttribute("total", cartService.sumAllProducts(authUser));

        return "cart";
    }

    @PostMapping("/cart/deleteFromCart")
    protected String deleteProductFromCart(@RequestParam("cartItemId") String id) {
        cartService.deleteOneProductFromTheCart(Long.parseLong(id));
        return "redirect:/cart";
    }
}

