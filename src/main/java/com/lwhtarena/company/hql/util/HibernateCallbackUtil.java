package com.lwhtarena.company.hql.util;

import com.lwhtarena.company.hql.entities.HibernateCallbackUtilVo;
import com.lwhtarena.company.hql.entities.Rs;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;

import java.util.List;

/**
 * @Author：liwh
 * @Description:
 * @Date 16:41 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class HibernateCallbackUtil {

    public static Rs getRs(HibernateTemplate hibernateTemplate, String hql, int firstPlace, int page, int pagesize) {
        long originalCount = (Long)hibernateTemplate.find("select count(*) " + hql, new Object[0]).get(0);
        HibernateCallbackUtilVo hcuv = new HibernateCallbackUtilVo();
        hcuv.setHql(hql);
        hcuv.setHibernateTemplate(hibernateTemplate);
        hcuv.setPageSize(pagesize);
        hcuv.setOriginalCount(originalCount);
        hcuv.setPage(page);
        hcuv.setFirstPlace(firstPlace);
        Rs rs = exeRs(hcuv);
        return rs;
    }

    public static Rs exeRs(HibernateCallbackUtilVo hcuv) {
        int page = hcuv.getPage();
        final int pageSize = hcuv.getPageSize();
        final int firstPlace = hcuv.getFirstPlace();
        final String hql = hcuv.getHql();
        long originalCount = hcuv.getOriginalCount();
        Rs rs;
        if (firstPlace < 0) {
            rs = RsUtil.init(page, pageSize, originalCount);
        } else {
            rs = RsUtil.init(page, pageSize, originalCount - (long)firstPlace);
        }

        final int pageE = rs.getPage();
        HibernateTemplate hibernateTemplate = hcuv.getHibernateTemplate();
        HibernateCallback<Object> hc = new HibernateCallback<Object>() {
            public Object doInHibernate(Session session) throws HibernateException {
                int n = pageSize;
                if (n == 0) {
                    n = 10;
                }

                Query<?> query = session.createQuery(hql).setCacheable(true);
                if (firstPlace == 0) {
                    query.setFirstResult((pageE - 1) * pageSize);
                } else {
                    query.setFirstResult(firstPlace - 1);
                }

                query.setMaxResults(n);
                List<?> list = query.list();
                return list;
            }
        };
        rs.setList((List)hibernateTemplate.execute(hc));
        return rs;
    }
}
