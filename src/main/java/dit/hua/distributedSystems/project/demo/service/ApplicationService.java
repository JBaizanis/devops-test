package dit.hua.distributedSystems.project.demo.service;

import dit.hua.distributedSystems.project.demo.entity.Application;
import dit.hua.distributedSystems.project.demo.entity.MUser;
import dit.hua.distributedSystems.project.demo.repository.ApplicationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserDetailsServiceImpl userService;
    @Transactional
    public List<Application> getApplications(){
        return applicationRepository.findAll();
    }

    public List<Application> getFarmerApplications(Integer userId){
        MUser user = userService.getUser(userId);
        return user.getApplications();
    }

    public MUser getApplicationsFarmer(Integer applicationId){
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found with ID: " + applicationId));

        MUser user = application.getUser();

        return user;
    }
    @Transactional
    public Application getApplication(Integer applicationId) {
        return applicationRepository.findById(applicationId).get();
    }

    @Transactional
    public Application saveApplication(Application application){
         return applicationRepository.save(application);
    }

    @Transactional
    public void deleteApplication(Integer applicationId) {
        applicationRepository.deleteById(applicationId);
    }
}
