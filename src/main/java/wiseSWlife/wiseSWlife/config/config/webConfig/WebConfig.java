package wiseSWlife.wiseSWlife.config.config.webConfig;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import wiseSWlife.wiseSWlife.config.config.interceptor.LoginCheckInterceptor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/**",
                        "/signUp",
                        "/login",
                        "/logout",
                        "/css/**",
                        "/images/**",
                        "/js/**",
                        "/*.ico",
                        "/error",
                        "/webfonts/**");

        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
