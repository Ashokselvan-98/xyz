package in.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.jwt.use.User;
import in.jwt.use.UserService;

@RestController
public class MyController {

    @Autowired
    private UserService userService;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("msg")
    public String getMsg() {
        return "Hi this is Ashok";
    }

    @GetMapping("msg1")
    public String findProduct() {
        return "comment your favorite product";
    }

    @GetMapping("msg2")
    public String addValue() {
        return "enter the amount";
    }

    @PostMapping("/new-user")
    public String createUser(@RequestBody User user) throws IllegalArgumentException {
        return userService.createUser(user);
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody User user) throws UsernameNotFoundException {
        return userService.resetPassword(user);
    }

    @PostMapping("/authenticate")
    public LoginResponse getResponse(@RequestBody LoginRequest loginRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect User", e);
        }
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(loginRequest.getUsername());
        String jwtToken = jwtUtil.generateToken(userDetails);
        return new LoginResponse(jwtToken);
    }
}
