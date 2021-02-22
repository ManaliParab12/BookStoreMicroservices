package com.bridgelabz.onlinebookservice.zuulservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.bridgelabz.onlinebookservice.zuulservice.filter.ErrorFilter;
import com.bridgelabz.onlinebookservice.zuulservice.filter.PostFilter;
import com.bridgelabz.onlinebookservice.zuulservice.filter.PreFilter;
import com.bridgelabz.onlinebookservice.zuulservice.filter.RouteFilter;

@EnableZuulProxy
@SpringBootApplication
public class ZuulServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulServiceApplication.class, args);
	}
	
	@Bean
	public PreFilter preFilter() {
		return new PreFilter();
	}

	@Bean
	public PostFilter postFilter() {
		return new PostFilter();
	}

	@Bean
	public ErrorFilter errorFilter() {
		return new ErrorFilter();
	}

	@Bean
	public RouteFilter routeFilter() {
		return new RouteFilter();
	}

}
