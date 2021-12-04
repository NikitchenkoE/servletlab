package com.controller;

import com.entity.Product;
import com.entity.dto.AuthorizedUserDto;
import com.service.CartService;
import com.service.MainPageService;
import com.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class MainPageController {
    private final ProductService productService;
    private final MainPageService mainPageService;
    private final CartService cartService;

    @Autowired
    public MainPageController(ProductService productService, MainPageService mainPageService, CartService cartService) {
        this.productService = productService;
        this.mainPageService = mainPageService;
        this.cartService = cartService;
    }

    @GetMapping(path = {"/", "/products"})
    protected String getMainPageWithAllProducts(@RequestAttribute("isLogged") boolean logged,
                                                @RequestAttribute("user") Optional<AuthorizedUserDto> authUser,
                                                Model model) {
        model.addAttribute("logged", String.valueOf(logged));
        authUser.ifPresent(authorizedUserDto -> model.addAttribute("user", authorizedUserDto));

        var products = productService.getAll();
        var productDtoList = mainPageService.mapToProductDtoList(products);
        model.addAttribute("products", productDtoList);

        return "mainPage";
    }

    @GetMapping("/productsById")
    protected String getMainPageById(@RequestAttribute Optional<Boolean> logged,
                                     @RequestParam("productId") String productId,
                                     Model model) {
        long id = Long.parseLong(productId);
        model.addAttribute("products", mainPageService.getDataById(id));
        model.addAttribute("logged", String.valueOf(logged.orElse(true)));
        return "mainPage";
    }

    @GetMapping("/productsByDescription")
    protected String getMainPageByDescription(@RequestAttribute Optional<Boolean> logged,
                                              @RequestParam("productDescription") String productDescription,
                                              Model model) {
        model.addAttribute("logged", String.valueOf(logged.orElse(true)));
        model.addAttribute("products", mainPageService.getDataByDescription(productDescription));
        return "mainPage";
    }

    @PostMapping("/cart")
    protected String addProductToCart(@RequestParam("productId") String id,
                               @RequestAttribute Optional<AuthorizedUserDto> authUser) {
        AuthorizedUserDto user = authUser.orElse(AuthorizedUserDto.builder()
                .id(1L)
                .username("user")
                .role("USER")
                .build());
        cartService.addProductToCart(user, id);

        return "redirect:/";
    }

    @GetMapping(path = "/products/add")
    protected String getAddProductPage() {
        return "addProductPage";
    }

    @PostMapping("/products/add")
    protected String addNewProduct(@ModelAttribute Product product) {
        productService.save(product);
        return "redirect:/";
    }

    @PostMapping("/products/delete")
    protected String deleteProduct(@RequestParam("idToDelete") Long id) {
        productService.delete(id);
        cartService.deleteAllProductWithSameIdFromCart(id);
        return "redirect:/";
    }

    @GetMapping("/products/update")
    protected String getUpdateProductPage(@RequestParam("idToUpdate") Long id,
                                          Model model) {
        Product productToBeUpdated = productService.get(id).orElseThrow(() -> new RuntimeException("Impossible to update without id"));
        model.addAttribute("product", productToBeUpdated);

        return "updateProductPage";

    }

    @PostMapping("/products/update")
    protected String updateProduct(@RequestParam("productId") Long id, @ModelAttribute Product product) {
        Product productToBeUpdated = productService.get(id).orElseThrow(() -> new RuntimeException("Impossible to update without id"));
        productService.update(productToBeUpdated, product);
        return "redirect:/";
    }
}


