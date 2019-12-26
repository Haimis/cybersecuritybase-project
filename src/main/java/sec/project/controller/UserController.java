package sec.project.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import sec.project.domain.User;
import sec.project.repository.UserRepository;

@Controller
@SessionAttributes("user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/register";
    }

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String loadForm() {
        return "signin";
    }
    
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String loadRegisterForm() {
        return "register";
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public String submitSigninForm(Model model, @RequestParam String name, @RequestParam String password) {
        List<User> list = userRepository.findAll();
        User u = null;
        for (User r : list) {
            if (r.getName().equalsIgnoreCase(name) && r.getPassword().equalsIgnoreCase(password)) {
                u = r;
            }
        }
        
        if (u != null) {
            model.addAttribute("user", u);
            return "redirect:/notes";
        }
        return "register";
    }
    
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String submitRegisterForm(@RequestParam String name, @RequestParam String password) {
        List<User> list = userRepository.findAll();
        for (User u : list) {
            if (u.getName().equals(name)) {
                return "redirect:/register";
            }
        }
        userRepository.save(new User(name, password));
        return "signin";
    }

}
