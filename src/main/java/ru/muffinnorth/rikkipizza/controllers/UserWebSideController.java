package ru.muffinnorth.rikkipizza.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.muffinnorth.rikkipizza.model.Client;
import ru.muffinnorth.rikkipizza.model.Section;
import ru.muffinnorth.rikkipizza.service.ClientService;
import ru.muffinnorth.rikkipizza.service.ItemService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/userSide")
public class UserWebSideController {

    private ItemService itemService;
    private ClientService clientService;

    @Autowired
    public UserWebSideController(ItemService is){
        this.itemService = is;
    }

    @Transactional
    @GetMapping()
    public String getUserIndex(Model model, Principal principal){
        List<Section> sectionList = itemService.getAllSection();
        sectionList.sort((o1, o2) -> {
            if(o1.getPriority() > o2.getPriority()){
                return -1;
            }else if(o1.getPriority() < o2.getPriority()){
                return 1;
            }else {
                return 0;
            }
        });
        model.addAttribute("sections", sectionList);
        return "UserSideIndex";
    }

    @GetMapping("/test")
    public String getTest(Model model){
        List<Section> sections = itemService.getAllSection();
        System.out.println(sections.get(0).getId());
        model.addAttribute("sectionList", sections);
        return "test";
    }
}
