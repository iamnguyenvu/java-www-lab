package com.nguyenvu.homeworkspringdatajpa.controller;

import com.nguyenvu.homeworkspringdatajpa.dto.EmployeeDTO;
import com.nguyenvu.homeworkspringdatajpa.service.DepartmentService;
import com.nguyenvu.homeworkspringdatajpa.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeWebController {
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    @GetMapping
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employees";
    }

    @GetMapping("/{id}")
    public String viewEmployee(@PathVariable Long id, Model model) {
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "employee-detail";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("employee", new EmployeeDTO());
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "employee-form";
    }

    @PostMapping
    public String createEmployee(@ModelAttribute EmployeeDTO employeeDTO) {
        employeeService.createEmployee(employeeDTO);
        return "redirect:/employees";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "employee-form";
    }

    @PostMapping("/update/{id}")
    public String updateEmployee(@PathVariable Long id, @ModelAttribute EmployeeDTO employeeDTO) {
        employeeService.updateEmployee(id, employeeDTO);
        return "redirect:/employees";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employees";
    }

    @GetMapping("/search/name")
    public String searchByName(@RequestParam String name, Model model) {
        model.addAttribute("employees", employeeService.searchByName(name));
        return "employees";
    }

    @GetMapping("/search/salary")
    public String searchBySalary(
            @RequestParam(required = false) BigDecimal min,
            @RequestParam(required = false) BigDecimal max,
            Model model) {
        if (min != null && max != null) {
            model.addAttribute("employees", employeeService.findBySalaryRange(min, max));
        } else if (min != null) {
            model.addAttribute("employees", employeeService.findBySalaryGreaterThan(min));
        } else if (max != null) {
            model.addAttribute("employees", employeeService.findBySalaryLessThan(max));
        }
        return "employees";
    }

    @GetMapping("/search/age")
    public String searchByAge(
            @RequestParam Integer minAge,
            @RequestParam Integer maxAge,
            Model model) {
        model.addAttribute("employees", employeeService.findByAgeRange(minAge, maxAge));
        return "employees";
    }

    @GetMapping("/department/{deptId}")
    public String findByDepartment(@PathVariable Long deptId, Model model) {
        model.addAttribute("employees", employeeService.findByDepartmentId(deptId));
        return "employees";
    }
}
