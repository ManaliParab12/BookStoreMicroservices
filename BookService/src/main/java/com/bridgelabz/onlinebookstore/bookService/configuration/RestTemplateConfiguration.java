package com.bridgelabz.onlinebookstore.bookService.configuration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.bridgelabz.onlinebookstore.bookService.utility.ResponseDTO;


@Component
public class RestTemplateConfiguration {

	@Autowired
	private RestTemplate userTemplate;
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
//	List<String> service = discoveryClient.getServices();
	List<ServiceInstance> instances;
	ServiceInstance serviceInstance;
	
	String baseUrl = "";
	
	String Url = "";
	
	public RestTemplateConfiguration () {
//		System.out.println("RestTemplate Test" +this.service); 
//		this.instances = discoveryClient.getInstances("UserServiceApplication");
//		this.serviceInstance = instances.get(0);
//		this.baseUrl = serviceInstance.getUri().toString();
//		System.out.println("baseUrl" +baseUrl);
//		this.Url = baseUrl+"/user";
		System.out.println("Description" +discoveryClient.description());
	}
	
	
	public void demo() {
		//userTemplate = new RestTemplate(); 
		HttpHeaders httpHeaders = new HttpHeaders();
		String message = userTemplate.getForEntity(Url+"/hello", String.class).getBody();
//		String message = userTemplate.getForEntity("http://localhost:8081/user/hello", String.class).getBody();
		System.out.println("Message " +message);		
	}
	
	public ResponseDTO validateToken(String token) {
		userTemplate = new RestTemplate(); 
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Token", token);
		HttpEntity httpEntity = new HttpEntity(httpHeaders);
		return userTemplate.exchange("http://localhost:8081/user/validate", HttpMethod.GET, httpEntity, ResponseDTO.class).getBody();
	}
}
