package com.mitrais.RMS.StudyCase.Controller;

import com.mitrais.RMS.StudyCase.Model.User;
import com.mitrais.RMS.StudyCase.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("")
public class UserController {

    @Autowired
    UserService userService;

    private User currentUser;

    @GetMapping("login")
    public String Login(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "Login";
    }

    @GetMapping("admin/user")
    public String Users(Model model){
        model.addAttribute("users",userService.findAllUsers());
        return "Users";
    }

    @RequestMapping(value = "admin/saveUser", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute User user, Model model) {
        if (userService.createUser(user)) {
            model.addAttribute("users", userService.findAllUsers());
            return "Users";
        } else {
            model.addAttribute("error", "Field still empty !");
            return "Create";
        }
    }

    @GetMapping("admin")
    public String Admin(Model model){
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = ((UserDetails)user).getUsername();
        String password =((UserDetails)user).getPassword();
        currentUser = userService.getUser(name,password);
        model.addAttribute("user",currentUser);
        return "Admin";
    }

    @GetMapping("member")
    public String Member(Model model){
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = ((UserDetails)user).getUsername();
        String password =((UserDetails)user).getPassword();
        currentUser = userService.getUser(name,password);
        model.addAttribute("user",currentUser);
        return "Member";
    }

    @RequestMapping(value = "error", method = RequestMethod.GET)
    public String errorPage(Model model, HttpServletRequest httpRequest){
        String errorMsg = "";
        int httpErrorCode = getErrorCode(httpRequest);

        switch (httpErrorCode) {
            case 400: {
                errorMsg = "Http Error Code: 400. Bad Request";
                break;
            }
            case 401: {
                errorMsg = "Http Error Code: 401. Unauthorized";
                break;
            }
            case 404: {
                errorMsg = "Http Error Code: 404. Resource not found";
                break;
            }
            case 500: {
                errorMsg = "Http Error Code: 500. Internal Server Error";
                break;
            }
            case 403: {
                errorMsg = "Http Error Code: 403. Forbidden";
                break;
            }
        }
        model.addAttribute("error",errorMsg);
        return "Error";
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
                .getAttribute("javax.servlet.error.status_code");
    }

    @GetMapping("member/userdetails")
    public String userDetails(Model model){
        model.addAttribute("user",currentUser);
        return "UserDetails";
    }

    @GetMapping("member/changepass")
    public String changePass(Model model){
        model.addAttribute("user",currentUser);
        return "ChangePassword";
    }

    @GetMapping("admin/create")
    public String Create(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "Create";
    }

    @RequestMapping(value = "admin/delete_user", method = RequestMethod.GET)
    public String deleteUser(@RequestParam(name="personId")int userId, Model model) {
        userService.deleteUser(userId);
        model.addAttribute("users",userService.findAllUsers());
        return "Users";
    }

    @RequestMapping(value = "admin/find_user", method = RequestMethod.GET)
    public String findUserUpdate(@RequestParam(name="personId")int userId, Model model) {
        User user = new User();
        model.addAttribute("user",userService.findUserById(userId));
        return "Update";
    }

    @RequestMapping(value = "admin/updateUser", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute User user, Model model) {
        if(userService.updateUser(user)){
            model.addAttribute("users",userService.findAllUsers());
            return "Users";
        }else{
            model.addAttribute("error","Field still empty !");
            return "Update";
        }
    }

    @RequestMapping(value = "member/updatePass", method = RequestMethod.POST)
    public String updatePass(@ModelAttribute User user, Model model) {
        if(userService.changePassword(user)){
            model.addAttribute("user",currentUser);
            return "Member";
        }else{
            model.addAttribute("error","Field still empty !");
            return "ChangePassword";
        }
    }
}