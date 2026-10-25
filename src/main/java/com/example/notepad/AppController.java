package com.example.notepad;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Controller layer: only handles pages and calls the service.
@Controller
public class AppController {

    private final UserService userService;

    public AppController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    // ---------- Login ----------
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session, Model model) {

        User user = userService.login(username, password);
        if (user == null) {
            model.addAttribute("error", "Wrong username or password");
            return "login";
        }
        session.setAttribute("username", user.getUsername());
        return "redirect:/notepad";
    }

    // ---------- Create user ----------
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String fullName,
                           @RequestParam String email,
                           @RequestParam String username,
                           @RequestParam String password,
                           Model model) {

        boolean created = userService.register(fullName, email, username, password);
        if (!created) {
            model.addAttribute("error", "Username already taken, choose another");
            return "register";
        }
        return "redirect:/login?registered";
    }

    // ---------- Notepad ----------
    @GetMapping("/notepad")
    public String notepad(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", userService.getUser(username));
        return "notepad";
    }

    @PostMapping("/notepad/save")
    public String save(@RequestParam String notes,
                       @RequestParam String action,
                       HttpSession session) {

        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        userService.saveNotes(username, notes);

        if (action.equals("exit")) {
            session.invalidate();
            return "redirect:/login?saved";
        }
        return "redirect:/notepad?saved";
    }

    // ---------- Logout ----------
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
