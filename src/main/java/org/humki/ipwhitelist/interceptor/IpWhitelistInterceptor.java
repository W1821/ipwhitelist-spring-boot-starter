package org.humki.ipwhitelist.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.humki.ipwhitelist.config.IpWhitelistConfig;
import org.humki.ipwhitelist.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * <br>
 * <b>功能：</b>ip白名单配置拦截器<br>
 * <b>作者：</b>Pan.ShiJu<br>
 * <b>日期：</b>2017/4/11 23:27<br>
 *
 * @author Administrator
 */
@Slf4j
@Component
public class IpWhitelistInterceptor implements HandlerInterceptor {

    private boolean ipWhitelistIsEnable;
    private List<String> ipWhitelistAllowList;

    private final IpWhitelistConfig ipWhitelistConfig;

    @Autowired
    public IpWhitelistInterceptor(IpWhitelistConfig ipWhitelistConfig) {
        this.ipWhitelistConfig = ipWhitelistConfig;
        init();
    }

    /**
     * 初始化数据
     */
    private void init() {
        if (!this.ipWhitelistConfig.isEnable()) {
            log.warn("ip白名单未开启，leesin.ipwhitelist.enable={}", this.ipWhitelistConfig.isEnable());
            this.ipWhitelistIsEnable = false;
            return;
        }
        if (this.ipWhitelistConfig.getAllow() == null || this.ipWhitelistConfig.getAllow().length == 0) {
            log.warn("ip白名单未开启，leesin.ipwhitelist.enable={},leesin.ipwhitelist.allow={}", this.ipWhitelistConfig.isEnable(), this.ipWhitelistConfig.getAllow());
            this.ipWhitelistIsEnable = false;
            return;
        }
        log.info("ip白名单开启成功，leesin.ipwhitelist.enable={},leesin.ipwhitelist.allow={}", this.ipWhitelistConfig.isEnable(), this.ipWhitelistConfig.getAllow());
        this.ipWhitelistIsEnable = true;
        this.ipWhitelistAllowList = Arrays.asList(this.ipWhitelistConfig.getAllow());
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!ipWhitelistIsEnable) {
            return true;
        }
        String ip = IpUtil.getRealIP(request);
        boolean allow = ipWhitelistAllowList.contains(ip);
        if (!allow) {
            try {
                response.sendError(401, "无权限");
            } catch (IOException ignored) {
            }
        }
        return allow;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }

}
