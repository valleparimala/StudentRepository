package com.studentmgmt.controller;

import com.studentmgmt.model.Student;
import com.studentmgmt.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StudentController {
    private final StudentService service;
    public StudentController(StudentService service) { this.service = service; }

    @GetMapping({"/", "/students"})
    public String home(Model model) {
        model.addAttribute("listStudents", service.listAll());
        return "index";
    }

    @GetMapping("/students/new")
    public String newStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "form";
    }

    @PostMapping("/students/save")
    public String saveStudent(@ModelAttribute Student student) {
        service.save(student);
        return "redirect:/students";
    }

    @GetMapping("/students/edit/{id}")
    public ModelAndView editStudent(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("form");
        service.get(id).ifPresent(s -> mav.addObject("student", s));
        return mav;
    }

    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/students";
    }
}
