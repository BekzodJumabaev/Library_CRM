package org.example.controller;

import org.example.model.AuthUser;
import org.example.model.dto.BorrowCreateDTO;
import org.example.model.dto.BorrowUpdateDTO;
import org.example.service.BookService;
import org.example.service.BorrowService;
import org.example.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/borrow")
public class BorrowController {

    private final BorrowService borrowService;
    private final UserService userService;
    private final BookService bookService;

    public BorrowController(BorrowService borrowService, UserService userService, BookService bookService) {
        this.borrowService = borrowService;
        this.userService = userService;
        this.bookService = bookService;
    }

    @GetMapping
    public ModelAndView page(Authentication authentication) {

        ModelAndView modelAndView = new ModelAndView("borrow");

        boolean b = authentication.getAuthorities()
                .stream()
                .anyMatch(authRole -> authRole
                        .getAuthority().contains("ADMIN"));


        if (b) {
            modelAndView.addObject("borrowList", borrowService.getAll());
        } else {
            AuthUser user = (AuthUser) authentication.getPrincipal();
            modelAndView.addObject("borrowList", borrowService.getByUserId(user.getId()));
        }

        modelAndView.addObject("users", userService.getUsersList());
        modelAndView.addObject("books", bookService.getAllBooks());
        return modelAndView;
    }

    @PostMapping("/add")
    public String create(@ModelAttribute BorrowCreateDTO borrowDTO){
        borrowService.create(borrowDTO);
        return "redirect:/borrow";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editpage(@PathVariable(name = "id") Integer id){
        ModelAndView modelAndView = new ModelAndView("borrow-edit");

        modelAndView.addObject("borrow", borrowService.getById(id));
        modelAndView.addObject("users", userService.getUsersList());
        modelAndView.addObject("books", bookService.getAllBooks());
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable(name = "id") Integer id, @ModelAttribute BorrowUpdateDTO borrowDTO, RedirectAttributes redirectAttributes){
        try {
            borrowService.update(id, borrowDTO);
            redirectAttributes.addFlashAttribute("message", "Kitob muvaffaqiyatli qaytarildi!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Xatolik yuz berdi!");
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        return "redirect:/borrow";
    }
}
