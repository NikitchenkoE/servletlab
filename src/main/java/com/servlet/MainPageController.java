package com.servlet;

import com.entity.Product;
import com.service.CartService;
import com.service.MainPageService;
import com.service.ProductService;
import com.service.util.PageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
    @ResponseBody
    protected byte[] getMainPage(@RequestAttribute Optional<Boolean> logged,
                                 @RequestAttribute Optional<String> productDescription,
                                 @RequestAttribute Optional<String> productId) {
        Map<String, Object> model = new HashMap<>();

        String description = productDescription.orElse(null);
        String id = productId.orElse(null);
        model.put("products", new ArrayList<Product>());
        model.put("logged", String.valueOf(logged.orElse(true)));

        if (description == null && id == null) {
            var products = productService.getAll();
            var productDtoList = mainPageService.mapToProductDtoList(products);
            model.put("products", productDtoList);

        } else if (id != null) {
            if (!id.isEmpty()) {
                long prodid = Long.parseLong(id);
                model.put("products", mainPageService.getDataById(prodid));
            }

        } else if (description != null && !description.isEmpty()) {
            model.put("products", mainPageService.getDataByDescription(description));

        }
        return PageGenerator.init().writePage(model, "mainPage.ftlh");
    }

    @GetMapping(path = "/products/add")
    @ResponseBody
    protected byte[] getAddProductPage() {
        return PageGenerator.init().writePage("addProductPage.ftlh");
    }

    @PostMapping(path = "/products/add")
    protected String addNewProduct(@ModelAttribute Product product) {
        productService.save(product);
        return "redirect:/";
    }

    @PostMapping(path = "/products/delete")
    protected String deleteProduct(@RequestParam("idToDelete") Long id) {
        productService.delete(id);
        cartService.deleteAllProductWithSameIdFromCart(id);
        return "redirect:/";
    }

    @GetMapping("/products/update")
    @ResponseBody
    protected byte[] getUpdateProductPage(@RequestParam("idToUpdate") Long id) {
        Map<String, Object> model = new HashMap<>();
        Product productToBeUpdated = productService.get(id).orElseThrow(() -> new RuntimeException("Impossible to update without id"));
        model.put("product", productToBeUpdated);

        return PageGenerator.init().writePage(model, "updateProductPage.ftlh");

    }

    @PostMapping("/products/update")
    protected String updateProduct(@RequestParam("productId") Long id, @ModelAttribute Product product) throws IOException {
        Product productToBeUpdated = productService.get(id).orElseThrow(() -> new RuntimeException("Impossible to update without id"));
        productService.update(productToBeUpdated, product);
        return "redirect:/";
    }
}


