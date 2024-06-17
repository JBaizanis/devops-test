package dit.hua.distributedSystems.project.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.cors.CorsConfiguration;
import java.util.List;
import java.util.Arrays;

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean (AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();

    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("http://51.120.247.169")); // IP vm DevOps3 για την εκτέλεση της εφαρμογής μέσω Ansible και Ansible-Docker με την χρήση Jenkins.
        corsConfiguration.setAllowedOrigins(List.of("http://4.211.248.159:9000")); // IP vm DevOps-01 για την εκτέλεση της εφαρμογής μέσω Docker compose με την χρήση του αντίστοιχου playbook.
        // Αφαίρεση του συγκεκριμένου comment παρακάτω για την τοπική εκτέλεση της εφαρμογής. Ύστερα προσθήκη σε comment τις δύο προηγούμενες εντολές.
        // corsConfiguration.setAllowedOrigins(List.of("http://localhost:5173"));
        corsConfiguration.setAllowedMethods(Arrays.asList("*"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setExposedHeaders(List.of("Authorization"));

        http
                .csrf(csrf -> csrf.disable())
//                .cors(cors -> cors.disable())
                .cors(cors -> cors.configurationSource(request -> corsConfiguration))
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(unauthorizedHandler))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**","/actuator/health/**").permitAll()
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/v2/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .requestMatchers("api/user/new","api/user/{user_id}","api/user/role/{user_id}/{role_id}").hasRole("ADMIN")
                        .requestMatchers("api/application/new/{user_id}","api/application/user/{user_id}","api/application/{applicationId}").hasRole("FARMER")
                        .requestMatchers("api/application/makeDecision/{applicationId}","api/application","api/application/determineCompensation/{applicationId}").hasRole("INSPECTOR")
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


//        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();


    }



}
