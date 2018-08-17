package com.zpp.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author pingpingZhong
 *         Date: 2017/5/26
 *         Time: 14:48
 */

public class Main {
    public static void main(String[] a) throws Exception {
        JPAUtil util = new JPAUtil();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProfessorService");
        EntityManager em = emf.createEntityManager();
        ProfessorService service = new ProfessorService(em);

        em.getTransaction().begin();


        service.findWithAlias();

        util.checkData("select * from EMP");

        em.getTransaction().commit();
        em.close();
        emf.close();
    }
}