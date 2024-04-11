package com.example.zapbites.Menu;

import java.util.List;
import java.util.Optional;

public interface MenuService {
    List<Menu> getAllMenus();

    Optional<Menu> getMenuById(Long id);

    Menu createMenu(Menu menu);

    Menu updateMenu(Menu updatedMenu);

    void deleteMenuById(Long id);
}
