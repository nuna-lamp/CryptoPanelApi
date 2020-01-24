package de.lamp.cryptopanel.helper;


import de.lamp.cryptopanel.model.Invoices;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RequestArgumentsHandler {

    public List<Predicate> buildPredicateListForFromArguments(Map<String, Object> arguments, CriteriaBuilder criteriaBuilder, Root<Invoices> root){

        List<Predicate> predicates = new ArrayList<>();

        for (Map.Entry<String, Object> entry : arguments.entrySet()) {
            if (!(null == entry) || entry.equals("email")) {
                predicates.add(criteriaBuilder.equal(root.get("email"), entry.getValue()));
            } else if (!(null == entry) || entry.getValue().equals("last_name")) {
                predicates.add(criteriaBuilder.equal(root.get("last_name"), entry.getValue()));
            } else if (!(null == entry) || entry.getValue().equals("first_name")) {
                predicates.add(criteriaBuilder.equal(root.get("first_name"), entry.getValue()));
            }
        }

        return predicates;
    }

}
