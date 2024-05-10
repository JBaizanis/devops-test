package dit.hua.distributedSystems.project.demo.service;

import dit.hua.distributedSystems.project.demo.entity.Role;
import dit.hua.distributedSystems.project.demo.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public List<Role> getRoles(){
        return roleRepository.findAll();
    }

    @Transactional
    public Role getRole(Integer roleId) {
        return roleRepository.findById(roleId).get();
    }

    @Transactional
    public void saveRole(Role role){
        roleRepository.save(role);
    }
}
