package com.zpp.ldap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.OrFilter;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Service;

/**
 * @author pingpingZhong
 *         Date: 2017/8/28
 *         Time: 18:02
 */
@Service
public class LdapService implements CommandLineRunner {

    @Autowired
    private LdapTemplate ldapTemplate;

    public boolean validate(String username, String mobile, String password) {
        OrFilter orFilter = new OrFilter();
        AndFilter andFilter = new AndFilter();
        orFilter.or((new EqualsFilter("uid", username)))
                .or(new EqualsFilter("mobile", mobile));
        andFilter.and(new EqualsFilter("objectclass", "inetOrgPerson")).and(orFilter);
        return ldapTemplate.authenticate(LdapUtils.emptyLdapName(), andFilter.toString(), password);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(validate("zhongpp", "1591", "1234567"));
    }
}
