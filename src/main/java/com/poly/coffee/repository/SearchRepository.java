package com.poly.coffee.repository;

import com.poly.coffee.dto.response.PageResponse;
import com.poly.coffee.entity.Category;
import com.poly.coffee.entity.Drink;
import com.poly.coffee.entity.Order;
import com.poly.coffee.repository.criteria.SearchCriteria;
import com.poly.coffee.repository.criteria.SearchCriteriaQueryConsumer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class SearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public PageResponse<?> getDrinksSearch(int pageNo, int pageSize, String sortBy, String search) {
        // Query Select
        StringBuilder sqlQuery = new StringBuilder("SELECT o FROM Drink o");

        if (StringUtils.hasLength(search)) {
            sqlQuery.append(" WHERE LOWER(o.name) LIKE LOWER(:name)");
            sqlQuery.append(" OR LOWER(o.category.name) LIKE LOWER(:categoryName)");
        }

        if (StringUtils.hasLength(sortBy)) {
            // regex: firstName:asc|desc
            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                sqlQuery.append(String.format(" ORDER BY o.%s %s", matcher.group(1), matcher.group(3)));
            }
        }

        Query selectQuery = entityManager.createQuery(sqlQuery.toString());

        selectQuery.setFirstResult(pageNo * pageSize);
        selectQuery.setMaxResults(pageSize);

        if (StringUtils.hasLength(search)) {
            selectQuery.setParameter("name", String.format("%%%s%%", search));
            selectQuery.setParameter("categoryName", String.format("%%%s%%", search));
        }

        List drinks = selectQuery.getResultList();

        // Query Count Total Items
        StringBuilder sqlCountQuery = new StringBuilder("SELECT COUNT(*) FROM Drink o");

        if (StringUtils.hasLength(search)) {
            sqlCountQuery.append(" WHERE LOWER(o.name) LIKE LOWER(:name)");
            sqlCountQuery.append(" OR LOWER(o.category.name) LIKE LOWER(:categoryName)");
        }

        Query selectCountQuery = entityManager.createQuery(sqlCountQuery.toString());

        if (StringUtils.hasLength(search)) {
            selectCountQuery.setParameter("name", String.format("%%%s%%", search));
            selectCountQuery.setParameter("categoryName", String.format("%%%s%%", search));
        }

        Long totalItems = (Long) selectCountQuery.getSingleResult();

        Page<?> page = new PageImpl<Object>(drinks, PageRequest.of(pageNo, pageSize), totalItems);

        return PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .items(page.stream().toList())
                .build();
    }

    public PageResponse<?> advancedSearchDrinksWithCriteria(int pageNo, int pageSize, String sortBy, String categoryName, String... search) {

        List<SearchCriteria> criteriaList = new ArrayList<>();

        // 1. Get list
        if (search != null) {
            for (String searchQuery : search) {
                // firstName:value
                Pattern pattern = Pattern.compile("(\\w+?)(:|>|<)(.*)");
                Matcher matcher = pattern.matcher(searchQuery);
                if (matcher.find()) {
                    criteriaList.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
                }
            }
        }

        List<Drink> drinks = getDrinksWithCriteria(pageNo, pageSize, criteriaList, sortBy, categoryName);

        // 2. Count numbers of record

        Long totalElements = getTotalDrinkElements(criteriaList, categoryName);

        return PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPages((int) Math.ceil((double) totalElements / pageSize))
                .totalElements(totalElements)
                .items(drinks)
                .build();
    }

    private Long getTotalDrinkElements(List<SearchCriteria> criteriaList, String categoryName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Drink> root = query.from(Drink.class);

        // Processing search criteria queries
        Predicate predicate = criteriaBuilder.conjunction();
        SearchCriteriaQueryConsumer queryConsumer = new SearchCriteriaQueryConsumer(criteriaBuilder, predicate, root);

        criteriaList.forEach(queryConsumer);
        predicate = queryConsumer.getPredicate();

        // Search query: categoryName -> join table
        if (StringUtils.hasLength(categoryName)) {
            Join<Category, Drink> categoryDrinkJoin = root.join("category");
            Predicate categoryPredicate = criteriaBuilder.like(categoryDrinkJoin.get("name"), "%" + categoryName + "%");

            // Count total elements
            query.select(criteriaBuilder.count(root));

            query.where(predicate, categoryPredicate);
        } else {
            // Count total elements
            query.select(criteriaBuilder.count(root));

            query.where(predicate);
        }

        return entityManager.createQuery(query).getSingleResult();
    }

    private List<Drink> getDrinksWithCriteria(int pageNo, int pageSize, List<SearchCriteria> criteriaList, String sortBy, String categoryName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Drink> query = criteriaBuilder.createQuery(Drink.class);
        Root<Drink> root = query.from(Drink.class);

        // Processing search criteria queries
        Predicate predicate = criteriaBuilder.conjunction();
        SearchCriteriaQueryConsumer queryConsumer = new SearchCriteriaQueryConsumer(criteriaBuilder, predicate, root);

        criteriaList.forEach(queryConsumer);
        predicate = queryConsumer.getPredicate();

        // Search query: categoryName -> join table
        if (StringUtils.hasLength(categoryName)) {
            Join<Category, Drink> categoryDrinkJoin = root.join("category");
            Predicate categoryPredicate = criteriaBuilder.like(categoryDrinkJoin.get("name"), "%" + categoryName + "%");
            query.where(predicate, categoryPredicate);
        } else {
            query.where(predicate);
        }

        // Sort by
        if (StringUtils.hasLength(sortBy)) {
            // regex: firstName:asc|desc
            Pattern pattern = Pattern.compile("(\\w+?)(:)(asc|desc)");
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                String columnName = matcher.group(1);

                if (matcher.group(3).equalsIgnoreCase("desc")) {
                    query.orderBy(criteriaBuilder.desc(root.get(columnName)));
                } else {
                    query.orderBy(criteriaBuilder.asc(root.get(columnName)));
                }

            }
        }

        return entityManager.createQuery(query)
                .setFirstResult(pageNo * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public PageResponse<?> advancedSearchOrdersWithCriteria(int pageNo, int pageSize, String[] search) {
        List<SearchCriteria> criteriaList = new ArrayList<>();

        // 1. Get list
        if (search != null) {
            for (String searchQuery : search) {
                // firstName:value
                Pattern pattern = Pattern.compile("(\\w+?)(:|>|<)(.*)");
                Matcher matcher = pattern.matcher(searchQuery);
                if (matcher.find()) {
                    criteriaList.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
                }
            }
        }

        List<Order> orders = getOrdersWithCriteria(pageNo, pageSize, criteriaList);

        // 2. Count numbers of record

        Long totalElements = getTotalOrderElements(criteriaList);

        return PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPages((int) Math.ceil((double) totalElements / pageSize))
                .totalElements(totalElements)
                .items(orders)
                .build();
    }

    private Long getTotalOrderElements(List<SearchCriteria> criteriaList) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Order> root = query.from(Order.class);

        // Processing search criteria queries
        Predicate predicate = criteriaBuilder.conjunction();
        SearchCriteriaQueryConsumer queryConsumer = new SearchCriteriaQueryConsumer(criteriaBuilder, predicate, root);

        criteriaList.forEach(queryConsumer);
        predicate = queryConsumer.getPredicate();

        // Count total elements
        query.select(criteriaBuilder.count(root));

        query.where(predicate);

        return entityManager.createQuery(query).getSingleResult();

    }

    private List<Order> getOrdersWithCriteria(int pageNo, int pageSize, List<SearchCriteria> criteriaList) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);

        // Processing search criteria queries
        Predicate predicate = criteriaBuilder.conjunction();
        SearchCriteriaQueryConsumer queryConsumer = new SearchCriteriaQueryConsumer(criteriaBuilder, predicate, root);

        criteriaList.forEach(queryConsumer);
        predicate = queryConsumer.getPredicate();
        query.where(predicate);

        return entityManager.createQuery(query)
                .setFirstResult(pageNo * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

}
