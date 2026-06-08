package org.example.controller;

import org.example.model.dto.UserCreateDto;
import org.example.model.dto.UserDTO;
import org.example.model.dto.UserUpdateDto;
import org.example.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping
    public ModelAndView page() {
        List<UserDTO> usersList = userService.getUsersList();
        ModelAndView mav = new ModelAndView();
        mav.addObject("usersList", usersList);
        mav.setViewName("user");
        return mav;
    }

    @GetMapping("/add")
    public String addUser() {
        return "user-add";
    }

    @PostMapping("/add")
    public String addPage(@ModelAttribute UserCreateDto dto) {
        userService.create(dto);
        return "redirect:/user";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editPage(@PathVariable(name = "id") Integer id) {
        UserDTO byId = userService.getById(id);
        ModelAndView mav = new ModelAndView();
        mav.addObject("user", byId);
        mav.setViewName("user-edit");
        return mav;
    }

    @PostMapping("/edit/{id}")
    public String updatePage(@PathVariable(name = "id") Integer id, @ModelAttribute UserUpdateDto dto) {
        userService.update(id, dto);
        return "redirect:/user";
    }

    @GetMapping("/delete/{id}")
    public String deletePage(@PathVariable(name = "id") Integer id) {
        userService.delete(id);
        return "redirect:/user";
    }
}
