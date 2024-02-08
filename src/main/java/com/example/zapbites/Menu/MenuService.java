package com.example.zapbites.Menu;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

    public Menu createmenu(Menu menu) {
        return menuRepository.save(menu);
    }

    public Menu updateMenu(Menu menu) {
        try {
            Menu updateMenu = menuRepository.save(menu);
            return updateMenu;
        } catch (DataAccessException e) {
            throw new EntityNotFoundException("Menu with id " + menu.getId() + " not found", e);
        }
    }

    public void deleteMenuById(Long id) {
        try {
            menuRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Menu with id " + id + " not found", e);
        }
    }
}
