package dit.hua.distributedSystems.project.demo.controller;

import dit.hua.distributedSystems.project.demo.config.JwtUtils;
import dit.hua.distributedSystems.project.demo.entity.MUser;
import dit.hua.distributedSystems.project.demo.entity.Role;
import dit.hua.distributedSystems.project.demo.payload.request.LoginRequest;
import dit.hua.distributedSystems.project.demo.payload.response.JwtResponse;
import dit.hua.distributedSystems.project.demo.payload.response.MessageResponse;
import dit.hua.distributedSystems.project.demo.repository.RoleRepository;
import dit.hua.distributedSystems.project.demo.repository.UserRepository;
import dit.hua.distributedSystems.project.demo.service.UserDetailsImpl;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import dit.hua.distributedSystems.project.demo.payload.request.*;
import dit.hua.distributedSystems.project.demo.payload.response.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static Long id;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    public static Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        System.out.println("authentication");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        System.out.println("authentication: " + authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("post authentication");
        String jwt = jwtUtils.generateJwtToken(authentication);
        System.out.println("jw: " + jwt);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        id = userDetails.getId();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));


    }

}
