package dit.hua.distributedSystems.project.demo;

import dit.hua.distributedSystems.project.demo.entity.MUser;
import dit.hua.distributedSystems.project.demo.entity.Role;
import dit.hua.distributedSystems.project.demo.service.RoleService;
import dit.hua.distributedSystems.project.demo.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SpringbootdemoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
    private UserDetailsServiceImpl userService;
    @Autowired
    private RoleService roleService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@BeforeEach
    void setUp() {
		if (roleService.getRoles().isEmpty()) {
            Role role = new Role();
            role.setId(1);
            role.setRole("ROLE_FARMER");
            roleService.saveRole(role);
            Role role1 = new Role();
            role1.setId(2);
            role1.setRole("ROLE_INSPECTOR");
            roleService.saveRole(role1);
            Role role2 = new Role();
            role2.setId(3);
            role2.setRole("ROLE_ADMIN");
            roleService.saveRole(role2);

            MUser adminUser = new MUser();

            adminUser.setUsername("admin");
            adminUser.setPassword("pavlosnikolopoulos44");
            String passencode=passwordEncoder.encode(adminUser.getPassword());
            adminUser.setPassword(passencode);
            adminUser.setFirstName("Pavlos");
            adminUser.setLastName("Nikolopoulos");
            adminUser.setEmail("pavlosnikolopoulos@gmail.com");
            adminUser.setPhone("6942553328");
            adminUser.setAddress("Kipon 44");
            adminUser.setRole(role2);
            userService.saveUser(adminUser);
        }
    }

	@Test
	void contextLoads() {
	}

	@Test
	public void testSignUser() throws Exception {
		// Arrange
		String userJson = "{\"username\":\"admin\",\"password\":\"pavlosnikolopoulos44\"}";

		// Act
		ResultActions result = mockMvc.perform(post("/api/auth/signin")
				.contentType(MediaType.APPLICATION_JSON)
				.content(userJson));

		// Assert
		result.andExpect(status().isOk())
				.andExpect(jsonPath("$.username").value("admin"));

	}

}
