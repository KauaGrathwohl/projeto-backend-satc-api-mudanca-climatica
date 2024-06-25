package com.satc.projeto.mudancaclimatica.controllers;

import com.satc.projeto.mudancaclimatica.dto.RequestWeatherInfoDto;
import com.satc.projeto.mudancaclimatica.feign.AirQualityServiceClient;
import com.satc.projeto.mudancaclimatica.models.WeatherData;
import com.satc.projeto.mudancaclimatica.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private AirQualityServiceClient airQualityServiceClient;

    @Autowired
    private WeatherRepository weatherRepository;

    @PostMapping
    public void getWeatherChanges(@RequestBody RequestWeatherInfoDto requestWeatherInfoDto) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("latitude", requestWeatherInfoDto.getLatitude());
        parameters.put("longitude", requestWeatherInfoDto.getLongitude());
        parameters.put("hourly", "carbon_monoxide,ozone,uv_index");
        parameters.put("start_date", requestWeatherInfoDto.getStartDate().toString());
        parameters.put("end_date", requestWeatherInfoDto.getEndDate().toString());
        LinkedHashMap response = airQualityServiceClient.getAirQuality(parameters);

        List<LocalDateTime> time = ((ArrayList<String>) ((LinkedHashMap) response.get("hourly")).get("time")).stream().map(LocalDateTime::parse).toList();
        List<Double> carbonMonoxide = ((ArrayList<Double>) ((LinkedHashMap) response.get("hourly")).get("carbon_monoxide")).stream().toList();
        List<Double> ozone = ((ArrayList<Double>) ((LinkedHashMap) response.get("hourly")).get("ozone")).stream().toList();
        List<Double> uvIndex = ((ArrayList<Double>) ((LinkedHashMap) response.get("hourly")).get("uv_index")).stream().toList();

        Map<LocalDateTime, Map<String, Object>> data = new HashMap<>();
        Map<String, Object> subFieldsData = new HashMap<>();
        IntStream.range(0, time.size())
                        .forEach(idx -> {
                            subFieldsData.put("carbon_monoxide", carbonMonoxide.get(idx));
                            subFieldsData.put("ozone", ozone.get(idx));
                            subFieldsData.put("uv_index", uvIndex.get(idx));
                            data.put(time.get(idx), subFieldsData);
                        });
        List<WeatherData> mongoData = new ArrayList<>();

        data.forEach((key, value) -> {
            WeatherData weatherData = new WeatherData(requestWeatherInfoDto.getLongitude(),
                    requestWeatherInfoDto.getLatitude(),
                    key,
                    (Double) value.get("carbon_monoxide"),
                    (Double) value.get("ozone"),
                    (Double) value.get("uv_index"));
            mongoData.add(weatherData);
        });

        weatherRepository.saveAll(mongoData);
    }

}
