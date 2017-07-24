package com.hafiz.www.shiro;

import com.hafiz.www.po.UserEntity;
import com.hafiz.www.service.UserService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Desc:自定义Realm
 * Created by hafiz.zhang on 2017/7/21.
 */
public class CustomRealm extends AuthorizingRealm{

    private static final String _WILDCARD = "*";
    private static final String _PATTERN_APPEND = "+.*";

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        UserEntity operator = (UserEntity) principalCollection.getPrimaryPrincipal();
        //获取该用户具有的所有的角色资源(把null换成findResourceUrlById())
        List<String> resourceList = null;
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> allPermissions = new HashSet<>(resourceList);
        allPermissions.remove("");
        allPermissions.remove(null);
        List<String> patternPermissions = new ArrayList<>();
        //通配url，以*，或者.*
        if (CollectionUtils.isNotEmpty(allPermissions)) {
            for (String per : allPermissions) {
                if (per.endsWith(_WILDCARD)) {
                    patternPermissions.add(per);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(patternPermissions)) {
            allPermissions.removeAll(patternPermissions);
            for (String pat : patternPermissions) {
                if(pat.endsWith(_WILDCARD)){
                    info.addObjectPermission(new CustomRegexPermission(pat.substring(0,pat.length()-1)+_PATTERN_APPEND));
                }else{
                    info.addObjectPermission(new CustomRegexPermission(pat+_PATTERN_APPEND));
                }
            }
        }
        info.addStringPermissions(allPermissions);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        List<UserEntity> users = userService.getByUserName(username);
        if (CollectionUtils.isEmpty(users)) {
            throw new UnknownAccountException();
        }
        if (users.size() != 1) {
            throw new LockedAccountException("用户名重复,请联系技术");
        }
        UserEntity user = users.get(0);
        username = user.getUserName();
        String password = user.getPassword();
        // 第一个参数也可以放user对象，这样在doGetAuthorizationInfo()方法中可以直接使用
        return new SimpleAuthenticationInfo(username, password, getName());
    }
}
