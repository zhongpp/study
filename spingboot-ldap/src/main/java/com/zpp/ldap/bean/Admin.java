package com.zpp.ldap.bean;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;

/**
 * @author pingpingZhong
 *         Date: 2017/10/31
 *         Time: 17:42
 */
@Entry(objectClasses = {"person", "top"}, base = "ou=company1")
public class Admin {
    @Id
    private Name dn;

    @Attribute(name = "cn")
    private String fullName;

    public Name getDn() {
        return this.dn;
    }

    public void setDn(Name dn) {
        this.dn = dn;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "dn=" + this.dn +
                ", fullName='" + this.fullName + '\'' +
                '}';
    }
}
