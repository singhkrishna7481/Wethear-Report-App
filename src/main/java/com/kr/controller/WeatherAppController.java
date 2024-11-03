package com.kr.controller;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kr.model.WeatherResponseModel;

import lombok.Data;

@Controller
public class WeatherAppController {

	@Value("${api.key}")
	private String apiKey;

	@GetMapping("/")
	public String home() {
		return "index";
	}

	@GetMapping("/getWeather")
	public String getWeather(@RequestParam String city, RedirectAttributes attr) {
		String uri = "http://api.openweathermap.org/data/2.5/weather?q="+city+"&APPID="+apiKey+"&units=metric";
		RestTemplate template = new RestTemplate();
		System.out.println(uri);
		WeatherResponseModel weatherReport = template.getForObject(uri, WeatherResponseModel.class);
		System.out.println(weatherReport);
		System.out.println(weatherReport.getName());
		System.out.println(weatherReport.getMain().getTemp());
		System.out.println(weatherReport.getMain().getHumidity());
		System.out.println(weatherReport.getSys().getCountry());
		System.out.println(weatherReport.getWeather().get(0).getDescription());
		System.out.println(weatherReport.getWind().getSpeed());
		
		attr.addFlashAttribute("isFound","true");
		attr.addFlashAttribute("name",weatherReport.getName());
		attr.addFlashAttribute("min_temp",weatherReport.getMain().getTemp_min());
		attr.addFlashAttribute("feels_like",weatherReport.getMain().getFeels_like());
		attr.addFlashAttribute("max_temp",weatherReport.getMain().getTemp_max());
		attr.addFlashAttribute("humid",weatherReport.getMain().getHumidity());
		attr.addFlashAttribute("pressure",weatherReport.getMain().getPressure());
		attr.addFlashAttribute("country",weatherReport.getSys().getCountry());
		attr.addFlashAttribute("info",weatherReport.getWeather().get(0).getDescription());
		attr.addFlashAttribute("speed",weatherReport.getWind().getSpeed());
		
		LocalDateTime sunrise = Instant.ofEpochSecond(weatherReport.getSys().getSunrise()).atZone(ZoneId.systemDefault()).toLocalDateTime();
		LocalDateTime sunset = Instant.ofEpochSecond(weatherReport.getSys().getSunset()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("hh:mm:ss a");
		
		attr.addFlashAttribute("sunrise",formatter.format(sunrise));
		attr.addFlashAttribute("sunset",formatter.format(sunset));
		
		return "redirect:./";
	}
	
	@PostMapping("/location")
	  public void handleLocationRequest(@RequestBody LocationData locationData) {
		System.out.println(locationData);
		System.out.println("invoked");
	    // Process location data (e.g., convert coordinates to a specific format)
	    // Store location data in a suitable data structure (e.g., a Java object or a database)
	  }
	  @Data
	  public static class LocationData {
	    private double latitude;
	    private double longitude;
	    // getters and setters
	  }
}
