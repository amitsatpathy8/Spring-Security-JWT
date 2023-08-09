package com.person.SpringSecurityJWTDemo001.SecurityConfig;

import com.person.SpringSecurityJWTDemo001.filter.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class Config {

    @Autowired
    private JwtAuthFilter authFilter;

    /**
     * This method return our entity details in the form of the UserDetailsService
     * which is a predefined class present in "org.springframework.security.core.userdetails.UserDetailsService"
     * package.
     *
     * Inorder to do so we have to create custom class which implements the UserDetails Interface
     * present in "org.springframework.security.core.userdetails.UserDetails".
     * There you have to provide the implementation to the abstract members
     * so that spring will understand what is your entity's credential.
     *
     * Here we do that by creating a class called PersonIntoUserDetails.
     *
     * But the problem is Spring need UserDetailsService object in order to fetch
     * all your credentials using the username.
     * To do so, we have to create another that implements the UserDetailsService interface
     * present in "org.springframework.security.core.userdetails.UserDetailsService".
     * Spring will automatically call loadUserByUsername(String username) method.
     *
     * In that class, your job is:
     * STEP:1
     * ----------------
     * call corresponding DAO class object to get all the details
     * from the database of the given username (String).
     *
     * STEP:2
     * ---------------
     * If details present then we have to convert these details to UserDetails of child class that you previously created.
     * If not, raise an exception "UsernameNotFoundException("Customized massage")"
     *
     * ----------------------------------------
     * Here all these tasks are done in the two classes:
     *          - PersonIntoUserDetails
     *          - PersonIntoUserDetailsService
     *
     */
    @Bean
    public UserDetailsService userDetailsService(){
        return new PersonIntoUserDetailsService();
    }

    /**
     *  This is the password encoder that is used to password comparison of the encoded password
     */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Here we are telling Spring that where is userDetailsService present and
     * what is our password encoding type.
     */

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /**
     * This will help Spring to tell what is out authentication Manager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * This will tell Spring that what URL should it filter and what it should not
     * be based on role-based filtration also.
     *
     * Also, here all security-related configuration is done in this method.
     * For letting Spring know to use your JWT filter before using its own filter mechanism
     * you have to use method addFilterBefore().
     */

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(
                configure -> configure
                        .requestMatchers(HttpMethod.GET,"person/statusCheck").permitAll()
                        .requestMatchers(HttpMethod.POST,"person/login").permitAll()

                        .requestMatchers(HttpMethod.GET,"person/details").hasRole("USER")
                        .requestMatchers(HttpMethod.POST,"person/updatePassword").hasRole("USER")

                        .requestMatchers(HttpMethod.GET,"/gov/**").hasRole("AUTH-GOV")
                        .requestMatchers(HttpMethod.POST,"/gov/**").hasRole("AUTH-GOV")
                        .requestMatchers(HttpMethod.PUT,"/gov/**").hasRole("AUTH-GOV")

                        .requestMatchers(HttpMethod.DELETE,"/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/admin/**").hasRole("ADMIN")
        ).sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authenticationProvider(authenticationProvider()).addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * This method was initialy used for inserting data in the database because at first there is no data present,
     * so you need backend entry.
     */

//        @Bean
//    public InMemoryUserDetailsManager userDetailsManager() {
//        UserDetails admin = User.builder().username("admin").password("$2a$10$6pDB9L0Ns8G6ORkL3DW6AOBPiwuDG3v6Q/f9jg3WlJHaEyrPLPs7G").roles("USER", "MANAGER", "ADMIN").build();
//        return new InMemoryUserDetailsManager(admin);
//    }

}
