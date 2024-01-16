package com.leikooo.duty;

import com.leikooo.pojo.BusinessLaunch;
import org.bouncycastle.LICENSE;

import java.util.List;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/16
 * @description
 */
public abstract class AbstractBusinessHandler {
    public AbstractBusinessHandler nextHandler;

    /**
     * 判断是否有下一个责任类
     *
     * @return 存在返回 true
     */
    public boolean hasNextHandler() {
        return this.nextHandler != null;
    }
    public abstract List<BusinessLaunch> processHandler(List<BusinessLaunch> launchList, String targetCity, String targetSex, String targetProduct);
}
