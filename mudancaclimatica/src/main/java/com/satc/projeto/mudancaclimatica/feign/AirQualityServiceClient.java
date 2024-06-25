package com.satc.projeto.mudancaclimatica.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.LinkedHashMap;
import java.util.Map;

@FeignClient(name = "AirQualityServiceClient", url = "https://air-quality-api.open-meteo.com/v1/air-quality")
public interface AirQualityServiceClient {

    @RequestMapping(method = RequestMethod.GET, value = "/{parameters}")
    LinkedHashMap getAirQuality(@SpringQueryMap Map<String, String> parameters);
}
