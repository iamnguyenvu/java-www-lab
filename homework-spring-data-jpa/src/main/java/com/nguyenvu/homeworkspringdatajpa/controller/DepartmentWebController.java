package com.nguyenvu.homeworkspringdatajpa.controller;

import com.nguyenvu.homeworkspringdatajpa.dto.DepartmentDTO;
import com.nguyenvu.homeworkspringdatajpa.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentWebController {
    private final DepartmentService departmentService;

    @GetMapping
    public String listDepartments(Model model) {
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "departments";
    }

    @GetMapping("/{id}")
    public String viewDepartment(@PathVariable Long id, Model model) {
        DepartmentDTO department = departmentService.getDepartmentWithEmployees(id);
        model.addAttribute("department", department);
        return "department-detail";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("department", new DepartmentDTO());
        return "department-form";
    }

    @PostMapping
    public String createDepartment(@ModelAttribute DepartmentDTO departmentDTO) {
        departmentService.createDepartment(departmentDTO);
        return "redirect:/departments";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        DepartmentDTO department = departmentService.getDepartmentById(id);
        model.addAttribute("department", department);
        return "department-form";
    }

    @PostMapping("/update/{id}")
    public String updateDepartment(@PathVariable Long id, @ModelAttribute DepartmentDTO departmentDTO) {
        departmentService.updateDepartment(id, departmentDTO);
        return "redirect:/departments";
    }

    @GetMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return "redirect:/departments";
    }

    @GetMapping("/search")
    public String searchDepartments(@RequestParam String name, Model model) {
        model.addAttribute("departments", departmentService.searchByName(name));
        return "departments";
    }
}
