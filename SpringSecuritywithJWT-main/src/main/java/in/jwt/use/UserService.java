package in.jwt.use;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String createUser(User a) throws IllegalArgumentException{
        if (userRepository.findByUsername(a.getUsername()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        a.setPassword(passwordEncoder.encode(a.getPassword()));  // Encode password
        userRepository.save(a);
        return "created successfully";
    }

    public String resetPassword(User a)throws UsernameNotFoundException {
        User user = userRepository.findByUsername(a.getUsername());
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        user.setPassword(passwordEncoder.encode(a.getPassword()));  // Encode new password
        userRepository.save(user);
        return "reseted succesfully";
    }
}
