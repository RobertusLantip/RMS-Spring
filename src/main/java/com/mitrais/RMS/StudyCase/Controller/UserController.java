package com.mitrais.RMS.StudyCase.Controller;

import com.mitrais.RMS.StudyCase.Model.User;
import com.mitrais.RMS.StudyCase.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("login")
    public String Login(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "Login";
    }

    @RequestMapping(value = "/routing_login", method = RequestMethod.POST)
    public String Routing(@ModelAttribute User user, Model model) {

        model.addAttribute("users",userService.findAllUsers());
        return "Users";
    }

    @GetMapping("user")
    public String Users(Model model){
        model.addAttribute("users",userService.findAllUsers());
        return "Users";
    }

    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute User user, Model model) {
        if(userService.createUser(user)){
            model.addAttribute("users",userService.findAllUsers());
            return "Users";
        }else{
            model.addAttribute("error","Field still empty !");
            return "Create";
        }
    }

    @GetMapping("create")
    public String Create(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "Create";
    }

    @RequestMapping(value = "/delete_user", method = RequestMethod.GET)
    public String deleteUser(@RequestParam(name="personId")int userId, Model model) {
        userService.deleteUser(userId);
        model.addAttribute("users",userService.findAllUsers());
        return "Users";
    }

    @RequestMapping(value = "/find_user", method = RequestMethod.GET)
    public String findUserUpdate(@RequestParam(name="personId")int userId, Model model) {
        User user = new User();
        model.addAttribute("user",userService.findUserById(userId));
        return "Update";
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute User user, Model model) {
        if(userService.updateUser(user)){
            model.addAttribute("users",userService.findAllUsers());
            return "Users";
        }else{
            model.addAttribute("error","Field still empty !");
            return "Update";
        }
    }

}
