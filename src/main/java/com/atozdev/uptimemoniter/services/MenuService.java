package com.atozdev.uptimemoniter.services;

import com.atozdev.uptimemoniter.entity.Menu;
import com.atozdev.uptimemoniter.repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    public MenuService(MenuRepository menuRepository){
        this.menuRepository =menuRepository;
    }

    public List<Menu> fetchAllMenus(){
       return menuRepository.findAll();
    }

    public Menu createMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    public Menu updateMenu(Menu menu, Long id) {
       Optional menuOptional = menuRepository.findById(id);
       if (menuOptional.isPresent())
       {
           Menu oldMenu = (Menu) menuOptional.get();
           menu.setId(oldMenu.getId());
           menuRepository.save(menu);
       }
       return menu;
    }

    public void deleteAMenu(Long id) {
        menuRepository.deleteById(id);
    }
}
