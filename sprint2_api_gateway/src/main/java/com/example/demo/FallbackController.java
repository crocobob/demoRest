package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class FallbackController {
	
	@RequestMapping("/customerFallback")
	Mono<String> customerServiceFallback() {
		return Mono.just("Customer Service is taking too long to respond or is down. Pls try after some time!!");
	}
	
	@RequestMapping("/userFallback")
	public Mono<String> userServiceFallback() {
		return Mono.just("Admin Service is taking too long to respond or is down. Pls try after some time!!");
	}
	
	@RequestMapping("/orderFallback")
	public Mono<String> orderFallback() {
		return Mono.just("Product Service is taking too long to respond or is down. Pls try after some time!!");
	}
	@RequestMapping("/sessionFallback")
	public Mono<String> sessionFallback() {
		return Mono.just("Admin Service is taking too long to respond or is down. Pls try after some time!!");
	}
	@RequestMapping("/productFallback")
	public Mono<String> productServiceFallback() {
		return Mono.just("Admin Service is taking too long to respond or is down. Pls try after some time!!");
	}
}
