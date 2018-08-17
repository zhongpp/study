package com.zpp.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * @author pingpingZhong
 *         Date: 2017/5/26
 *         Time: 14:47
 */

public class ProfessorService {
    protected EntityManager em;

    public ProfessorService(EntityManager em) {
        this.em = em;
    }

    public List findWithAlias() {
        Query query = em.createNativeQuery(
                "SELECT emp.emp_id AS emp_id, name, salary, manager_id, dept_id, address_id, "
                        + "address.id, street, city, state, zip " + "FROM emp, address "
                        + "WHERE address_id = id", "ProfessorWithAddressColumnAlias");
        return query.getResultList();
    }

}
