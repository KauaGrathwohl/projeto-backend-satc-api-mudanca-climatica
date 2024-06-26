package com.satc.projeto.mudancaclimatica.services;

import com.satc.projeto.mudancaclimatica.dto.WeatherDataDto;
import com.satc.projeto.mudancaclimatica.params.RequestWeatherInfoParam;
import com.satc.projeto.mudancaclimatica.feign.AirQualityServiceClient;
import com.satc.projeto.mudancaclimatica.models.WeatherData;
import com.satc.projeto.mudancaclimatica.repository.WeatherRepository;
import com.satc.projeto.mudancaclimatica.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;

@Service
public class WeatherService {

    @Autowired
    private AirQualityServiceClient airQualityServiceClient;

    @Autowired
    private WeatherRepository weatherRepository;

    public List<WeatherData> getWeatherChanges(RequestWeatherInfoParam requestWeatherInfoParam) {
        List<WeatherData> savedData = weatherRepository.findByLatitudeAndLongitudeAndRecordDateBetween(requestWeatherInfoParam.getLatitude(), requestWeatherInfoParam.getLongitude(), requestWeatherInfoParam.getStartDate(), requestWeatherInfoParam.getEndDate());

        if (!savedData.isEmpty())
            return savedData;

        return proccessThirdClientData(requestWeatherInfoParam);
    }

    public Page<WeatherData> getWeatherChangesPage(RequestWeatherInfoParam requestWeatherInfoDto,
                                                   int offset,
                                                   int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<WeatherData> savedData = weatherRepository.findByLatitudeAndLongitudeAndRecordDateBetween(requestWeatherInfoDto.getLatitude(),
                requestWeatherInfoDto.getLongitude(),
                requestWeatherInfoDto.getStartDate(),
                requestWeatherInfoDto.getEndDate(), pageable);

        if (!savedData.isEmpty())
            return savedData;

        List<WeatherData> data = proccessThirdClientData(requestWeatherInfoDto);
        pageable = PageRequest.of(offset, limit);
        return PageUtil.getPage(data, pageable);
    }

    private List<WeatherData> proccessThirdClientData(RequestWeatherInfoParam requestWeatherInfoParam) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("latitude", requestWeatherInfoParam.getLatitude());
        parameters.put("longitude", requestWeatherInfoParam.getLongitude());
        parameters.put("hourly", "carbon_monoxide,ozone,uv_index");
        parameters.put("start_date", requestWeatherInfoParam.getStartDate().toString());
        parameters.put("end_date", requestWeatherInfoParam.getEndDate().toString());
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
            WeatherData weatherData = new WeatherData(requestWeatherInfoParam.getLongitude(),
                    requestWeatherInfoParam.getLatitude(),
                    key,
                    (Double) value.get("carbon_monoxide"),
                    (Double) value.get("ozone"),
                    (Double) value.get("uv_index"));
            mongoData.add(weatherData);
        });
        return weatherRepository.saveAll(mongoData);
    }

    public WeatherData createWeatherRecord(WeatherDataDto weatherDataDto) {
        WeatherData weatherData = new WeatherData(weatherDataDto.getLongitude(),
                weatherDataDto.getLatitude(),
                LocalDateTime.now(),
                weatherDataDto.getCarbonMonoxideMean(),
                weatherDataDto.getOzone(),
                weatherDataDto.getUvIndex());
        return weatherRepository.save(weatherData);
    }

    public WeatherData updateWeatherRecord(String id, WeatherDataDto weatherDataDto) {
        WeatherData weatherData = new WeatherData(id, weatherDataDto.getLongitude(),
                weatherDataDto.getLatitude(),
                LocalDateTime.now(),
                weatherDataDto.getCarbonMonoxideMean(),
                weatherDataDto.getOzone(),
                weatherDataDto.getUvIndex());
        return weatherRepository.save(weatherData);
    }

    public void deleteWeatherRecord(String id) {
         weatherRepository.deleteById(id);
    }


}
