package com.example.springintermediate.auth;

import com.example.springintermediate.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductController {
    @GetMapping("/getUser")
    public String getUserFromReq(HttpServletRequest req) {
        User user = (User) req.getAttribute("user");
        System.out.println(user.getUsername()+" "+user.getRole());
        return user.getUsername()+" "+user.getRole();
    }
}
