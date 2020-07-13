package com.danielqueiroz.fooddelivery.core.openapi;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.parsing.Location;
import org.springframework.beans.factory.parsing.ParseState;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.danielqueiroz.fooddelivery.api.exceptionhandler.ProblemMessage;
import com.danielqueiroz.fooddelivery.api.model.CozinhaDTO;
import com.danielqueiroz.fooddelivery.api.model.PedidoResumoDTO;
import com.danielqueiroz.fooddelivery.api.openapi.model.CozinhasModelOpenApi;
import com.danielqueiroz.fooddelivery.api.openapi.model.PageableModelOpenApi;
import com.danielqueiroz.fooddelivery.api.openapi.model.PedidosModelOpenApi;
import com.fasterxml.classmate.TypeResolver;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer {

	@Bean
	public Docket apiDocket() {
		var typeResolver = new TypeResolver();
		
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
					.apis(RequestHandlerSelectors.basePackage("com.danielqueiroz.fooddelivery.api"))
					.paths(PathSelectors.any())
				.build()
				.useDefaultResponseMessages(false)
					.globalResponseMessage(RequestMethod.GET, globalResponseMessages())
					.globalResponseMessage(RequestMethod.POST, globalPostPutResponseMessages())
					.globalResponseMessage(RequestMethod.PUT, globalPostPutResponseMessages())
					.globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages()).apiInfo(apiInfo())
				.additionalModels(typeResolver.resolve(ProblemMessage.class))
				.ignoredParameterTypes(ServletWebRequest.class, URL.class, URI.class, 
						Resource.class, File.class, Throwable.class, StackTraceElement.class,
						Problem.class, ParseState.class, Location.class, InputStream.class)
				.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
				.alternateTypeRules(AlternateTypeRules.newRule(
							typeResolver.resolve(Page.class, CozinhaDTO.class), 
							CozinhasModelOpenApi.class),
						AlternateTypeRules.newRule(
								typeResolver.resolve(Page.class, PedidoResumoDTO.class), 
								PedidosModelOpenApi.class))
				.tags(new Tag("Cidades", "Gerencia cidades"),
				        new Tag("Grupos", "Gerencia os grupos de usuários"),
				        new Tag("Cozinhas", "Gerencia as cozinhas"),
				        new Tag("Formas de pagamento", "Gerencia as formas de pagamento"),
				        new Tag("Pedidos", "Gerencia os pedidos"),
				        new Tag("Restaurantes", "Gerencia os restaurantes"),
				        new Tag("Estados", "Gerencia os estados"),
				        new Tag("Produtos", "Gerencia os produtos de restaurantes"),
				        new Tag("Usuários", "Gerencia os usuários"),
				        new Tag("Estatísticas", "Estatísticas da FoodDelivery"),
				        new Tag("Permissões", "Gerencia as permissões"));
	}

	private List<ResponseMessage> globalResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
						.responseModel(new ModelRef("Problema"))
						.message("Erro interno do servidor").build(),
				new ResponseMessageBuilder().code(HttpStatus.NOT_ACCEPTABLE.value())
						.message("Recurso não possui representação que pode ser aceita por consumidor").build()

		);
	}

	private List<ResponseMessage> globalPostPutResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder().code(HttpStatus.BAD_REQUEST.value())
						.message("Requisição inválida (erro do cliente)")
						.responseModel(new ModelRef("Problema"))
						.build(),
				new ResponseMessageBuilder()
						.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
						.message("Erro interno no servidor")
						.responseModel(new ModelRef("Problema"))
						.build(),
				new ResponseMessageBuilder().code(HttpStatus.NOT_ACCEPTABLE.value())
						.message("Recurso não possui representação que poderia ser aceita pelo consumidor").build(),
				new ResponseMessageBuilder().code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
						.message("Requisição recusada porque o corpo está em um formato não suportado").build());
	}

	private List<ResponseMessage> globalDeleteResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder().code(HttpStatus.BAD_REQUEST.value())
						.responseModel(new ModelRef("Problema"))
						.message("Requisição inválida (erro do cliente)").build(),
				new ResponseMessageBuilder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
						.responseModel(new ModelRef("Problema"))
						.message("Erro interno no servidor").build());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Food Delivery API").description("API aberta para clientes e restaurantes")
				.version("1")
				.contact(new Contact("Daniel Queiroz", "http://danielqueiroz.dev", "danielqueirozak77@gmail.com"))
				.build();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

}
