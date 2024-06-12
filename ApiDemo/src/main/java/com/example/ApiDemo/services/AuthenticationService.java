package com.example.ApiDemo.services;

import com.example.ApiDemo.models.Users;
import com.example.ApiDemo.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
//    private final JwtService jwtService;
//    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(UsersRepository usersRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public String register(Users request ) throws Exception {

        Optional<Users> dbUser = usersRepository.findByEmail( request.getEmail() );
        String token;

        if ( dbUser.isPresent() ) {
            throw new Exception( "El usuario con el mail: " + request.getEmail() + " ya existe" );
        } else {

            Users user = new Users();
            user.setName(request.getName());
            // a lo mejor tengo que agregar un campo de username a Users y a mi Base de datos
//        user.getUsername(request.getEmail());
            user.setPassword( passwordEncoder.encode(request.getPassword()) );
            user.setEmail( request.getEmail() );

            user.setRole( request.getRole() );
//        user.setRole( user.getRole() );

            usersRepository.save(user);

            token = jwtService.generateToken( user );
        }
        return token;
    }

    public String authenticate( Users request ) {

        String token;

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            Users user = usersRepository.findByEmail(request.getUsername()).orElseThrow( () -> new UsernameNotFoundException("No hay usuario"));

            return token = jwtService.generateToken( user );


        } catch (Exception e ) {
              return e.toString();
        }

    }

//    public Users getUser(Long id) throws Exception {
//        Optional<Users> user = usersRepository.findById(id);
//        if ( user.isPresent() ) {
//            return user.get();
//        } else {
//            throw new Exception("El usuario con el id: " + id + " no existe");
//        }
//    }
//
//    public String saveUser(Users user ) throws Exception {

//        var usuario = User.builder().username(user.getEmail()).password(user.getPassword())
//                .roles(String.valueOf(Role.USER))
//                .build();

//        Optional<Users> result = usersRepository.findByEmail( user.getEmail() );
//
//        if (result.isPresent() ) {
//            throw new Exception( "Ya existe un usuario con el email: " + user.getEmail()  );
//        } else {
//            usersRepository.save( user );
//            String token = jwtService.generateToken( usuario );
//            System.out.println("token = " + token);
//            return "token";
//        }
//
//    }
//
//
//    public String loginUser(LoginData data) throws Exception {
//        Optional<Users> user = usersRepository.findByEmail( data.getEmail() );

//        authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(
//           data.getEmail(),
//                data.getPassword()
//        ));

//        Users user = usersRepository.findByEmail( data.getEmail() ).orElseThrow( () -> new IllegalStateException() );
//        String token = jwtService.generateToken( user );
//        return token;

//        if ( user.isEmpty() ) {
//            throw new Exception( "El usuario o la contraseña son incorrectos --> caso email" );
//        }
//
//        if ( !Encryptor.verifyHash(data.getPassword(), user.get().getPassword()) ) {
//            throw new Exception( "El usuario o la contraseña son incorrectos --> caso contraseña" );
//        }
//
//        return "token" ;
//
//    }

//    private String generateToken(String email) {
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.HOUR, 10); //Token valid time
//        String secret = "this-secret-is-not-very-secret-99";
//
//        return Jwts.builder().setSubject(email).claim("role", "user")
//                .setIssuedAt( new Date() ).setExpiration( calendar.getTime() )
//                .signWith( SignatureAlgorithm.HS256, secret ).compact();
//
//    }

}
