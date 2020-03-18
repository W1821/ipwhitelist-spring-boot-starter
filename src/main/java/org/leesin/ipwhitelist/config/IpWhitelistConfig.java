package org.leesin.ipwhitelist.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <br>
 * <b>功能：</b>ip白名单配置<br>
 * <b>作者：</b>Pan.ShiJu<br>
 * <b>日期：</b>2017/4/11 23:27<br>
 *
 * @author Administrator
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "leesin.ipwhitelist")
public class IpWhitelistConfig {

    private boolean enable;

    private String[] allow;

    private String[] includePatterns;

    private String[] excludePatterns;

}
