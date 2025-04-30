package com.atozdev.uptimemoniter.controller;

import com.atozdev.uptimemoniter.dtos.ResponseDto;
import com.atozdev.uptimemoniter.entity.Menu;
import com.atozdev.uptimemoniter.services.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/menus")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping()
    @CrossOrigin
    public ResponseEntity<ResponseDto> getAllMenus() {
        return new ResponseEntity<>(new ResponseDto(true, menuService.fetchAllMenus(), ""), HttpStatus.OK);
    }

    @PostMapping()
    @CrossOrigin
    public Menu createMenu(@RequestBody Menu menu) {
        return menuService.createMenu(menu);
    }

    @PutMapping("/menus/{id}")
    @CrossOrigin
    public Menu updateMenu(@RequestBody Menu menu, @PathVariable Long id) {
        return menuService.updateMenu(menu, id);
    }

    @DeleteMapping("/menus/menu/{id}")
    @CrossOrigin
    public ResponseEntity<ResponseDto> deleteAMenu(@PathVariable Long id) {
        menuService.deleteAMenu(id);
        return new ResponseEntity<>(new ResponseDto(true, "", ""), HttpStatus.ACCEPTED);
    }
}
