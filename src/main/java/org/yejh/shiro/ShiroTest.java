package org.yejh.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShiroTest {
    private static final Logger LOG = LoggerFactory.getLogger(ShiroTest.class);

    public static void main(String[] args) {
        /**
         * 1. 获取安全管理器
         * 2. 获取用户
         * 3. 用户登录验证
         * 4. 角色管理
         * 5. 权限管理
         * 6. session:用户从登录到退出，作用域
         */

        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();

        SecurityUtils.setSecurityManager(securityManager);

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        session.setAttribute("name", "yejiahao");
        String name = (String) session.getAttribute("name");
        if (name != null) {
            LOG.info("shiro已经帮我们获得了session会话中对象指定的值： " + name);
        }

        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken("lisi", "456");
            token.setRememberMe(true);
            try {
                currentUser.login(token);
                LOG.info("用户名和密码正确，登录成功");
            } catch (UnknownAccountException e) {
                LOG.error("账户不存在");
            } catch (IncorrectCredentialsException e) {
                LOG.error("密码错误");
            } catch (LockedAccountException e) {
                LOG.error("用户已经锁死");
            } catch (AuthenticationException e) {
                LOG.error("认证异常");
            }
        }

        if (currentUser.hasRole("role2")) {
            LOG.info("拥有指定的角色");
        } else {
            LOG.info("不拥有指定的角色");
        }

        if (currentUser.isPermitted("permission2")) {
            LOG.info("拥有指定的权限");
        } else {
            LOG.info("不拥有指定的权限");
        }

        currentUser.logout();
        LOG.info("用户已退出");
    }
}
