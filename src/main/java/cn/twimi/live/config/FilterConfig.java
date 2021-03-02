package cn.twimi.live.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import cn.twimi.live.interceptor.PermissionInterceptor;

import javax.servlet.ServletContext;
import java.io.File;

@Configuration
public class FilterConfig implements WebMvcConfigurer {

    private final ServletContext context;

    public FilterConfig(ServletContext context) {
        this.context = context;
    }

    @Bean
    public PermissionInterceptor permissionInterceptor() {
        return new PermissionInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(permissionInterceptor())
                .excludePathPatterns("/api/user/login")
                .addPathPatterns("/api/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:" + context.getRealPath("upload").replace(File.separatorChar, '/') + '/');
    }
}
