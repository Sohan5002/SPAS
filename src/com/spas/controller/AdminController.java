package com.spas.controller;

import com.spas.model.User;
import com.spas.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    
    @Autowired
    private UserService userService;

    @PostMapping("/create-student")
    public ResponseEntity<?> createStudent(@RequestBody User user) {
        if (userService.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        user.setRole("STUDENT");
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/students")
    public ResponseEntity<?> getAllStudents() {
        // TODO: Implement get all students logic
        return ResponseEntity.ok().build();
    }
}
