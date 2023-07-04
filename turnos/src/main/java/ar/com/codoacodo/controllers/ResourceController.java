package ar.com.codoacodo.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.codoacodo.domain.dto.reqres.ListResource;
import ar.com.codoacodo.services.feign.FeignResourceService;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import lombok.RequiredArgsConstructor;
 

@RestController
@RequestMapping("/resource")
@RequiredArgsConstructor
public class ResourceController {

	@Value(value = "${ENDPOINT_REQ_RES}")
	private String apiEndpoint;

	@GetMapping()
	public ResponseEntity<ListResource> findAll() {

		/*
		RestTemplate restTemplate = new RestTemplate();
		
		//String fooResourceUrl = "http://localhost:8080/spring-rest/foos";
		
		ResponseEntity<ListResource> response = restTemplate.getForEntity(apiEndpoint, ListResource.class);
		*/
		
		FeignResourceService response = Feign.builder()
				  .client(new OkHttpClient())
				  .encoder(new GsonEncoder())
				  .decoder(new GsonDecoder())
				  .logger(new Slf4jLogger(FeignResourceService.class))
				  .logLevel(Logger.Level.FULL)
				  .target(FeignResourceService.class, apiEndpoint);

		// http status code=200 */
		return ResponseEntity.ok(response.findAll());
	}

}
