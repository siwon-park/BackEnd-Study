package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em =emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 회원 생성
//            Member member = new Member();
//            member.setId(2L);
//            member.setName("HelloB");
//            em.persist(member);
            
            // 회원 조회
//            Member findMember = em.find(Member.class, 1L);
//            System.out.println(findMember.getId());
//            System.out.println(findMember.getName());
            
            // 회원 삭제
//            Member targetMember = em.find(Member.class, 1L);
//            em.remove(targetMember);

            // 회원 수정
//            Member targetMember = em.find(Member.class, 1L);
//            targetMember.setName("SIWON");

            // 쿼리문처럼 보이는 곳의 Member는 Member 테이블이 아니고, Member 객체임
            List<Member> result = em.createQuery("SELECT m FROM Member AS m", Member.class)
                    .getResultList();

            for (Member member : result) {
                System.out.println(member.getName());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
