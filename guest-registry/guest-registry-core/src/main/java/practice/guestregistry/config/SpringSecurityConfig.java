//package practice.guestregistry.config;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
////@Configuration
////public class GuestRegistrySecurityConfig extends WebSecurityConfigureAdapter {
////    @Override
////    protected void configure(HttpSecurity httpSecurity) throws Exception {
////        httpSecurity.authorizeRequests().antMatchers("/","/swagger-resources").permitAll();
////        httpSecurity.csrf().disable();
////        httpSecurity.headers().frameOptions().disable();
////    }
////}
//@Configuration
//@EnableWebSecurity
////@EnableGlobalMethodSecurity(
////        prePostEnabled = true)
//////        securedEnabled = true,
//////        jsr250Enabled = true)
//public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private UserDetailsService customUserDetailService;
//
//    @Autowired
//    private RestAuthenticationSuccessHandler authenticationSuccessHandler;
//
//    public SpringSecurityConfig (UserDetailsService customUserDetailService) {
//        this.customUserDetailService = customUserDetailService;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customUserDetailService)
//                .passwordEncoder(this.passwordEncoder());
//
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .httpBasic()
//                .and()
//                .securityContext()
//                .and()
//                .execptionHandling()
//                .and()
//                .logout()
//                .and()
//                .rememberMe()


//        http
//                .cors()
//                .and()
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/api/*").hasRole("USER")
//                .antMatchers("/login", "/user").permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic();
//
//
////        http
////                .authorizeRequests()
////                .antMatchers("/resources/", "/webjars/","/assets/")
////                .permitAll()
////                .antMatchers("/").permitAll()
////                .antMatchers("/admin/").hasRole("ADMIN")
////                .anyRequest().authenticated()
////                .and()
////                .formLogin()
////                .loginPage("/login")
////                .defaultSuccessUrl("/home")
////                .failureUrl("/login?error")
////                .permitAll()
////                .and()
////                .logout()
////                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
////                .logoutSuccessUrl("/login?logout")
////                .permitAll()
////                .and()
////        -----------------------------
////                .rememberMe("remember-me") // remember stuff game
////                .and()        // remember stuf game
////        -----------------------------
////                .rememberMe()
////                .key("my-secure-key")
////                .rememberMeCookieName("my-remember-me-cookie")
////                .tokenValiditySeconds(30*60)
////                .and()
////        -----------------------------
////                .exceptionHandling()
////                .accessDeniedPage("/accessDenied");
//
//
//
//
//    }
//
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/","/register","/forgotPassword").permitAll()
//                .antMatchers("/admin/").hasRole("ADMIN")
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(
//                        new Http401AuthenticationEntryPoint("Basic realm=\"MyApp\""))
//                .and()
//                .formLogin()
//                .permitAll()
//                .loginProcessingUrl("/login")
//                .successHandler(authenticationSuccessHandler)
//                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
//                .and()
//                .logout()
//                .permitAll()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessHandler(
//                        new HttpStatusReturningLogoutSuccessHandler());
//    }
//
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("pass")
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
//
//    PersistentTokenRepository persistentTokenRepository(){
//        JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
//        tokenRepositoryImpl.setDataSource(dataSource);
//        return tokenRepositoryImpl;
//    }}
//
////   {
////           "password":null,
////           "username":"admin@gmail.com",
////           "authorities":[
////           {"authority":"ROLE_ADMIN"},
////           {"authority":"ROLE_USER"}
////           ],
////           "accountNonExpired":true,
////           "accountNonLocked":true,
////           "credentialsNonExpired":true,
////           "enabled":true,
////           "name":"admin@gmail.com"
////           }