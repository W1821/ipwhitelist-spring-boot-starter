package org.leesin.ipwhitelist.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.leesin.ipwhitelist.config.IpWhitelistConfig;
import org.leesin.ipwhitelist.configurer.IpWhitelistWebMvcConfigurer;
import org.leesin.ipwhitelist.interceptor.IpWhitelistInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Slf4j
@Configuration
@EnableConfigurationProperties(IpWhitelistConfig.class)
@Import(IpWhitelistInterceptor.class)
public class IpWhitelistAutoConfigure {

    @Autowired
    private IpWhitelistConfig ipWhitelistConfig;

    @Autowired
    private IpWhitelistInterceptor ipWhitelistInterceptor;

    @Bean
    @ConditionalOnMissingBean
    IpWhitelistWebMvcConfigurer ipWhitelistWebMvcConfigurer() {
        return new IpWhitelistWebMvcConfigurer(ipWhitelistConfig, ipWhitelistInterceptor);
    }


}
