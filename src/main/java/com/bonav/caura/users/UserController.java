package com.bonav.caura.users;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Slf4j
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
    @RequestMapping (value = "/register" ,method=RequestMethod.POST)
    public  String processRegistrationForm (User registrationForm,Model model,HttpServletRequest request) {
        try{
            registrationForm=userService.saveUser(registrationForm);
            if(registrationForm==null){
                model.addAttribute("message","Error registering user");
                return "register";
            }
            request.getSession().setAttribute("id", registrationForm.id);
            return "redirect:home";
        }catch(Exception e){
            model.addAttribute("message","Error registering user");
            return "register";
        }

        // Process the registration form data

    }


    @PostMapping("/login")
    public String login(String username, String password, Model model,HttpServletRequest request) {
        log.info("Username: " + username + ", Password: " + password);
        User user = userService.login(username, password);
        if (user == null) {
            model.addAttribute("message", "Incorrect Username and Password");
            return "login";
        } else {
            //log.info("User",user);
            request.getSession().setAttribute("id", user.id);
            return "redirect:home";
        }

        }

        @GetMapping("/home")
        public String home (HttpServletRequest request, Model model){
            Long id = (Long) request.getSession().getAttribute("id");
            model.addAttribute("user", userService.getUserById(id));
            return "home";
        }
        @GetMapping("/register")
        public String register (HttpServletRequest request, Model model){
            //long id= (Long)request.getSession() .getAttribute("id");
            //model.addAttribute("user",userService.getUserById(id));
            return "register";
        }


    }
