package cn.com.dhcc.credit.approval.service.base;

import cn.com.dhcc.credit.approval.bean.entity.ApprTask;
import cn.com.dhcc.credit.platform.util.ArrayUtil;
import cn.com.dhcc.credit.platform.util.Collections3;
import cn.com.dhcc.credit.platform.util.PageBean;
import cn.com.dhcc.credit.platform.util.jpa.PageUtil;
import cn.com.dhcc.credit.platform.util.jpa.SearchFilter;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author by 王豪伟
 * @Description BaseService
 * @Date 2020/7/3 17:09
 */
public class BaseService {

    public PageRequest buildPageRequest(PageBean page, String order, String... orderByStr) {
        int curPage = page.getCurPage();
        int maxSize = page.getMaxSize();
        PageRequest pageRequest = PageUtil.buildPageRequest(curPage, maxSize, order, orderByStr);
        return pageRequest;
    }

    /**
     *
     * @param searchParams
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> Specification<T> buildSpecification(Map<String, Object> searchParams, Class<T> tClass) {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        Collection<SearchFilter> searchFilters = filters.values();
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

                if (Collections3.isNotEmpty(searchFilters)) {

                    List<Predicate> andPredicates = Lists.newArrayList();
                    List<Predicate> orPredicates = Lists.newArrayList();
                    for (SearchFilter filter : searchFilters) {
                        Path expression = root.get(filter.fieldName);
                        switch (filter.conjunctionType) {
                            case OR:
                                orPredicates.add(buildPredicate(filter, builder, expression));
                                break;
                            case ADD:
                                andPredicates.add(buildPredicate(filter, builder, expression));
                                break;
                            default:
                        }
                    }
                    Predicate andPre = null;
                    Predicate orPre = null;
                    if (!andPredicates.isEmpty() && !orPredicates.isEmpty()) {
                        andPre = builder.and(andPredicates.toArray(new Predicate[andPredicates.size()]));
                        orPre = builder.or(orPredicates.toArray(new Predicate[orPredicates.size()]));
                        return query.where(andPre, orPre).getRestriction();
                    }

                    if (!andPredicates.isEmpty()) {
                        andPre = builder.and(andPredicates.toArray(new Predicate[andPredicates.size()]));
                        return query.where(andPre).getRestriction();
                    }
                    if (!orPredicates.isEmpty()) {
                        orPre = builder.or(orPredicates.toArray(new Predicate[orPredicates.size()]));
                        return query.where(orPre).getRestriction();
                    }
                    return query.getRestriction();
                }

                return builder.conjunction();
            }
        };
    }

    /**
     *
     * @param filter
     * @param builder
     * @param expression
     * @return
     */
    public Predicate buildPredicate(SearchFilter filter, CriteriaBuilder builder, Path expression) {
        switch (filter.operator) {
            case EQ:
                return builder.equal(expression, filter.value);
            case NE:
                return builder.notEqual(expression, filter.value);
            case LIKE:
                return builder.like(expression, "%" + filter.value + "%");
            case NOTLIKE:
                return builder.notLike(expression, "%" + filter.value + "%");
            case GT:
                return builder.greaterThan(expression, (Comparable) filter.value);
            case LT:
                return builder.lessThan(expression, (Comparable) filter.value);
            case GTE:
                return builder.greaterThanOrEqualTo(expression, (Comparable) filter.value);
            case LTE:
                return builder.lessThanOrEqualTo(expression, (Comparable) filter.value);
            case IN:
                String[] values = filter.value.toString().split(",");
                List<String> list = Arrays.asList(values);
                List<List<String>> listPage = ArrayUtil.split(list, 1000);
                List<Predicate> predicatesBak = new ArrayList<>();
                for (List<String> page : listPage) {
                    predicatesBak.add(builder.or(expression.in(page)));
                }
                return builder.or(predicatesBak.toArray(new Predicate[predicatesBak.size()]));
            case NOTIN:
                Object[] values1 = filter.value.toString().split(",");
                return builder.not(expression.in(values1));
            case ISNULL:
                return builder.isNull(expression);
            case ISNOTNULL:
                return builder.isNotNull(expression);
            default:
                return null;
        }
    }

    public HttpServletRequest getCurRequest() {
        return ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
    }

    protected PageBean convert(Page page, PageBean pageBean) {
        if(page == null){
            return pageBean;
        }
        List<ApprTask> list = page.getContent();
        if (CollectionUtils.isNotEmpty(list)) {
            pageBean.setList(list);
            //设置总的页数
            pageBean.setPageCnt(page.getTotalPages());
            //设置总的条数
            pageBean.setRecordCnt(new Long(page.getTotalElements()).intValue());
            pageBean.pageResult(list, pageBean.getRecordCnt(), pageBean.getMaxSize(), pageBean.getCurPage());
        }
        return pageBean;
    }

}
