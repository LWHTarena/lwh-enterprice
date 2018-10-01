package com.lwhtarena.company.rank.util;

import com.lwhtarena.company.rank.entities.RankUnit;
import com.lwhtarena.company.rank.obj.RankUnitPlace;
import org.springframework.orm.hibernate5.HibernateTemplate;

import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * <h2>简述：</h2>
 * <ol></ol>
 * <h2>功能描述：</h2>
 * <ol></ol>
 * </p>
 *
 * @Author: liwh
 * @Date :
 * @Version: 版本
 */
public class RankUnitUtil {

    public static RankUnit put(HibernateTemplate ht, RankUnit ru, boolean newrec) {
        String entitiesClass = ru.getClass().getName();
        RankUnit parentCurr = ru.getParent();
        if (parentCurr != null) {
            parentCurr = (RankUnit)ht.get(entitiesClass, parentCurr.getId());
        }

        String hql;
        String countHql;
        String displayOrder;
        if (parentCurr != null) {
            displayOrder = parentCurr.getOrderStr();
            countHql = "select count(*) from " + entitiesClass + " a where a.parent.id=" + parentCurr.getId();
            int parentRightValue = parentCurr.getFootRight();
            hql = "update " + entitiesClass + " a set a.footLeft=a.footLeft+2 where a.footLeft>" + parentCurr.getFootRight();
            ht.bulkUpdate(hql, new Object[0]);
            hql = "update " + entitiesClass + " a set a.footRight=a.footRight+2 where a.footRight>=" + parentCurr.getFootRight();
            ht.bulkUpdate(hql, new Object[0]);
            ru.setFootLeft(parentRightValue);
            ru.setFootRight(parentRightValue + 1);
        } else {
            displayOrder = "o";
            countHql = "select count(*) from " + entitiesClass + " a where a.parent is null";
            hql = "select max(a.footRight) from " + entitiesClass + " a";
            Integer maxRight = (Integer)ht.find(hql, new Object[0]).get(0);
            if (maxRight == null) {
                ru.setFootLeft(1);
                ru.setFootRight(2);
            } else {
                ru.setFootLeft(maxRight + 1);
                ru.setFootRight(maxRight + 2);
            }
        }

        long count = (Long)ht.find(countHql, new Object[0]).get(0);
        ++count;
        if (count <= 9L) {
            displayOrder = displayOrder + "00" + count;
        } else if (count > 9L && count <= 99L) {
            displayOrder = displayOrder + "0" + count;
        } else {
            displayOrder = displayOrder + count;
        }

        ru.setOrderStr(displayOrder);
        ht.save(ru);
        return ru;
    }

    public static RankUnit add(HibernateTemplate ht, RankUnit ru) {
        ru = put(ht, ru, true);
        return ru;
    }

    public static int del(HibernateTemplate ht, RankUnit ru) {
        String entitiesClass = ru.getClass().getName();
        long id = ru.getId();
        ru = (RankUnit)ht.get(entitiesClass, id);
        RankUnit parent = ru.getParent();
        if (parent != null) {
            parent = (RankUnit)ht.get(entitiesClass, parent.getId());
        }

        String hql = "select count(*) from " + entitiesClass + " a where a.parent.id=" + id;
        if ((Long)ht.find(hql, new Object[0]).get(0) > 0L) {
            ht.clear();
            return -2;
        } else {
            ht.delete(ru);
            hql = "update " + entitiesClass + " a set a.footRight=a.footRight-2 where a.footRight>0 and a.footRight>" + ru.getFootRight();
            ht.bulkUpdate(hql, new Object[0]);
            hql = "update " + entitiesClass + " a set a.footLeft=a.footLeft-2 where a.footLeft>0 and a.footLeft>" + ru.getFootRight();
            ht.bulkUpdate(hql, new Object[0]);
            RankUnit ru2 = (RankUnit)ht.get(entitiesClass, id);
            if (ru2 != null) {
                ht.clear();
                return -1;
            } else {
                if (parent == null) {
                    parent = new RankUnit();
                    parent.setId(0L);
                }

                parent.setEntitiesName(entitiesClass);
                sortOrderStr(ht, parent);
                return 0;
            }
        }
    }

    public static RankUnit modifyFoot(HibernateTemplate ht, RankUnit ru) {
        String entitiesClass = ru.getClass().getName();
        RankUnit parentCurr = ru.getParent();
        parentCurr = (RankUnit)ht.get(entitiesClass, parentCurr.getId());
        RankUnit ruTrue = (RankUnit)ht.get(entitiesClass, ru.getId());
        int trueLeft = ruTrue.getFootLeft();
        int trueRight = ruTrue.getFootRight();
        int footSpanAll = trueRight - trueLeft + 1;
        String hql = "update " + entitiesClass + " a set a.footRight= 1000000000 + a.footRight,a.footLeft= 1000000000 + a.footLeft where a.footLeft>=" + trueLeft + " and a.footRight <=" + trueRight;
        ht.bulkUpdate(hql, new Object[0]);
        hql = "update " + entitiesClass + " a set a.footLeft= a.footLeft-" + footSpanAll + " where a.footLeft>" + trueRight + " and a.footLeft<1000000000 ";
        ht.bulkUpdate(hql, new Object[0]);
        hql = "update " + entitiesClass + " a set a.footRight= a.footRight-" + footSpanAll + " where a.footRight>" + trueRight + " and a.footLeft<1000000000 ";
        ht.bulkUpdate(hql, new Object[0]);
        ht.clear();
        long currParentID;
        if (parentCurr == null) {
            currParentID = 0L;
        } else {
            currParentID = parentCurr.getId();
        }

        parentCurr = (RankUnit)ht.get(entitiesClass, currParentID);
        int tagetParentFootLeft;
        if (parentCurr == null) {
            tagetParentFootLeft = 0;
        } else {
            tagetParentFootLeft = parentCurr.getFootLeft();
        }

        hql = "update " + entitiesClass + " a set a.footRight= a.footRight+" + footSpanAll + " where a.footRight>" + tagetParentFootLeft + " and a.footLeft<1000000000 ";
        ht.bulkUpdate(hql, new Object[0]);
        hql = "update " + entitiesClass + " a set a.footLeft= a.footLeft+" + footSpanAll + " where a.footLeft>" + tagetParentFootLeft + " and a.footLeft<1000000000 ";
        ht.bulkUpdate(hql, new Object[0]);
        hql = "update " + entitiesClass + " a set a.footRight= a.footRight-" + (trueLeft - 1) + ",a.footLeft= a.footLeft- " + (trueLeft - 1) + " where a.footLeft>1000000000 ";
        ht.bulkUpdate(hql, new Object[0]);
        ht.clear();
        hql = "update " + entitiesClass + " a set a.footRight = a.footRight+" + tagetParentFootLeft + ",a.footLeft= a.footLeft + " + tagetParentFootLeft + " where a.footLeft>1000000000 ";
        ht.bulkUpdate(hql, new Object[0]);
        hql = "update " + entitiesClass + " a set a.footRight = a.footRight - 1000000000,a.footLeft= a.footLeft - 1000000000 where a.footLeft>1000000000 ";
        ht.bulkUpdate(hql, new Object[0]);
        ht.clear();
        ruTrue = (RankUnit)ht.get(entitiesClass, ru.getId());
        ru.setFootLeft(ruTrue.getFootLeft());
        ru.setFootRight(ruTrue.getFootRight());
        ht.clear();
        ht.saveOrUpdate(ru);
        return ru;
    }

    private static String className(RankUnit ru) {
        if (ru == null) {
            ru = new RankUnit();
        }

        String entitiesClass = ru.getClass().getName();
        if (ru.getEntitiesName() != null && !ru.getEntitiesName().trim().equals("")) {
            entitiesClass = ru.getEntitiesName();
        }

        int extP = entitiesClass.indexOf("_$$_");
        if (extP != -1) {
            entitiesClass = entitiesClass.substring(0, extP);
        }

        return entitiesClass;
    }

    public static void sortOrderStr(HibernateTemplate ht, RankUnit parent) {
        String entitiesClass = className(parent);
        if (parent != null && parent.getId() - 0L == 0L) {
            parent = null;
        }

        String parentOrderStr;
        if (parent == null) {
            parentOrderStr = "o";
        } else {
            ht.clear();
            parent = (RankUnit)ht.get(entitiesClass, parent.getId());
            parentOrderStr = parent.getOrderStr();
        }

        String hql;
        int parentFootLeft;
        int parentFootRight;
        if (parent == null) {
            parentFootLeft = 0;
            hql = "select max(a.footRight) from " + entitiesClass + " a";
            if (ht.find(hql, new Object[0]).get(0) == null) {
                parentFootRight = 1;
            } else {
                parentFootRight = (Integer)ht.find(hql, new Object[0]).get(0) + 1;
            }
        } else {
            parentFootLeft = parent.getFootLeft();
            parentFootRight = parent.getFootRight();
        }

        if (parent == null) {
            hql = "from " + entitiesClass + " a where a.footLeft> 0 and a.footLeft>" + parentFootLeft + " and a.footRight<" + parentFootRight + " and a.parent is null order by length(a.orderStr) asc,a.orderStr asc";
        } else {
            hql = "from " + entitiesClass + " a where a.footLeft>0 and a.footLeft>" + parentFootLeft + " and a.footRight<" + parentFootRight + " and a.parent.id=" + parent.getId() + " order by length(a.orderStr) asc,a.orderStr asc";
        }

        List<RankUnit> list = (List<RankUnit>) ht.find(hql, new Object[0]);
        if (!list.isEmpty()) {
            Iterator<RankUnit> it = list.iterator();
            int step = 0;

            while(it.hasNext()) {
                ++step;
                String toOrderStr = "";
                RankUnit a = (RankUnit)it.next();
                if (step <= 9) {
                    toOrderStr = toOrderStr + "00" + step;
                } else if (step > 9 && step <= 99) {
                    toOrderStr = toOrderStr + "0" + step;
                } else {
                    toOrderStr = toOrderStr + step;
                }

                toOrderStr = parentOrderStr + toOrderStr;
                String orderOld = a.getOrderStr();
                a.setOrderStr(toOrderStr);
                ht.saveOrUpdate(a);
                ht.flush();
                hql = "update " + entitiesClass + " a set a.orderStr=replace(a.orderStr,'" + orderOld + "','" + toOrderStr + "') where a.footLeft>=" + a.getFootLeft() + " and a.footRight<=" + a.getFootRight();
                ht.bulkUpdate(hql, new Object[0]);
            }
        }

        ht.clear();
    }

    public static String newOrderStr(HibernateTemplate ht, RankUnit parent) {
        String entitiesClass = className(parent);
        String hql;
        int parentFootLeft;
        int parentFootRight;
        if (parent == null) {
            parentFootLeft = 0;
            hql = "select max(a.footRight) from " + entitiesClass + " a";
            if (ht.find(hql, new Object[0]).get(0) == null) {
                parentFootRight = 1;
            } else {
                parentFootRight = (Integer)ht.find(hql, new Object[0]).get(0) + 1;
            }
        } else {
            parentFootLeft = parent.getFootLeft();
            parentFootRight = parent.getFootRight();
        }

        if (parent == null) {
            hql = "from " + entitiesClass + " a where a.footLeft> 0 and a.footLeft>" + parentFootLeft + " and a.footRight<" + parentFootRight + " and a.parent is null order by length(a.orderStr) asc,a.orderStr asc";
        } else {
            hql = "from " + entitiesClass + " a where a.footLeft>0 and a.footLeft>" + parentFootLeft + " and a.footRight<" + parentFootRight + " and a.parent.id=" + parent.getId() + " order by length(a.orderStr) asc,a.orderStr asc";
        }

        List<RankUnit> list = (List<RankUnit>) ht.find(hql, new Object[0]);
        int count = list.size();
        String toOrderStr = "";
        ht.clear();
        if (parent != null) {
            parent = (RankUnit)ht.get(entitiesClass, parent.getId());
        }

        if (parent != null && parent.getOrderStr() != null && !parent.getOrderStr().trim().equals("")) {
            toOrderStr = parent.getOrderStr();
        } else {
            toOrderStr = "o";
        }

        if (count <= 9) {
            toOrderStr = toOrderStr + "00" + count;
        } else if (count > 9 && count <= 99) {
            toOrderStr = toOrderStr + "0" + count;
        } else {
            toOrderStr = toOrderStr + count;
        }

        return toOrderStr;
    }

    public static void chgOrder(HibernateTemplate ht, RankUnit curr, RankUnit parentTarget, RankUnit parentFrom) {
        String entitiesClass = className(curr);
        String toOrderStr = newOrderStr(ht, parentTarget);
        ht.clear();
        curr = (RankUnit)ht.get(entitiesClass, curr.getId());
        String hql = "update " + entitiesClass + " a set a.orderStr=replace(a.orderStr,'" + curr.getOrderStr() + "','" + toOrderStr + "') where a.footLeft>=" + curr.getFootLeft() + " and a.footRight<=" + curr.getFootRight();
        ht.bulkUpdate(hql, new Object[0]);
    }

    public static void modify(HibernateTemplate ht, RankUnit ru) {
        String entitiesClass = ru.getClass().getName();
        ht.clear();
        RankUnit ruDB = (RankUnit)ht.get(entitiesClass, ru.getId());
        ru.setOrderStr(ruDB.getOrderStr());
        long toParentID = ru.getParent().getId();
        RankUnit parent = (RankUnit)ht.get(entitiesClass, toParentID);
        RankUnit aTrue = (RankUnit)ht.get(entitiesClass, ru.getId());
        RankUnit parentOld = aTrue.getParent();
        long oldParentID;
        if (parentOld == null) {
            oldParentID = 0L;
        } else {
            oldParentID = parentOld.getId();
        }

        if (oldParentID - toParentID != 0L) {
            ru = modifyFoot(ht, ru);
            ruDB = (RankUnit)ht.get(entitiesClass, ru.getId());
            ru.setFootLeft(ruDB.getFootLeft());
            ru.setFootRight(ruDB.getFootRight());
            ru.setParent(parent);
            parent = (RankUnit)ht.get(entitiesClass, toParentID);
            ht.clear();
            ru.setParent(parent);
            ht.update(ru);
            ht.flush();
            ht.clear();
            ru = (RankUnit)ht.get(entitiesClass, ru.getId());
            chgOrder(ht, ru, parent, parentOld);
            parentOld = (RankUnit)ht.get(entitiesClass, oldParentID);
            sortOrderStr(ht, parentOld);
        }

    }

    public static RankUnitPlace placeInfInParent(HibernateTemplate ht, RankUnit ru) {
        RankUnitPlace place = new RankUnitPlace();
        String entitiesClass = className(ru);
        String hql;
        if (ru.getParent() != null) {
            hql = "from " + entitiesClass + " a where a.parent.id=" + ru.getParent().getId() + " order by a.orderStr asc";
        } else {
            hql = "from " + entitiesClass + " a where a.parent is null order by a.orderStr asc";
        }

        List<RankUnit> list = (List<RankUnit>) ht.find(hql, new Object[0]);
        int step = 0;
        long lastID = 0L;

        RankUnit r;
        for(Iterator var10 = list.iterator(); var10.hasNext(); lastID = r.getId()) {
            r = (RankUnit)var10.next();
            ++step;
            if (step == list.size()) {
                step = 0;
            }

            if (r.getId() - ru.getId() == 0L) {
                place.setLastID(lastID);
                place.setPlaceNumber(step);
            }

            if (ru.getId() - lastID == 0L) {
                place.setNextID(r.getId());
            }
        }

        return place;
    }

    public static void move(HibernateTemplate ht, RankUnit ru, RankUnitPlace rup, int offset) {
        if (ru != null && (offset == 1 || offset == -1)) {
            String entitiesClass = className(ru);
            if (rup == null) {
                rup = placeInfInParent(ht, ru);
            }

            RankUnit targetRu = null;
            if (rup.getLastID() > 0L && offset == -1) {
                targetRu = (RankUnit)ht.get(entitiesClass, rup.getLastID());
            }

            if (rup.getNextID() > 0L && offset == 1) {
                targetRu = (RankUnit)ht.get(entitiesClass, rup.getNextID());
            }

            if (targetRu != null) {
                String orderStrSource = ru.getOrderStr();
                String orderStrTarget = targetRu.getOrderStr();
                String hql = "update " + entitiesClass + " a set a.orderStr=replace(a.orderStr,'" + orderStrSource + "','v" + orderStrTarget + "')";
                ht.bulkUpdate(hql, new Object[0]);
                hql = "update " + entitiesClass + " a set a.orderStr=replace(a.orderStr,'vo','va')";
                ht.bulkUpdate(hql, new Object[0]);
                hql = "update " + entitiesClass + " a set a.orderStr=replace(a.orderStr,'" + orderStrTarget + "','" + orderStrSource + "')";
                ht.bulkUpdate(hql, new Object[0]);
                hql = "update " + entitiesClass + " a set a.orderStr=replace(a.orderStr,'va','o')";
                ht.bulkUpdate(hql, new Object[0]);
                ru.setOrderStr(orderStrTarget);
                ht.clear();
                ht.update(ru);
                ht.flush();
                targetRu.setOrderStr(orderStrSource);
                ht.clear();
                ht.update(targetRu);
                ht.flush();
            }
        }

    }
}
