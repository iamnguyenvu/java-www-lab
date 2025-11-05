package com.github.imnguyenvu.finalpracticaltest1.controller;

import com.github.imnguyenvu.finalpracticaltest1.dto.EmployeeForm;
import com.github.imnguyenvu.finalpracticaltest1.entity.Department;
import com.github.imnguyenvu.finalpracticaltest1.entity.Employee;
import com.github.imnguyenvu.finalpracticaltest1.service.DepartmentService;
import com.github.imnguyenvu.finalpracticaltest1.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    @GetMapping
    public String list(
            @RequestParam(value = "q", required = false) String keyword,
            @RequestParam(value = "departmentId", required = false) Long departmentId,
            @PageableDefault(size = 10, sort = "fullName", direction = Sort.Direction.ASC) Pageable pageable,
            Model model) {
        Page<Employee> employees = employeeService.search(keyword, departmentId, pageable);
        List<Department> departments = departmentService.findAll();
        
        model.addAttribute("employees", employees);
        model.addAttribute("departments", departments);
        model.addAttribute("keyword", keyword);
        model.addAttribute("departmentId", departmentId);
        return "employees/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Employee employee = employeeService.findById(id);
        model.addAttribute("employee", employee);
        return "employees/detail";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        List<Department> departments = departmentService.findAll();
        model.addAttribute("form", new EmployeeForm());
        model.addAttribute("departments", departments);
        model.addAttribute("isEdit", false);
        return "employees/form";
    }

    @PostMapping("/new")
    public String create(
            @Valid @ModelAttribute("form") EmployeeForm form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            List<Department> departments = departmentService.findAll();
            model.addAttribute("departments", departments);
            model.addAttribute("isEdit", false);
            return "employees/form";
        }

        try {
            Employee employee = employeeService.create(form);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm nhân viên thành công");
            return "redirect:/employees/" + employee.getId();
        } catch (Exception e) {
            List<Department> departments = departmentService.findAll();
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("departments", departments);
            model.addAttribute("isEdit", false);
            return "employees/form";
        }
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Employee employee = employeeService.findById(id);
        List<Department> departments = departmentService.findAll();
        
        EmployeeForm form = EmployeeForm.builder()
                .id(employee.getId())
                .fullName(employee.getFullName())
                .email(employee.getEmail())
                .salary(employee.getSalary())
                .hireDate(employee.getHireDate())
                .departmentId(employee.getDepartment().getId())
                .active(employee.getActive())
                .build();
        
        model.addAttribute("form", form);
        model.addAttribute("departments", departments);
        model.addAttribute("isEdit", true);
        return "employees/form";
    }

    @PostMapping("/{id}/edit")
    public String edit(
            @PathVariable Long id,
            @Valid @ModelAttribute("form") EmployeeForm form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            List<Department> departments = departmentService.findAll();
            model.addAttribute("departments", departments);
            model.addAttribute("isEdit", true);
            form.setId(id);
            return "employees/form";
        }

        try {
            employeeService.update(id, form);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật nhân viên thành công");
            return "redirect:/employees/" + id;
        } catch (Exception e) {
            List<Department> departments = departmentService.findAll();
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("departments", departments);
            model.addAttribute("isEdit", true);
            form.setId(id);
            return "employees/form";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            employeeService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa nhân viên thành công");
            return "redirect:/employees";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/employees/" + id;
        }
    }
}
