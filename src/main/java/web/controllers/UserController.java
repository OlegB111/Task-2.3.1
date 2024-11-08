package web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import web.models.User;
import web.services.UserService;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/users")

public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String usersPage(Model model) {
        model.addAttribute("listUser", userService.index());
        return "users";
    }

    @GetMapping("/show")
    public String show(@RequestParam(value = "id") int id, Model model) {
        model.addAttribute("user", userService.show(id));
        return "show";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "new";

        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam(value = "id") int id) {
        model.addAttribute("user", userService.show(id));
        return "edit";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute("user") User user, BindingResult bindingResult,
                         @RequestParam(value = "id") int id) {
        if (bindingResult.hasErrors())
            return "edit";

        userService.update(id, user);
        return "redirect:/users";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam(value = "id") int id) {
        userService.delete(id);
        return "redirect:/users";
    }

}