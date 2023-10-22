package community.gdsc.wanted.controller;

import community.gdsc.wanted.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;
}
