package wiseSWlife.wiseSWlife.global.config.webConfig;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import wiseSWlife.wiseSWlife.global.config.interceptor.LoginCheckInterceptor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/",
                        "/community/freeBoard",
                        "/community/auctionBoard",
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
