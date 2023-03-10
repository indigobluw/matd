package com.thereselindquist.matd.user;

import com.thereselindquist.matd.security.AppPasswordConfig;
import com.thereselindquist.matd.user.authorities.UserRoles;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class UserController {
    private final UserRepository userModelRepository;
    private final AppPasswordConfig appPasswordConfig;

    @Autowired
    public UserController(UserRepository userModelRepository, AppPasswordConfig appPasswordConfig) {
        this.userModelRepository = userModelRepository;
        this.appPasswordConfig = appPasswordConfig;
    }

    @GetMapping("/register")
    public String displayRegisterUser(UserModel userModel) {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid UserModel userModel, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "register";
        }

        String role = String.valueOf(userModel.getAuthorities().iterator().next());

        switch (role) {
            case "ADMIN" -> userModel.setAuthorities(UserRoles.ADMIN.getGrantedAuthorities());
            case "USER" -> userModel.setAuthorities(UserRoles.USER.getGrantedAuthorities());
        }

        userModel.setPassword(appPasswordConfig.bCryptPasswordEncoder().encode(userModel.getPassword()));
        userModel.setAccountNonExpired(true);
        userModel.setAccountNonLocked(true);
        userModel.setCredentialsNonExpired(true);
        userModel.setEnabled(true);
        userModelRepository.save(userModel);
        return "index";
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        List<UserModel> users = userModelRepository.findAll();
        model.addAttribute("users", users);
        return "users";
    }
}