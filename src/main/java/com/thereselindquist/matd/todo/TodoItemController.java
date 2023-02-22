package com.thereselindquist.matd.todo;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TodoItemController {

    private final TodoItemRepository repository;

    @Autowired
    public TodoItemController(TodoItemRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/createitem")
    public String index(Model model) {
        model.addAttribute("item", new TodoItemFormData());
        model.addAttribute("totalNumberOfItems", repository.count());
        return "createitem";
    }

    @PostMapping("/createitem")
    public String addNewToDoItem(@Valid @ModelAttribute("item") TodoItemFormData formData) {
        repository.save(new TodoItemModel(formData.getTitle(), false, false));
        return "redirect:/createitem";
    }
}
