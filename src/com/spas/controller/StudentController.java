package com.spas.controller;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    
    @GetMapping("/performance")
    public ResponseEntity<?> getMyPerformance() {
        // TODO: Implement get student performance logic
        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getMyProfile() {
        // TODO: Implement get student profile logic
        return ResponseEntity.ok().build();
    }
}
