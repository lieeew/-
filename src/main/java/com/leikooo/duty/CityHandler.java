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
public class CityHandler extends AbstractBusinessHandler {
    @Override
    public List<BusinessLaunch> processHandler(List<BusinessLaunch> launchList, String targetCity, String targetSex, String targetProduct) {
        if (launchList.isEmpty()) {
            return launchList;
        }
        launchList = launchList.stream().filter(launch -> {
            String city = launch.getTargetCity();
            // 这里就算没有写城市，也默认是匹配的，因为不可能不给你发广告
            if (StringUtils.isBlank(city)) {
                return true;
            }
            List<String> cityList = Arrays.asList(city.split(","));
            return cityList.contains(targetCity);
        }).collect(Collectors.toList());
        if (super.hasNextHandler()) {
            processHandler(launchList, targetCity, targetSex, targetProduct);
        }
        return launchList;
    }
}
