package com.mohammad.Config;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	
	public static final String AUTHORIZATION_HEADER="Authorization";
	
	private ApiKey  apiKeys()
	{
		return new ApiKey("JWT",AUTHORIZATION_HEADER,"header");
	}
	
	// making securityContext method below here!
	
	private List<SecurityContext> securityContext(){
		
		return Arrays.asList(SecurityContext.builder().securityReferences(srf()).build());
	}
	
	private List<SecurityReference> srf(){
		
		AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");
		return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[] {scope}));
	}
	
	@Bean
	public Docket api() {
	
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(getInfo())
				.securityContexts(securityContext())
				.securitySchemes(Arrays.asList(apiKeys()))
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo getInfo() {
		
		return new ApiInfo("Blogging_Application ","This project is developed by Mohammad_Ahtisham","1.0","Terms of service",
				new Contact("Mohammad","https://ahtishamkhan5678.github.io/MyPortfolio-main/","mohammadahtishamofficial@gmail.com"),"License Of API","API license URL",
				Collections.emptyList());
	}

}
