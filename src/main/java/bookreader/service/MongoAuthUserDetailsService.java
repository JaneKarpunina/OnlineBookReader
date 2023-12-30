package bookreader.service;

import bookreader.model.Role;
import bookreader.model.UserRole;
import bookreader.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import java.util.HashSet;
import java.util.Set;

@Service
public class MongoAuthUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MongoAuthUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        bookreader.model.User user = userRepository.findByUsername(userName);

//         UserRole userRole = new UserRole();
//         Role r = new Role();
//         r.setName("client");
//         userRole.setRole(r);
//
//         Set<UserRole> userRoles = new HashSet<>();
//         userRoles.add(userRole);
//
//         user.setUserRoles(userRoles);
//         user.setPassword(new BCryptPasswordEncoder().encode("password"));
//         userRepository.save(user);
//
//         user = userRepository.findByUsername(userName);

         Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        user.getAuthorities()
                .forEach(role -> {
                    grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()
                            .getName()));
                });

        return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }

}

