package com.example.zapbites.Menu;

import com.example.zapbites.Menu.Exceptions.DuplicateMenuException;
import com.example.zapbites.Menu.Exceptions.MenuNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MenuServiceTest {

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private MenuService menuService;

    @Test
    void getAllMenus_ShouldReturnListOfMenus() {
        List<Menu> menuList = new ArrayList<>();
        menuList.add(new Menu());
        menuList.add(new Menu());
        when(menuRepository.findAll()).thenReturn(menuList);

        List<Menu> result = menuService.getAllMenus();

        assertEquals(2, result.size());
    }

    @Test
    void getMenuById_WithValidId_ShouldReturnMenu() {
        Long menuId = 1L;
        Menu menu = new Menu();
        when(menuRepository.findById(menuId)).thenReturn(Optional.of(menu));

        Optional<Menu> result = menuService.getMenuById(menuId);

        assertTrue(result.isPresent());
        assertEquals(menu, result.get());
    }

    @Test
    void getMenuById_WithInvalidId_ShouldReturnEmptyOptional() {
        Long menuId = 1L;
        when(menuRepository.findById(menuId)).thenReturn(Optional.empty());

        Optional<Menu> result = menuService.getMenuById(menuId);

        assertTrue(result.isEmpty());
    }

    @Test
    void createMenu_WithValidMenu_ShouldReturnCreatedMenu() {
        Menu menu = new Menu();
        when(menuRepository.findById(any())).thenReturn(Optional.empty());
        when(menuRepository.save(menu)).thenReturn(menu);

        Menu result = menuService.createMenu(menu);

        assertEquals(menu, result);
    }

    @Test
    void createMenu_WithDuplicateId_ShouldThrowDuplicateMenuException() {
        Menu menu = new Menu();
        when(menuRepository.findById(any())).thenReturn(Optional.of(new Menu()));

        assertThrows(DuplicateMenuException.class, () -> menuService.createMenu(menu));
    }

    @Test
    void updateMenu_WithValidMenu_ShouldReturnUpdatedMenu() {
        Menu updatedMenu = new Menu();
        updatedMenu.setId(1L);
        // Mocking getAllMenus() to return a list containing the updated menu
        when(menuService.getAllMenus()).thenReturn(Collections.singletonList(updatedMenu));
        when(menuRepository.save(updatedMenu)).thenReturn(updatedMenu);

        Menu result = menuService.updateMenu(updatedMenu);

        assertEquals(updatedMenu, result);
    }

    @Test
    void updateMenu_WithNonExistingId_ShouldThrowMenuNotFoundException() {
        Menu updatedMenu = new Menu();
        // Mocking getAllMenus() to return an empty list, simulating that the menu does not exist
        when(menuService.getAllMenus()).thenReturn(Collections.emptyList());

        assertThrows(MenuNotFoundException.class, () -> menuService.updateMenu(updatedMenu));
    }

    @Test
    void deleteMenuById_WithValidId_ShouldDeleteMenu() {
        Long menuId = 1L;

        menuService.deleteMenuById(menuId);

        verify(menuRepository, times(1)).deleteById(menuId);
    }

    @Test
    void deleteMenuById_WithNonExistingId_ShouldThrowMenuNotFoundException() {
        Long menuId = 1L;
        doThrow(new EmptyResultDataAccessException(1)).when(menuRepository).deleteById(menuId);

        assertThrows(MenuNotFoundException.class, () -> menuService.deleteMenuById(menuId));
    }
}
