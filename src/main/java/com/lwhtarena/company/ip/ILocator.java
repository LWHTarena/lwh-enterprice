package com.lwhtarena.company.ip;

import com.lwhtarena.company.ip.entities.LocationInfo;

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
public interface ILocator {

    LocationInfo find(String var1);

    LocationInfo find(byte[] var1);

    LocationInfo find(int var1);
}
