package edu.ssafy.enjoytrip.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
public class SwaggerConfiguration {
	private final String version = "V1";
	private final String title = "Share Your Trip" + version;

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.OAS_30)
					.consumes(getConsumeContentTypes())
					.produces(getProduceContentTypes())
					.useDefaultResponseMessages(false)
					.select()
					.apis(RequestHandlerSelectors.basePackage("edu.ssafy.enjoytrip.controller"))
					.paths(regex("/enjoytrip/.*"))
					.build()
					.apiInfo(apiInfo());
	}
	private Set<String> getConsumeContentTypes() {
        Set<String> consumes = new HashSet<>();
        consumes.add("application/json;charset=UTF-8");
        consumes.add("application/x-www-form-urlencoded");
        return consumes;
    }

    private Set<String> getProduceContentTypes() {
        Set<String> produces = new HashSet<>();
        produces.add("application/json;charset=UTF-8");
        return produces;
    }

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title(title)
				.description("<h3>Share Your Trip API Reference for Developers</h3>Share Your Trip API<br>")
				.contact(new Contact("SSAFY", "https://edu.ssafy.com", "ssafy@ssafy.com"))
				.license("SSAFY License")
				.licenseUrl("https://www.ssafy.com/ksp/jsp/swp/etc/swpPrivacy.jsp")
				.version("1.0").build();
	}
	
}
