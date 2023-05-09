package com.join.config;

import com.join.interceptor.ThreadLocalInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author join
 * @Description
 * @date 2023/3/1 15:08
 */
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Autowired
    private ThreadLocalInterceptor threadLocalInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(threadLocalInterceptor).addPathPatterns("/**");
    }

//    /**
//     * 跨域配置
//     * @return
//     */
//    @Bean
//    public CorsFilter corsFilter(){
//        CorsConfiguration config = new CorsConfiguration();
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
////        config.addAllowedOrigin("*");
//        config.addAllowedOriginPattern("*");
//        config.setAllowCredentials(true);
//        config.setMaxAge(3600L);
//        config.addExposedHeader("set-cookie");
//        config.addExposedHeader("Access-Control-Allow-Headers");
//        config.addExposedHeader("Access-Control-Allow-Methods");
//        config.addExposedHeader("Access-Control-Allow-Origin");
//        config.addExposedHeader("Access-Control-max-age");
//        config.addExposedHeader("X-Frame-Options");
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**",config);
//        return new CorsFilter(source);
//    }

}
