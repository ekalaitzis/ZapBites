package com.example.zapbites.Menu;

import com.example.zapbites.Business.Business;
import com.example.zapbites.Menu.Exceptions.DuplicateMenuException;
import com.example.zapbites.Menu.Exceptions.MenuNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MenuServiceTest {


    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private MenuServiceImpl menuService;

    private Business business1, business2;

    private Menu menu1, menu2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        business1 = new Business();
        business1.setId(1L);
        business1.setEmail("business1@example.com");
        business1.setPassword("password1");

        business2 = new Business();
        business2.setId(2L);
        business2.setEmail("business2@example.com");
        business2.setPassword("password2");

        menu1 = new Menu();
        menu1.setId(1L);
        menu1.setName("Menu 1");
        menu1.setBusiness(business1);

        menu2 = new Menu();
        menu2.setId(2L);
        menu2.setName("Menu 2");
        menu2.setBusiness(business2);
    }

    @Test
    void getAllMenus() {
        List<Menu> menuList = Arrays.asList(menu1, menu2);
        when(menuRepository.findAll()).thenReturn(menuList);

        List<Menu> result = menuService.getAllMenus();

        assertEquals(menuList, result);
        verify(menuRepository, times(1)).findAll();
    }

    @Test
    void getMenuById() {
        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu1));

        Optional<Menu> result = menuService.getMenuById(1L);

        assertTrue(result.isPresent());
        assertEquals(menu1, result.get());
        verify(menuRepository, times(1)).findById(1L);
    }

    @Test
    void createMenu() {
        when(menuRepository.findByName(menu1.getName())).thenReturn(Optional.empty());
        when(menuRepository.save(any(Menu.class))).thenReturn(menu1);

        Menu result = menuService.createMenu(menu1);

        assertNotNull(result);
        assertEquals(menu1, result);
        verify(menuRepository, times(1)).findByName(menu1.getName());
        verify(menuRepository, times(1)).save(any(Menu.class));
    }

    @Test
    void createMenu_DuplicateException() {
        when(menuRepository.findByName(menu1.getName())).thenReturn(Optional.of(menu1));

        assertThrows(DuplicateMenuException.class, () -> menuService.createMenu(menu1));
        verify(menuRepository, times(1)).findByName(menu1.getName());
        verify(menuRepository, never()).save(any());
    }

    @Test
    void updateMenu() {
        when(menuRepository.findAll()).thenReturn(Arrays.asList(menu1, menu2));
        when(menuRepository.save(any(Menu.class))).thenReturn(menu1);

        Menu result = menuService.updateMenu(menu1);

        assertNotNull(result);
        assertEquals(menu1, result);
        verify(menuRepository, times(1)).findAll();
        verify(menuRepository, times(1)).save(any(Menu.class));
    }

    @Test
    void updateMenu_MenuNotFoundException() {
        Menu nonExistingMenu = new Menu();
        nonExistingMenu.setId(3L);
        when(menuRepository.findAll()).thenReturn(Arrays.asList(menu1, menu2));

        assertThrows(MenuNotFoundException.class, () -> menuService.updateMenu(nonExistingMenu));
        verify(menuRepository, times(1)).findAll();
        verify(menuRepository, never()).save(any());
    }

    @Test
    void deleteMenuById() {
        menuService.deleteMenuById(1L);
        verify(menuRepository, times(1)).deleteById(1L);
    }
}