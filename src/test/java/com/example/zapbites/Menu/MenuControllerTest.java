package com.example.zapbites.Menu;

import com.example.zapbites.Menu.Exceptions.MenuNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MenuControllerTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private MenuService menuService;

    @InjectMocks
    private MenuController menuController;

    private MockMvc mockMvc;

    private Menu testMenu;

    @BeforeEach
    void init() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(menuController).build();
        testMenu = new Menu(1L, "Test Menu", null);
    }

    @Test
    @DisplayName("Should return a list of all menus")
    public void getAllMenusShouldReturnListOfMenus() throws Exception {
        List<Menu> menuList = new ArrayList<>();
        menuList.add(new Menu(1L, "Test Menu 1", null));
        menuList.add(new Menu(2L, "Test Menu 2", null));
        when(menuService.getAllMenus()).thenReturn(menuList);

        mockMvc.perform(get("/menu"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
        verify(menuService, times(1)).getAllMenus();
    }

    @Test
    @DisplayName("Should return a menu when a valid id is given")
    void getMenuByIdWithValidIdShouldReturnMenu() throws Exception {
        when(menuService.getMenuById(testMenu.getId())).thenReturn(Optional.of(testMenu));

        mockMvc.perform(get("/menu/{id}", testMenu.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testMenu.getId()));
        verify(menuService, times(1)).getMenuById(testMenu.getId());
    }

    @Test
    @DisplayName("Should return not found when an invalid id is given")
    void getMenuByIdWithInvalidIdShouldReturnNotFound() throws Exception {
        Long menuId = 99L;
        when(menuService.getMenuById(menuId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/menu/{id}", menuId))
                .andExpect(status().isNoContent());
        verify(menuService, times(1)).getMenuById(menuId);
    }

    @Test
    @DisplayName("Should create a menu when valid menu attributes are given")
    void createMenuWithValidAttributesShouldReturnCreatedMenu() throws Exception {
        when(menuService.createMenu(testMenu)).thenReturn(testMenu);

        mockMvc.perform(post("/menu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testMenu)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(testMenu.getId()));
        verify(menuService, times(1)).createMenu(testMenu);
    }

    @Test
    @DisplayName("Should return an updated menu when valid updated attributes are given")
    void updateMenuWithValidAttributesShouldReturnUpdatedMenu() throws Exception {
        Menu updatedMenu = new Menu(1L, "Updated Menu", null);

        when(menuService.updateMenu(any(Menu.class))).thenReturn(updatedMenu);

        mockMvc.perform(put("/menu/{id}", updatedMenu.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedMenu)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updatedMenu.getName()));
        verify(menuService, times(1)).updateMenu(updatedMenu);
    }

    @Test
    @DisplayName("Should return not found when trying to update a non-existing menu")
    void updateMenuWithNonExistingIdShouldReturnNotFound() throws Exception {
        Long menuId = 1L;
        Menu menu = new Menu(menuId, "Test Menu", null);

        when(menuService.updateMenu(menu)).thenThrow(new MenuNotFoundException(""));

        mockMvc.perform(put("/menu/{id}", menuId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(menu)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return no content when deleting a menu")
    void deleteMenuByIdWithValidIdShouldReturnNoContent() throws Exception {
        Long menuId = 1L;

        mockMvc.perform(delete("/menu/{id}", menuId))
                .andExpect(status().isNoContent());
        verify(menuService, times(1)).deleteMenuById(menuId);
    }
}
