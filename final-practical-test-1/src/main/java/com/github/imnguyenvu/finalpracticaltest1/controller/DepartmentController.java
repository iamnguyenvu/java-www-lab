package com.github.imnguyenvu.finalpracticaltest1.controller;

import com.github.imnguyenvu.finalpracticaltest1.dto.DepartmentForm;
import com.github.imnguyenvu.finalpracticaltest1.entity.Department;
import com.github.imnguyenvu.finalpracticaltest1.service.DepartmentService;
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

@Controller
@RequiredArgsConstructor
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping
    public String list(
            @RequestParam(value = "q", required = false) String keyword,
            @PageableDefault(size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable,
            Model model) {
        Page<Department> departments = departmentService.search(keyword, pageable);
        model.addAttribute("departments", departments);
        model.addAttribute("keyword", keyword);
        return "departments/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Department department = departmentService.findById(id);
        long employeeCount = departmentService.getEmployeeCount(id);
        model.addAttribute("department", department);
        model.addAttribute("employeeCount", employeeCount);
        return "departments/detail";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("form", new DepartmentForm());
        model.addAttribute("isEdit", false);
        return "departments/form";
    }

    @PostMapping("/new")
    public String create(
            @Valid @ModelAttribute("form") DepartmentForm form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "departments/form";
        }

        try {
            Department department = departmentService.create(form);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm phòng ban thành công");
            return "redirect:/departments/" + department.getId();
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("isEdit", false);
            return "departments/form";
        }
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Department department = departmentService.findById(id);
        DepartmentForm form = DepartmentForm.builder()
                .id(department.getId())
                .code(department.getCode())
                .name(department.getName())
                .description(department.getDescription())
                .build();
        model.addAttribute("form", form);
        model.addAttribute("isEdit", true);
        return "departments/form";
    }

    @PostMapping("/{id}/edit")
    public String edit(
            @PathVariable Long id,
            @Valid @ModelAttribute("form") DepartmentForm form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", true);
            form.setId(id);
            return "departments/form";
        }

        try {
            departmentService.update(id, form);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật phòng ban thành công");
            return "redirect:/departments/" + id;
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("isEdit", true);
            form.setId(id);
            return "departments/form";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            departmentService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa phòng ban thành công");
            return "redirect:/departments";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/departments/" + id;
        }
    }
}
