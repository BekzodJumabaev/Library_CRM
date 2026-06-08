package org.example.controller;

import org.example.model.dto.AuthorCreateDTO;
import org.example.model.dto.AuthorDTO;
import org.example.model.dto.AuthorUpdateDTO;
import org.example.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @GetMapping
    public ModelAndView authorPage() {
       List<AuthorDTO> authorDTOList = service.getAll();
       ModelAndView modelAndView = new ModelAndView("author");
       modelAndView.addObject("authorDTOList", authorDTOList);
       return modelAndView;
    }
    @GetMapping("/add")
    public String addPage() {
        return "author-add";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute AuthorCreateDTO dto) {
        service.create(dto);
        return "redirect:/author";
    }
    @GetMapping("/edit/{id}")
    public ModelAndView aditPage(@PathVariable(name = "id") Integer id) {
        AuthorDTO author = service.getById(id);
        ModelAndView modelAndView = new ModelAndView("author-edit");
        modelAndView.addObject("author", author);
        return  modelAndView;
    }
    @PostMapping("/edit/{id}")
    public String update(@PathVariable( name = "id") Integer id, @ModelAttribute AuthorUpdateDTO dto) {
        service.update(id, dto);
        return "redirect:/author";
    }

    @GetMapping("/delete/{id}")
    public String deletePage(@PathVariable(name = "id") Integer id) {
        service.delete(id);
        return "redirect:/author";
    }




}
