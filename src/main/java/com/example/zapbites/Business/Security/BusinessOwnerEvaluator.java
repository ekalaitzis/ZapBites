package com.example.zapbites.Business.Security;


import com.example.zapbites.Business.Business;
import com.example.zapbites.Business.BusinessService;
import com.example.zapbites.BusinessSchedule.BusinessSchedule;
import com.example.zapbites.BusinessSchedule.BusinessScheduleService;
import com.example.zapbites.Category.Category;
import com.example.zapbites.Category.CategoryService;
import com.example.zapbites.Ingredient.Ingredient;
import com.example.zapbites.Ingredient.IngredientService;
import com.example.zapbites.Menu.Menu;
import com.example.zapbites.Menu.MenuService;
import com.example.zapbites.Order.Order;
import com.example.zapbites.Order.OrderService;
import com.example.zapbites.Product.Product;
import com.example.zapbites.Product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class BusinessOwnerEvaluator {

    private BusinessService businessService;
    private BusinessScheduleService businessScheduleService;
    private CategoryService categoryService;
    private IngredientService ingredientService;
    private MenuService menuService;
    private OrderService orderService;
    private ProductService productService;


    public boolean checkForOwnerById(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Check if the authenticated user is the owner of the business with the given id
        Optional<Business> optionalBusiness = businessService.getBusinessById(id);

        return optionalBusiness
                .filter(business -> business.getEmail().equals(username))
                .isPresent();
    }

    public boolean checkForOwnerByBusiness(Business business) {
       return checkForOwnerById(business.getId());
    }

    public boolean checkForOwnerByBusinessScheduleId(Long id) {
        Optional<BusinessSchedule> optionalBusinessSchedule = businessScheduleService.getBusinessScheduleById(id);
        if (optionalBusinessSchedule.isPresent()) {
            Long businessId = optionalBusinessSchedule
                    .map(schedule -> schedule.getBusiness().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Business ID not found"));

            return checkForOwnerById(businessId);
        }
        return false;
    }

    public boolean checkForOwnerByBusinessSchedule(BusinessSchedule businessSchedule) {
        return checkForOwnerByBusinessScheduleId(businessSchedule.getId());
    }


    public boolean checkForOwnerByCategory(Category category) {
        return checkForOwnerByCategoryId(category.getId());
    }

    public boolean checkForOwnerByCategoryId(Long id) {
        Optional<Category> optionalCategory = categoryService.getCategoryById(id);
        if (optionalCategory.isPresent()) {
            Long categoryId = optionalCategory
                    .map(category -> category.getMenu().getBusiness().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Business ID not found"));

            return checkForOwnerById(categoryId);
        }
        return false;
    }


    public boolean checkForOwnerByIngredient(Ingredient ingredient) {
        return checkForOwnerByIngredientId(ingredient.getId());
    }

    public boolean checkForOwnerByIngredientId(Long id) {
        Optional<Ingredient> optionalIngredient = ingredientService.getIngredientById(id);
        if (optionalIngredient.isPresent()) {
            Long ingredientId = optionalIngredient
                    .map(ingredient -> ingredient.getProducts().getFirst().getCategory().getMenu().getBusiness().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Business ID not found"));

            return checkForOwnerById(ingredientId);
        }
        return false;
    }

    public boolean checkForOwnerByMenu(Menu menu) {
        return checkForOwnerByMenuId(menu.getId());
    }

    public boolean checkForOwnerByMenuId(Long id) {
        Optional<Menu> optionalMenu = menuService.getMenuById(id);
        if (optionalMenu.isPresent()) {
            Long menuID = optionalMenu
                    .map(menu -> menu.getBusiness().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Business ID not found"));

            return checkForOwnerById(menuID);
        }
        return false;
    }

    public boolean checkForOwnerByProductId(Long  id) {
        Optional<Product> optionalProduct= productService.getProductById(id);
        if (optionalProduct.isPresent()) {
            Long productId = optionalProduct
                    .map(product -> product.getCategory().getMenu().getBusiness().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Business ID not found"));

            return checkForOwnerById(productId);
        }
        return false;
    }

    public boolean checkForOwnerByProduct(Product product) {
        return checkForOwnerByProductId(product.getId());
    }

    public boolean checkForOwnerByOrders(Order order) {
        return checkForOwnerByOrdersId(order.getId());
    }

    public boolean checkForOwnerByOrdersId(Long id) {
        Optional<Order> optionalOrder= orderService.getOrderById(id);
        if (optionalOrder.isPresent()) {
            Long orderId = optionalOrder
                    .map(order -> order.getBusinessId().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Business ID not found"));

            return checkForOwnerById(orderId);
        }
        return false;
    }
}
