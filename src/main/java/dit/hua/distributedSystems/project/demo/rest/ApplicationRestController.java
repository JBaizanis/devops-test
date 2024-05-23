package dit.hua.distributedSystems.project.demo.rest;


import dit.hua.distributedSystems.project.demo.controller.AuthController;
import dit.hua.distributedSystems.project.demo.entity.Application;
import dit.hua.distributedSystems.project.demo.entity.MUser;
import dit.hua.distributedSystems.project.demo.service.ApplicationService;
import dit.hua.distributedSystems.project.demo.service.UserDetailsImpl;
import dit.hua.distributedSystems.project.demo.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/application")
@CrossOrigin(origins = "http://51.120.247.169")
public class ApplicationRestController {




    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private UserDetailsServiceImpl userService;

    //Η συγκεκριμένη μέθοδος επιστρέφει μια λίστα με τις αιτήσεις στον επιθεωρητή που είναι προς έγκριση 'Pending', ώστε να τις ελέγξει.
    @GetMapping("")
    public List <Application> showApplications(){
        List <Application> applications = applicationService.getApplications();

        List<Application> pendingApplications = applications.stream()
                .filter(application -> "Pending".equals(application.getDecision()) || ("Approved".equals(application.getDecision()) && application.getCompensationAmount() == 0))
                .collect(Collectors.toList());

        return pendingApplications;

    }

    //Η συγκεκριμένη μέθοδος χρησιμοποιείται για την προβολή από τον αγρότη των αιτήσεων του, που βρίσκονται είτε σε κατάσταση 'Pending' είτε έχουν ελεγχθεί και είναι 'Approved'
    //ή Denied.
    @GetMapping("/user/{user_id}")
    public List <Application> showFarmerApplications(@PathVariable Integer user_id){
        Long loginid = AuthController.getId();
        if (loginid.intValue() == user_id) {
            return applicationService.getFarmerApplications(user_id);
        } else {
            List<Application> EmptyList = new ArrayList<>();
            return EmptyList;
        }
    }

    @PostMapping("/new/{user_id}")
    public String saveApplication(@RequestBody Application application, @PathVariable Integer user_id) {

        String ds=application.getDecision();
        double ca=application.getCompensationAmount();
        String top=application.getCategoryOfProduction();
        String top1=top.toLowerCase();
        Long loginid = AuthController.getId();
        if (loginid.intValue() == user_id) {
            if (top1.equals("fruits") || top1.equals("vegetables")) { //Έλεγχος ότι το πεδίο categoryOfProduction είναι συμπληρωμένο με τις τιμές 'fruits' ή 'vegetables'.
                if (ds != null || ca != 0) {
                    return "Fields decision and compensationAmount should not be filled!";
                }
                if (application.getDamagedProductionAmount() > application.getProductionAmount()) { //Το ποσό της καταστραμμένης παραγωγής δεν θα πρέπει να υπερβαίνει το ποσό
                    // της κανονικής παραγωγής.
                    return "The damaged production should not surpass the production amount!";
                }
                application.setCompensationAmount(0.0); //Προσθήκη νέας αίτησης στην βάση από τον αγρότη. Το πεδίο decision τίθενται 'Pending' αφού δεν έχει εγκριθεί η απορριφθεί από τον
                //επιθεωρητή η αίτηση. Αντίστοιχα και το πεδίο του ποσού της αποζημίωσης έχει τεθεί στο 0 για τον ίδιο λόγο.
                application.setDecision("Pending");
                MUser user = userService.getUser(user_id);
                application.setUser(user);
                applicationService.saveApplication(application);
                return "Application has been successfully saved!";
            } else {
                return "Field category of production should be filled with values fruits or vegetables!";
            }
        } else {
            return "You can't create an application and assign it to another user apart from yourself";
        }
    }

    //Μέθοδος για διαγραφή μιας αίτησης από τον αγρότη.
    @DeleteMapping("{applicationId}")
    public String deleteApplication(@PathVariable Integer applicationId) {
        Application application = applicationService.getApplication(applicationId);
        Long loginid = AuthController.getId();
        if (loginid.intValue() == applicationService.getApplicationsFarmer(applicationId).getId()) {
            if ("Approved".equals(application.getDecision()) || "Denied".equals(application.getDecision())) {
                return "You can not delete an already checked application!";
            }
            applicationService.deleteApplication(applicationId);
            return "Application has been successfully deleted!";
        } else {
            return "You can't delete an application and assign it to another user apart from yourself";
        }
    }

    @PostMapping("/makeDecision/{applicationId}")
    public String makeDecision(@PathVariable Integer applicationId) {
        Application application = applicationService.getApplication(applicationId);

        double damagedProductionAmount = application.getDamagedProductionAmount();
        double productionAmount = application.getProductionAmount();
        double ratio = damagedProductionAmount / productionAmount;

        //Αν το ratio είναι >0.4 η αίτηση του αγρότη εγκρίνεται, δηλαδή η κατεστραμμένη παραγωγή από την παραχθείσα παραγωγή ξεπερνά το 40% τότε ο αγρότης αποζημιώνεται, αλλιώς όχι.
        if (ratio > 0.4) {
            application.setDecision("Approved");
        } else {
            application.setDecision("Denied");
        }

        applicationService.saveApplication(application); //Αλλαγή της κατάστασης της αίτησης σε 'Approved' ή 'Denied' για την αίτηση.

        return "Application with id: " + applicationId + " has been checked "; //Επιστροφή κατάλληλου μηνύματος.
    }


    @PostMapping("/determineCompensation/{applicationId}")
    public String determineCompensation(@PathVariable Integer applicationId) {
        Application application = applicationService.getApplication(applicationId);

        //Έλεγχος αν η απόφαση για την αίτηση είναι 'Approved'
        if ("Approved".equals(application.getDecision())) {
            String categoryOfProduction = application.getCategoryOfProduction();

            double damagedProductionAmount = application.getDamagedProductionAmount();
            double compensationAmount = 0.0;

            if ("Fruits".equals(categoryOfProduction) || "fruits".equals(categoryOfProduction)) { //Σε περίπτωση που το είδος της παραγωγής είναι φρούτα ο αγρότης αποζημιώνεται
                //με 10 ευρώ για κάθε κιλό κατεστραμμένης παραγωγής.
                compensationAmount = 10 * damagedProductionAmount; //Σε περίπτωση που το είδος της παραγωγής είναι λαχανικά ο αγρότης αποζημιώνεται
                //με 20 ευρώ για κάθε κιλό κατεστραμμένης παραγωγής.
            } else if ("Vegetables".equals(categoryOfProduction) || "vegetables".equals(categoryOfProduction)) {
                compensationAmount = 20 * damagedProductionAmount;
            }

            //Ενημέρωση του πεδίου CompensationAmount.
            application.setCompensationAmount(compensationAmount);

            //Αποθήκευση της ενημερωμένης αίτησης.
            applicationService.saveApplication(application);
        } else { //Αν η απόφαση ήταν 'Denied' η αποζημίωση είναι 0.
            application.setCompensationAmount(0.0);
            applicationService.saveApplication(application);
        }

        //Επιστροφή κατάλληλου μηνύματος.
        return "Application with id: " + applicationId + " has been: " + application.getDecision() + " and the compensation amount is: " + application.getCompensationAmount();
    }
}
