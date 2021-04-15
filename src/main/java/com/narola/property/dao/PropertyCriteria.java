package com.narola.property.dao;

import com.narola.property.entity.Property;
import com.narola.property.security.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PropertyCriteria {
    private final String DEFAULT_SORT_BY = "propertyId";

    @Autowired
    private EntityManager entityManager;

    public List<Property> getPropertiesWithSearch(String sortBy, String sortOrder, int pageSize, String searchString,
                                                  int propertyId, int pageIndex) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Property> criteriaQuery = criteriaBuilder.createQuery(Property.class);
        Root<Property> property = criteriaQuery.from(Property.class);

        criteriaQuery.select(property);
        if (propertyId == 0/* && StringUtils.check(searchString, sortBy)*/) {
            criteriaQuery.orderBy(criteriaBuilder.asc(property.get(DEFAULT_SORT_BY)));
        } else if (StringUtils.check(searchString, sortBy)) {
            if (sortOrder.equals("ASC"))
                criteriaQuery.orderBy(criteriaBuilder.asc(property.get(DEFAULT_SORT_BY)));
            else
                criteriaQuery.orderBy(criteriaBuilder.desc(property.get(DEFAULT_SORT_BY)));
        } else {
            ArrayList<Predicate> conditions = new ArrayList<>();
            conditions.add(criteriaBuilder.like(property.get("name"), "%" + searchString + "%"));
            conditions.add(criteriaBuilder.like(property.get("price"), "%" + searchString + "%"));
            conditions.add(criteriaBuilder.like(property.get("location"), "%" + searchString + "%"));
            conditions.add(criteriaBuilder.like(property.get("type"), "%" + searchString + "%"));
//            Predicate findByCategoryId = criteriaBuilder.equal(category_Id, propertyId);

            criteriaQuery.where(criteriaBuilder.or(conditions.toArray(new Predicate[conditions.size()]))/*, criteriaBuilder.and(findByCategoryId)*/);

            if (sortOrder.equals("ASC"))
                criteriaQuery.orderBy(criteriaBuilder.asc(property.get(sortBy)));
            else
                criteriaQuery.orderBy(criteriaBuilder.desc(property.get(sortBy)));
        }
        TypedQuery<Property> query = entityManager.createQuery(criteriaQuery);
        return query.setFirstResult(pageSize * (pageIndex - 1)).setMaxResults(pageSize).getResultList();
    }
}
