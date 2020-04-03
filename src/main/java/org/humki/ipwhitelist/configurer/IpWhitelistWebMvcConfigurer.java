package org.humki.ipwhitelist.configurer;

import org.humki.ipwhitelist.config.IpWhitelistConfig;
import org.humki.ipwhitelist.interceptor.IpWhitelistInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <br>
 * <b>功能：</b>MVC配置<br>
 * 例如：public/image/** -> 磁盘某个位置<br>
 * 例如：拦截器<br>
 * <b>作者：</b>Pan.ShiJu<br>
 * <b>日期：</b>2017/4/11 23:27<br>
 *
 * @author Administrator
 */
@Configuration
public class IpWhitelistWebMvcConfigurer implements WebMvcConfigurer {

    private final IpWhitelistConfig ipWhitelistConfig;
    private final IpWhitelistInterceptor ipWhitelistInterceptor;

    @Autowired
    public IpWhitelistWebMvcConfigurer(IpWhitelistConfig ipWhitelistConfig, IpWhitelistInterceptor ipWhitelistInterceptor) {
        this.ipWhitelistConfig = ipWhitelistConfig;
        this.ipWhitelistInterceptor = ipWhitelistInterceptor;
    }

    /**
     * 静态资源映射
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }

    /**
     * addPathPatterns 添加拦截规则
     * excludePathPatterns 排除拦截
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 白名单配置
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(this.ipWhitelistInterceptor);
        if (this.ipWhitelistConfig.getIncludePatterns() != null) {
            interceptorRegistration.addPathPatterns(this.ipWhitelistConfig.getIncludePatterns());
        }
        if (this.ipWhitelistConfig.getExcludePatterns() != null) {
            interceptorRegistration.excludePathPatterns(this.ipWhitelistConfig.getExcludePatterns());
        }
    }

}
