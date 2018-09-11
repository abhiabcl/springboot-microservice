package com.flavor.order;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.Data;

@EnableFeignClients
@EnableCircuitBreaker
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class EdgeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdgeServiceApplication.class, args);
	}
}

@Data //DTO : Data transfer object
class Item {
	
	Item(){
		
	}
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

@FeignClient("item-catalog-service") // Communicate with item-catalog service using feign-webservice client
interface ItemClient{
	@GetMapping("/items")
	Resources<Item> readItems();
}


@RestController
class GoodItemApiAdapterRestController{
	private final ItemClient itemClient;
	
	public GoodItemApiAdapterRestController(ItemClient itemClient) {
		this.itemClient = itemClient;
	}
	
	public Collection<Item> fallback(){
		return new ArrayList<>();
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	@GetMapping("/top-brands")
	@CrossOrigin(origins = "*")
	public Collection<Item> goodItem(){
		return itemClient.readItems()
				.getContent()
				.stream()
				//.filter(this::isGreat)
				.collect(Collectors.toList());
	}
	
	private boolean isGreat(Item item) {
		return item.getName().equals("Nike")&&
				item.getName().equals("Addidas")&&
				item.getName().equals("Reebok");
	}
}


