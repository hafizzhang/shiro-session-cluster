package com.hafiz.www.shiro;

import org.apache.shiro.authz.Permission;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Desc:
 * Created by hafiz.zhang on 2017/7/22.
 */
public class CustomRegexPermission implements Permission,Serializable {

    private String pattern;

    public CustomRegexPermission(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean implies(Permission permission) {
        if (pattern == null) {
            throw new IllegalArgumentException("pattern argument cannot be null.");
        }
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(p.toString().replace("[","").replace("]",""));
        return m.matches();
    }
}
