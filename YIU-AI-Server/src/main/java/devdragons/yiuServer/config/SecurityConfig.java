package devdragons.yiuServer.config;

import devdragons.yiuServer.security.JwtAuthenticationFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import devdragons.yiuServer.security.JwtProvider;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Component
public class SecurityConfig {
    private final JwtProvider jwtProvider;

    @Bean
    public SecurityFilterChain filterChain(final @NotNull HttpSecurity http) throws Exception {
        http
                .httpBasic(HttpBasicConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .cors(c -> {
                    CorsConfigurationSource source = request -> {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowCredentials(true);
                        config.setAllowedOrigins(List.of("http://ai.yongin.ac.kr"));
                        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
                        config.setAllowedHeaders(List.of("*"));
                        config.setExposedHeaders(List.of("*"));
                        return config;
                    };
                    c.configurationSource(source);
                })
                .sessionManagement(configurer ->
                        configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/login", "/api/register", "/api/register/email", "/api/pwd/email", "/api/pwd/change", "/api/token/refresh", "/api/main", "/api/council", "/api/news", "/api/info", "/api/mou", "/api/member", "/api/graduateSchool", "/api/microdegree", "/api/curriculum", "/api/files/*", "/api/microdegree", "/api/microdegreeSubject", "/api/greeting", "/api/graduation", "/api/news/search", "/api/news/search-title", "/api/news/search-contents", "/api/student", "/api/student/all", "/api/student/search", "/api/graduationDetail", "/api/userGraduation").permitAll()
                        .requestMatchers("/api/council/admin", "/api/news/admin", "/api/info/admin", "/api/mou/admin", "/api/member/admin", "/api/graduateSchool/admin", "/api/microdegree/admin", "/api/curriculum/admin", "/api/microdegreeSubject/admin", "/api/greeting/admin", "/api/graduation/admin", "/api/graduationDetail/admin", "/api/userGraduation/admin").hasAnyRole("ADMIN", "SUPER")
                        .anyRequest().authenticated())
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(authenticationManager -> authenticationManager
                        .authenticationEntryPoint(new AuthenticationEntryPoint() {
                            @Override
                            public void commence(HttpServletRequest request, HttpServletResponse response,
                                                 AuthenticationException authException)
                                    throws IOException, ServletException {
                                response.setStatus(401);
                                response.setCharacterEncoding("utf-8");
                                response.setContentType("text/html; charset=UTF-8");
                                response.getWriter().write("인증되지 않은 사용자입니다.");
                                System.out.println("인증 실패: " + authException.getMessage());
                            }
                        })
                        .accessDeniedHandler(new AccessDeniedHandler() {
                            @Override
                            public void handle(HttpServletRequest request, HttpServletResponse response,
                                               AccessDeniedException accessDeniedException) throws IOException, ServletException {
                                response.setStatus(403);
                                response.setCharacterEncoding("utf-8");
                                response.setContentType("text/html; charset=UTF-8");
                                response.getWriter().write("권한이 없는 사용자입니다.");
                                System.out.println("권한 없음: " + accessDeniedException.getMessage());
                            }
                        })
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}