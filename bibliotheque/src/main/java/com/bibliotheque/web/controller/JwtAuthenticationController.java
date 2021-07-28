package com.bibliotheque.web.controller;

import com.bibliotheque.configuration.JwtTokenUtil;
import com.bibliotheque.dto.UserDTO;
import com.bibliotheque.model.JwtRequest;
import com.bibliotheque.model.JwtResponse;
import com.bibliotheque.configuration.JwtUserDetailsService;
import com.bibliotheque.model.User;
import com.bibliotheque.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * RestController JwtAuthenticationController
 */
@RestController
public class JwtAuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    ApplicationEventPublisher eventPublisher;


    /**
     * Authentifie l'user
     * @param authenticationRequest
     * @return jwt
     * @throws Exception
     */
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception
    {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

     
        return ResponseEntity.ok(new JwtResponse(token));
    }


    /**
     * Verfie l'authentification
     * @param username
     * @param password
     * @throws Exception
     */
    private void authenticate(String username, String password) throws Exception {
        System.out.println("\n \n "+ username+ " \n "  + password );
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    /**
     * Inscription de l'user
     * @param user
     * @param request
     * @param errors
     * @return user
     * @throws Exception
     */
    @PostMapping("/register")
    public ResponseEntity<User> saveUser(@RequestBody UserDTO user,
                                      HttpServletRequest request,
                                      Errors errors) throws Exception
    {

        User registered = userService.save(user);

        return new ResponseEntity<User>(registered, HttpStatus.ACCEPTED);
    }


    /**
     * Recupere un username par le jwt
     * @param jwt
     * @return string
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<?> getUserNameByToken(@RequestBody Map<String,String> jwt)
    {

        JwtResponse response = new JwtResponse(jwt.get("jwt"));
        String username = null ;
        try {
            username = jwtTokenUtil.getUsernameFromToken(response.getJwt());
            System.out.println("\n from biblio -> username : "+ username  +" \n " );

        } catch (Exception e )
        {
            System.out.println("\n error \n " );
        }

        return new ResponseEntity<String>(username,HttpStatus.ACCEPTED);
    }


    /**
     * Recupere un user par le jwt
     * @param jwt
     * @return user
     */
    @RequestMapping(value = "/token/{jwt}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserByToken( @PathVariable String jwt)
    {

        String username= jwtTokenUtil.getUsernameFromToken(jwt);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        User user = userService.findByUsername(userDetails.getUsername());

        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }



}
