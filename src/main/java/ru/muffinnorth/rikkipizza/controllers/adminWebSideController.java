package ru.muffinnorth.rikkipizza.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.muffinnorth.rikkipizza.model.Item;
import ru.muffinnorth.rikkipizza.model.Section;
import ru.muffinnorth.rikkipizza.service.ClientService;
import ru.muffinnorth.rikkipizza.service.ItemService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class adminWebSideController {

    private ItemService itemService;
    private ClientService clientService;

    @Autowired
    public adminWebSideController(ItemService is){
        this.itemService = is;
    }

    @GetMapping()
    public String getIndex(Model model){
        List<Section> sections = itemService.getAllSection();
        sections.sort((o1, o2) -> {
            if(o1.getPriority() > o2.getPriority()){
                return -1;
            }else if(o1.getPriority() < o2.getPriority()){
                return 1;
            }else {
                return 0;
            }
        });
        model.addAttribute("sections", sections);
        List<Item> items = itemService.getAllItem();
        List<Item> itemsOut = new ArrayList<>();
        for (Item item: items) {
            if(item.getSection() == null){
                itemsOut.add(item);
            }
        }
        model.addAttribute("emptyItems", itemsOut);
        return "adminIndex";
    }

    @GetMapping("/section/new")
    public String getSectionAdd(Model model){
        Section section = new Section();
        model.addAttribute("section", section);
        return "sectionPanel";
    }

    @GetMapping("/section/delete/{id}")
    public String getSectionDelete(Model model, @PathVariable String id){
        model.addAttribute("section", itemService.getSectionById(Integer.parseInt(id)));
        return "sectionDelete";
    }

    @GetMapping("/section/edit/{id}")
    public String getSectionEdit(Model model, @PathVariable String id){
        model.addAttribute("section", itemService.getSectionById(Integer.parseInt(id)));
        return "sectionEdit";
    }

    @GetMapping("/item/delete/{id}")
    public String getItemDelete(Model model, @PathVariable String id){
        model.addAttribute("item", itemService.getItemById(Integer.parseInt(id)));
        return "itemDelete";
    }

    @GetMapping("/item/new")
    public String getItemAdd(@RequestParam(value = "id", required = false) String id, Model model){
        Item item = new Item();
        if(id != null){
            try {
                int iid = Integer.parseInt(id);
                item.setSection(itemService.getSectionById(iid));
                item.setSectionId(iid);
                System.out.println(iid);
            }catch (Exception e){ }
        }
        model.addAttribute("item", item);
        model.addAttribute("sections", itemService.getAllSection());
        return "itemPanel";
    }
}
