package br.csi.porkManagerApi.security;


import br.csi.porkManagerApi.models.Usuario;
import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final AuthFilter authFilter;

    public SecurityConfig(AuthFilter authFilter) {
        this.authFilter = authFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/auth").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuario/**").hasAuthority(Usuario.Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/usuario/**").hasAuthority(Usuario.Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/usuario/**").hasAuthority(Usuario.Role.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/raca/saveRaca").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.PUT, "/raca/updateRaca").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.DELETE, "/raca/deleteRaca").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.GET, "/raca/getRaca").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.GET, "/raca/getAllRacas").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.POST, "/alojamento/saveAlojamento").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.PUT, "/alojamento/updateAlojamento").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.DELETE, "/alojamento/deleteAlojamento").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.GET, "/alojamento/getAlojamento").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.GET, "/alojamento/getAllAlojamentos").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.POST, "/suino/saveSuino").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.PUT, "/suino/updateSuino").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.DELETE, "/suino/deleteSuino").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.GET, "/suino/getSuino").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.GET, "/suino/getAllSuinos").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.POST, "/saude/saveSaude").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.PUT, "/saude/updateSaude").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.DELETE, "/saude/deleteSaude").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.GET, "/saude/getAllSaudes").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .requestMatchers(HttpMethod.GET, "/saude/getSaude").hasAnyAuthority(Usuario.Role.ADMIN.name(), Usuario.Role.CRIADOR.name())
                        .anyRequest().authenticated()
                )
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");
        config.setAllowedHeaders(Arrays.asList(
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT,
                HttpHeaders.ORIGIN,
                "timezone"
        ));
        config.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.DELETE.name(),
                HttpMethod.PUT.name(),
                HttpMethod.HEAD.name(),
                HttpMethod.OPTIONS.name()
        ));
        config.setMaxAge(3600L);
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
