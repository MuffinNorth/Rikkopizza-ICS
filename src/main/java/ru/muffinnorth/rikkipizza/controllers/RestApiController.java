package ru.muffinnorth.rikkipizza.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.muffinnorth.rikkipizza.model.Item;
import ru.muffinnorth.rikkipizza.model.Section;
import ru.muffinnorth.rikkipizza.service.ItemService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/api/v1")
public class RestApiController {

    private ItemService itemService;

    @Autowired
    public RestApiController(ItemService is){
        this.itemService = is;
    }

    //SECTION API v1

    @GetMapping("/section/all")
    public List<Section> getAllSection(){
        return itemService.getAllSection();
    }

    @PostMapping("/section/new")
    public String newSection(@ModelAttribute("section") Section section) throws IOException {
        section.setIcon(section.getMultipartFile().getBytes());
        section.setMultipartFile(null);
        itemService.createSection(section);
        return "redirect:/admin";
    }

    @PostMapping("/section/{id}/update")
    public Section updateSection(@ModelAttribute("section") Section section, @PathVariable String id) throws IOException {
        int intId = Integer.parseInt(id);
        Section updatedSection = itemService.getSectionById(intId);
        section.setIcon(section.getMultipartFile().getBytes());
        section.setMultipartFile(null);

        if(updatedSection != null){
            updatedSection.setTitle(section.getTitle());
            updatedSection.setPriority(section.getPriority());
            updatedSection.setIcon(section.getIcon());
            return updatedSection;
        }
        return new Section();
    }

    @PostMapping("/section/{id}/delete")
    public String deleteSection(@PathVariable String id){
        int intId = Integer.parseInt(id);
        Section deletedSection = itemService.getSectionById(intId);
        if(deletedSection != null)
            itemService.deleteSection(deletedSection);
        return "redirect:/admin";
    }

    @PostMapping("/section/{id}/edit")
    public String editSection(@PathVariable String id, @ModelAttribute("section") Section section) throws IOException {
        int intId = Integer.parseInt(id);
        Section editedSection = itemService.getSectionById(intId);
        if(!section.getMultipartFile().isEmpty()){
            editedSection.setIcon(section.getMultipartFile().getBytes());
        }
        editedSection.setTitle(section.getTitle());
        editedSection.setPriority(section.getPriority());
        itemService.updateSection(editedSection);
        return "redirect:/admin";
    }

    @ResponseBody
    @GetMapping("/section/{id}")
    public Section getSection(@PathVariable String id, Model model){
        int intId = Integer.parseInt(id);
        Section section = itemService.getSectionById(intId);
        model.addAttribute("section", section);
        return section;
    }



    @Transactional
    @GetMapping("/section/{sectionid}/icon/")
    public ResponseEntity<byte[]> downloadIcon(@PathVariable String sectionid){
        byte[] im = itemService.getSectionById(Integer.parseInt(sectionid)).getIcon();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(im);
    }

    @PostMapping("/item/{itemid}/delete")
    public String deleteItem(@PathVariable String itemid){
        int intId = Integer.parseInt(itemid);
        Item deletedItem = itemService.getItemById(intId);
        if(deletedItem == null){
            return "redirect:/admin?error=on_delete";
        }
        itemService.deleteItem(deletedItem);
        return "redirect:/admin";
    }

    @Transactional
    @GetMapping("/item/{itemId}/image")
    public ResponseEntity<byte[]> downloadImage(@PathVariable String itemId){
        Item item = itemService.getItemById(Integer.parseInt(itemId));
        byte[] im = item.getImage();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(im);
    }

    @PostMapping("/item/new")
    public String newItem(@ModelAttribute("item") Item item) throws IOException {
        item.setImage(item.getMultipartFile().getBytes());
        item.setMultipartFile(null);
        if(item.getSectionId() == -1){
            item.setSection(null);
        }else {
            item.connectSection(itemService.getSectionById(item.getSectionId()));
        }
        itemService.createItem(item);
        return "redirect:/admin";
    }

}
