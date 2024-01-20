package com.example.zapbites.Menu;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public ResponseEntity<List<Menu>> getAllMenu() {
        List<Menu> menus = menuService.getAllMenus();
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Menu> getMenuById(@PathVariable Long id) {
        Optional<Menu> optionalMenu = menuService.getMenuById(id);
        return optionalMenu.map(menu -> new ResponseEntity<>(menu, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @PostMapping
    public ResponseEntity<Menu> createMenu(@RequestBody Menu menu) {
        Menu createdMenu = menuService.createmenu(menu);
        return new ResponseEntity<>(createdMenu, HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Menu> updateMenu(@RequestBody Menu menu) {
        try {
            Menu updatedMenu = menuService.updateMenu(menu);
            return new ResponseEntity<>(updatedMenu, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuById(@PathVariable Long id) {
        menuService.deleteMenuById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
