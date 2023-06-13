package com.exlservice.timesheet.security.config;

import com.exlservice.timesheet.security.constant.TimesheetSecurityJDBCQueryConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class ExlTimesheetSecurityConfig {
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);

        //define query to find users by username
        userDetailsManager.setUsersByUsernameQuery(TimesheetSecurityJDBCQueryConstants.FIND_USERS_BY_USERNAME_QUERY);

        //define query to find roles by username
        userDetailsManager.setAuthoritiesByUsernameQuery(TimesheetSecurityJDBCQueryConstants.FIND_AUTHORITIES_BY_USERNAME_QUERY);

        return userDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requestConfigurer -> requestConfigurer
                .requestMatchers(HttpMethod.GET, "/exl-timesheet-api/employee/name/**").hasRole("MANAGER")
                .requestMatchers(HttpMethod.GET, "/exl-timesheet-api/employee/**").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "/exl-timesheet-api/employees/manager").hasRole("MANAGER")
                .requestMatchers(HttpMethod.GET, "/exl-timesheet-api/employees/manager/**").hasRole("MANAGER")
                .requestMatchers(HttpMethod.GET, "/exl-timesheet-api/employees/managers/**").hasRole("ADMIN"));

        //use HTTP basic authentication
        http.httpBasic(Customizer.withDefaults());

        //disable Cross Site Request Forgery (CSRF) protection as not required
        //for stateless REST APIs that use POST, PUT, DELETE and/or PATCH
        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
