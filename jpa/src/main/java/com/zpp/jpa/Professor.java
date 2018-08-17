package com.zpp.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
/**
 * @author pingpingZhong
 *         Date: 2017/5/26
 *         Time: 14:45
 */
import java.util.ArrayList;
import java.util.Collection;


@Entity
@Table(name = "EMP")
@SqlResultSetMappings({

        @SqlResultSetMapping(
                name = "ProfessorWithAddressColumnAlias",
                entities = {@EntityResult(entityClass = Professor.class,
                        fields = @FieldResult(name = "id", column = "EMP_ID")),
                        @EntityResult(entityClass = Address.class)}
        )

})
public class Professor {
    @Id
    @Column(name = "EMP_ID")
    private int id;

    private String name;

    private long salary;

    @OneToOne
    private Address address;

    @ManyToOne
    @JoinColumn(name = "DEPT_ID")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "MANAGER_ID")
    private Professor manager;

    @OneToMany(mappedBy = "manager")
    private Collection<Professor> directs = new ArrayList<Professor>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Collection<Professor> getDirects() {
        return directs;
    }

    public void addDirect(Professor employee) {
        if (!getDirects().contains(employee)) {
            getDirects().add(employee);
            if (employee.getManager() != null) {
                employee.getManager().getDirects().remove(employee);
            }
            employee.setManager(this);
        }
    }

    public Professor getManager() {
        return manager;
    }

    public void setManager(Professor manager) {
        this.manager = manager;
    }

    public String toString() {
        return "Professor id: " + getId() + " name: " + getName() + " with MgrId: "
                + (getManager() == null ? null : getManager().getId());
    }
}