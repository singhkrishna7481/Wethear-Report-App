package com.kr.model;

import java.util.List;

import lombok.Data;

@Data	
public class WeatherResponseModel {
	
	private String name;
	private Sys sys;
	private List<Weather> weather;
	private Main main;
	private Wind wind;
	
	@Data
	public static class Sys
	{
		private String country;
		private long sunrise;
		private long sunset;
	}
	
	@Data
	public static class Weather
	{
		private int id;
		private String description;
	}
	
	@Data
	public static class Main
	{
		private double temp;
		private double feels_like;
		private double temp_min	;
		private double temp_max;
		private int humidity;
		private int pressure	;
	}
	
	@Data
	public static class Wind
	{
		private double speed;
	}

}
