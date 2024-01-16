package com.leikooo.duty;

import com.leikooo.pojo.BusinessLaunch;
import org.apache.commons.lang3.StringUtils;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/16
 * @description
 */
public class SexHandler extends AbstractBusinessHandler {
    @Override
    public List<BusinessLaunch> processHandler(List<BusinessLaunch> launchList, String targetCity, String targetSex, String targetProduct) {
        if (launchList.isEmpty()) {
            return launchList;
        }
        launchList = launchList.stream().filter(launch -> {
            String sex = launch.getTargetSex();
            if (StringUtils.isEmpty(sex)) {
                return true;
            }
            List<String> sexList = Arrays.asList(sex.split(","));
            return sexList.contains(targetSex);
        }).collect(Collectors.toList());
        // 如果还有下一个责任类，则继续进行筛选
        if (super.hasNextHandler()) {
            processHandler(launchList, targetCity, targetSex, targetProduct);
        }
        return launchList;
    }
}
