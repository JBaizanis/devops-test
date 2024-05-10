package dit.hua.distributedSystems.project.demo.service;

import dit.hua.distributedSystems.project.demo.entity.MUser;
import dit.hua.distributedSystems.project.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Transactional
    public List<MUser> getUsers(){
        return userRepository.findAll();
    }

    @Transactional
    public MUser getUser(Integer userId) {
        return userRepository.findById(userId).get();
    }

    @Transactional
    public void saveUser(MUser user) {
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }

    @Transactional
    public boolean findByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional
    public boolean findByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


}
