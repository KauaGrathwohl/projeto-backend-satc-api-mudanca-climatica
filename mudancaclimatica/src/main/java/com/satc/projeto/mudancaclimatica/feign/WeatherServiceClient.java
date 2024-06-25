package com.satc.projeto.mudancaclimatica.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "WeatherServiceClient", url = "https://api.open-meteo.com/v1/forecast")
public interface WeatherServiceClient {
}