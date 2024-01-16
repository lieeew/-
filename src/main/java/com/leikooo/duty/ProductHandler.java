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
public class ProductHandler extends AbstractBusinessHandler {
    @Override
    public List<BusinessLaunch> processHandler(List<BusinessLaunch> launchList, String targetCity, String targetSex, String targetProduct) {
        if (launchList.isEmpty()) {
            return launchList;
        }
        launchList = launchList.stream().filter(launch -> {
            String product = launch.getTargetProduct();
            if (StringUtils.isBlank(product)) {
                return true;
            }
            List<String> productList = Arrays.asList(product.split(","));
            return productList.contains(targetProduct);
        }).collect(Collectors.toList());
        if (super.hasNextHandler()) {
            processHandler(launchList, targetCity, targetSex, targetProduct);
        }
        return launchList;
    }
}
