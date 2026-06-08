package org.example.controller;

import org.example.model.dto.AuthorDTO;
import org.example.model.dto.BookCreateDTO;
import org.example.model.dto.BookDTO;
import org.example.model.dto.BookUpdateDTO;
import org.example.service.AuthorService;
import org.example.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping
    public ModelAndView page() {
        List<BookDTO> bookDTOList = bookService.getAllBooks();
        ModelAndView modelAndView = new ModelAndView("book");
        modelAndView.addObject("bookDTOList", bookDTOList);
        return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView addPage(){
        List<AuthorDTO> all = authorService.getAll();
        ModelAndView modelAndView = new ModelAndView("book-add");
        modelAndView.addObject("all", all);
        return modelAndView;
    }

    @PostMapping("/add")
    public String create(@ModelAttribute BookCreateDTO dto) {
        bookService.create(dto);
        return "redirect:/book";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editBook(@PathVariable(name = "id") Integer id) {
        BookDTO bookDTO = bookService.getById(id);
        ModelAndView modelAndView = new ModelAndView("book-edit");
        modelAndView.addObject("book", bookDTO);
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable(name = "id") Integer id, BookUpdateDTO dto) {
        bookService.update(id, dto);
        return "redirect:/book";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") Integer id) {
        bookService.delete(id);
        return "redirect:/book";
    }

}
