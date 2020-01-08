package de.lamp.cryptopanel.repositories;

import de.lamp.cryptopanel.model.Invoices;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InvoicesRepositoryImpl implements InvoiceRepositoryCustom {

    @Autowired
    InvoicesRepository invoicesRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List getAllBetweenDatesAndStatus(
            String from,
            String to,
            String status,
            String email) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Invoices> cq = cb.createQuery(Invoices.class);
        Root<Invoices> invoices = cq.from(Invoices.class);

        List<Predicate> predicates = new ArrayList<>();

        if (!(null == status || status.equals(""))) {
            predicates.add(cb.equal(invoices.get("status"),status));
        }
        if (!(null == email || email.equals(""))) {
            predicates.add(cb.equal(invoices.get("email"),email));
        }

       for(int i = 0; i< predicates.size(); i++)
        {
            cq.where(predicates.toArray(new Predicate[i]));
        }

        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public Double getAllBetweenDatesAndSum(
        String from,
        String to,
        String status,
        String amount) {

            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Double> query = criteriaBuilder.createQuery(Double.class);

            Root<Invoices> invoices = query.from(Invoices.class);

            query.select(criteriaBuilder.sum(invoices.get("amount")));

            List<Predicate> predicates = new ArrayList<>();

            if (!(null == status || status.equals(""))) {
                predicates.add(criteriaBuilder.equal(invoices.get("status"),status));
            }


            for(int i = 0; i< predicates.size(); i++)
            {
                query.where(predicates.toArray(new Predicate[i]));
            }


            TypedQuery<Double> typedQuery = entityManager.createQuery(query);
            Double sum = typedQuery.getSingleResult();
            return sum;

    }

    @Override
    public List<Invoices> getAllBetweenDatesAndArguments(Map<String, Object> arguments) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Invoices> cq = cb.createQuery(Invoices.class);
        Root<Invoices> invoices = cq.from(Invoices.class);

        List<Predicate> predicates = new ArrayList<>();

        for(Map.Entry<String, Object> entry : arguments.entrySet()){
            if(null != entry &&  entry.getKey().equals("fromDate")){
                // predicates.add(cb.equal(invoices.get(entry.getKey()),entry.getValue()));
            }else
                if(!(null == entry) || entry.getValue().equals("")){
                predicates.add(cb.equal(invoices.get(entry.getKey()),entry.getValue()));
            }
        }

        for(int i = 0; i< predicates.size(); i++)
        {
            cq.where(predicates.toArray(new Predicate[i]));
        }

        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public List<Invoices> findByLastNameAndFirsName(String last_name, String first_name) {
        Query query = entityManager.createNativeQuery(
                "SELECT * FROM `invoices` WHERE last_name =:lname or first_name =:fname ", Invoices.class);

        query.setParameter("lname", last_name);
        query.setParameter("fname", first_name);
        return query.getResultList();
    }

}
