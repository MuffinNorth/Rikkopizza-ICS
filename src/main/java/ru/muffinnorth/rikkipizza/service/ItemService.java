package ru.muffinnorth.rikkipizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.muffinnorth.rikkipizza.model.Item;
import ru.muffinnorth.rikkipizza.model.Section;
import ru.muffinnorth.rikkipizza.repository.ItemRepo;
import ru.muffinnorth.rikkipizza.repository.SectionRepo;

import java.util.List;

@Service
public class ItemService {

    private SectionRepo sectionRepository;
    private ItemRepo itemRepository;

    @Autowired
    public ItemService(SectionRepo sr, ItemRepo ir){
        this.sectionRepository = sr;
        this.itemRepository = ir;
    }

    public void createSection(Section section){
        sectionRepository.saveAndFlush(section);
    }

    public void createSection(String title, int priority){
        Section section = new Section();
        section.setTitle(title);
        section.setPriority(priority);
        sectionRepository.saveAndFlush(section);
    }

    public boolean removeSection(String title){
        List<Section> sectionList = sectionRepository.findAllByTitle(title);
        if(sectionList.size() >= 1 || sectionList.size() == 0){
            return false;
        }
        Section section = sectionList.get(0);
        sectionRepository.deleteById(section.getId());
        return true;
    }

    public Section getSectionById(int id){
        return sectionRepository.findById(id).orElse(null);
    }

    public Item getItemById(int id){
        return itemRepository.findById(id).orElse(null);
    }

    public void createItem(Item item){
        itemRepository.saveAndFlush(item);
    }

    public void updateSection(Section section){
        sectionRepository.saveAndFlush(section);
    }

    public boolean deleteSection(Section section){
        if(getSectionById(section.getId()) != null){
            List<Item> items = section.getItems();
            items.forEach(item -> {
                item.setSection(null);
            });
            sectionRepository.delete(section);
            return true;
        }else{
            return false;
        }
    }

    public boolean deleteItem(Item item){
        if(getItemById(item.getId()) != null){
            itemRepository.delete(item);
            return true;
        }else {
            return false;
        }
    }

    public List<Item> getAllItem(){
        return itemRepository.findAll();
    }

    public List<Section> getAllSection(){
        return sectionRepository.findAll();
    }

}
