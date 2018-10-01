package com.lwhtarena.company.sys.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：liwh
 * @Description:
 * @Date 23:37 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class ListUtil {

    public static List<Object> paging(List<Object> all, int page, int pageSize) {
        List<Object> currpage = new ArrayList();
        int currIdx = page > 1 ? (page - 1) * pageSize : 0;

        for(int i = 0; i < pageSize && i < all.size() - currIdx; ++i) {
            Object list = all.get(currIdx + i);
            currpage.add(list);
        }

        return currpage;
    }
}
