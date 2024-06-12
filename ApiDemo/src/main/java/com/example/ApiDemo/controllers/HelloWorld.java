package com.example.ApiDemo.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hello")
public class HelloWorld {



    //user Acces
    @GetMapping("/demo")
    public ResponseEntity holaUser() {
        return ResponseEntity.status(200).body( "Bienvenidos de protegido!!!" );
    }

    //user admin
    @GetMapping("/admin")
    public ResponseEntity holaAdmin() {
        return ResponseEntity.status(200).body( "Bienvenidos solo admin!!!" );
    }






//    @PostMapping
//    public void prueba( @RequestParam( "userName" ) String userName ) {
//
//    }


}
