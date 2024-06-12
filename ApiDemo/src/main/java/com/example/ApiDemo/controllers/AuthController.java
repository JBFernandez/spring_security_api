package com.example.ApiDemo.controllers;

import com.example.ApiDemo.models.Users;
import com.example.ApiDemo.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
    @RequestMapping("/api/v1/auth")
public class AuthController {

    public AuthenticationService authenticationService;

//    @Autowired
//    private PasswordEncoder passwordEncoder;



    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity register( @RequestBody Users request ) {
        Map<String, String> response = new HashMap<>();
        try{
            String token = authenticationService.register(request);
            response.put( "status", "ok" );
            response.put( "token", token );
            return ResponseEntity.status(200).body( response );

        } catch (Exception e) {
            response.put( "status", "el usuario con el email: " + request.getEmail() + " ya existe!!" );
            response.put( "mensaje", e.getMessage() );
            return ResponseEntity.status(200).body( response );

        }

    }

    @PostMapping("/login")
    public ResponseEntity login( @RequestBody Users request ) {
        Map<String, String> response = new HashMap<>();
        String token = authenticationService.authenticate(request);


        if ( token.startsWith( "org.springframework.security" ) ) {
            response.put("status", "error");
            response.put("mensaje", token);
            return ResponseEntity.status(400).body(response);
        }


            response.put( "status", "ok" );
            response.put( "token", token );
            return ResponseEntity.status(200).body( response );



    }

    @GetMapping("/hello")
    public ResponseEntity<Map<String, String>> getBody( ) {

        Map<String, String > response = new HashMap<>();

        response.put( "hola", "Nuevo" );

        System.out.println( "Como estas!!" );

        return ResponseEntity.status(200).body( response );

    }



//    @GetMapping
//    public ResponseEntity getUserById(@RequestParam Long id) throws Exception {
//        Users user = userService.getUser(id);
//        return ResponseEntity.status(200).body(user);
//    }

//    @GetMapping
//    public String holaAuthenticated() {
//        return "Hola Amigooooos!!!";
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity addUser(@RequestBody Users user)  {

//        user.setPassword( passwordEncoder.encode(user.getPassword()) );

//        try {
//            HashMap<String, String> response =  new HashMap<>();
//            String token = authenticationService.saveUser(user);
//            response.put( "token", token );
//            response.put( "status", "Usuario guardado con exito" );
//            return  ResponseEntity.status(200).body( response );
//
//        } catch (Exception e) {
//            e.toString();
//            return ResponseEntity.status(400).body( e.getMessage() );
//        }
//    }
//
//    @PostMapping("/authenticate")
//    public ResponseEntity login(@RequestBody LoginData data) {
//
//        Map<String, String> response = new HashMap<>();
//
//        try {
//            String token = authenticationService.loginUser( data );
//            response.put( "message", "Bienvenido: " + data.getEmail() );
//            response.put( "token", token );
//
//            return ResponseEntity.status(200).body( response );
//
//        } catch ( Exception e ) {
//            e.toString();
//            response.put( "message", e.getMessage() );
//            response.put( "token", null );
//            return ResponseEntity.status(400).body( response );
//        }
//
//    }

}

