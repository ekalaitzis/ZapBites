package com.example.zapbites.Menu;

import com.example.zapbites.Menu.Exceptions.MenuNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/menu")
@Validated
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    @PreAuthorize("hasRole('BUSINESS')") //this is not secured properly as it is not going to be used in production
    public ResponseEntity<List<Menu>> getAllMenu() {
        List<Menu> menus = menuService.getAllMenus();
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@businessOwnerEvaluator.checkForOwnerByMenuId(#id)")
    public ResponseEntity<Menu> getMenuById(@PathVariable Long id) {
        Optional<Menu> optionalMenu = menuService.getMenuById(id);
        return optionalMenu.map(menu -> new ResponseEntity<>(menu, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createMenu(@Valid @RequestBody Menu menu) {
            Menu createdMenu = menuService.createMenu(menu);
            return new ResponseEntity<>(createdMenu, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@businessOwnerEvaluator.checkForOwnerByMenu(#menu)")
    public ResponseEntity<Menu> updateMenu(@RequestBody Menu menu) {
        try {
            Menu updatedMenu = menuService.updateMenu(menu);
            return new ResponseEntity<>(updatedMenu, HttpStatus.OK);
        } catch (MenuNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@businessOwnerEvaluator.checkForOwnerByMenuId(#id)")
    public ResponseEntity<Void> deleteMenuById(@PathVariable Long id) {
        menuService.deleteMenuById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
