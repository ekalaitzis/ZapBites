package com.example.zapbites.Menu;

import com.example.zapbites.Menu.Exceptions.DuplicateMenuException;
import com.example.zapbites.Menu.Exceptions.MenuNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MenuService {

    private final MenuRepository menuRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    public Optional<Menu> getMenuById(Long id) {
        return menuRepository.findById(id);
    }

    public Menu createMenu(Menu menu) {
        String name = menu.getName();
        Optional<Menu> existingMenu = menuRepository.findByName();

        if (existingMenu.isPresent()) {
            throw new DuplicateMenuException("Menu: " + name + " already exists.");
        }
        return menuRepository.save(menu);
    }

    public Menu updateMenu(Menu updatedMenu) {
        List<Menu> allMenus = getAllMenus();

        if (allMenus.stream().anyMatch(m -> m.getId().equals(updatedMenu.getId()))) {
            return menuRepository.save(updatedMenu);
        } else {
            throw new MenuNotFoundException("Menu:  " + updatedMenu.getName() + " doesn't exist.");
        }
    }

    public void deleteMenuById(Long id) {
        try {
            menuRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new MenuNotFoundException("Menu with id " + id + " not found", e);
        }
    }
}
