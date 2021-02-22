package com.bridgelabz.onlinebookstore.discoveryservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiscoveryServiceController {
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@GetMapping("/service")
	public int getServiceList(){
		return discoveryClient.getInstances("UserServiceApplication").get(0).getPort();
	}

}
