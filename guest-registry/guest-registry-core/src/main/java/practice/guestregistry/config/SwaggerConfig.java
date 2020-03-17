package practice.guestregistry.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("practice.guestregistry.api"))
//                .paths(PathSelectors.ant("/api/worker"))
                .paths(PathSelectors.any())
                .build()
//                .pathMapping("/")
                .useDefaultResponseMessages(false)
                .apiInfo(metaData())
                ;
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Spring boot guest registry REST API")
                .description("Spring boot guest registry REST API\nfor managing guest cards")
                .version("1.0.0")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
                .contact(new Contact("Dainius Gaižutis", null, "gaizutis.dainius@gmail.com"))
                .contact(new Contact("Tomas Kiziela", null, "tomas.kiziela@gmail.com"))
                .build()
                ;
    }
}

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import javax.servlet.ServletContext;
//
//import static springfox.documentation.spring.web.paths.Paths.removeAdjacentForwardSlashes;
//import static springfox.documentation.spring.web.paths.RelativePathProvider.ROOT;
//
////import springfox.documentation.swagger.web.UiConfigurationBuilder;
//
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//    @Bean
//    public Docket api(ServletContext servletContext) {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .pathProvider(new CustomPathProvider(servletContext))
//                .useDefaultResponseMessages(false)
//                .select()
//                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
//                .paths(PathSelectors.any())
//                .build();
////                .tags(new Tag("Books", "These endpoints are used to manage the books details of the library.", 1)
////                        , new Tag("Users", "These endpoints are used to manage the user details of the library.", 2));
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("Library API")
//                .description("This is the mock Library API can be used to practise the swagger documentation")
//                .version("1.0.0")
////              .contact(new Contact("Sivaram", "github", "cvaram96@gmail.com"))
//                .build();
//    }
//
//    private static class CustomPathProvider extends AbstractPathProvider {
//        ServletContext servletContext;
//        String host = "localhost:8081";
//
//        CustomPathProvider(ServletContext servletContext) {
//            this.servletContext = servletContext;
//        }
//
//        @Override
//        protected String applicationPath() {
//            return host;
////            return isNullOrEmpty(servletContext.getContextPath()) ? ROOT : servletContext.getContextPath();
//        }
//
//        @Override
//        protected String getDocumentationPath() {
//            return "";
//        }
//
//        @Override
//        public String getOperationPath(String operationPath) {
//            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath("/");
//            return this.getOperationPathPrefix(operationPath) +
//                    removeAdjacentForwardSlashes(uriComponentsBuilder.path(operationPath).build().toString());
//        }
//
//        private String getOperationPathPrefix(String path) {
//            switch (path) {
//                case "/card":
//                case "/card/{id}":
//                    return "card";
//                default:
//                    return "worker";
//            }
//        }
//    }
//
////    @Bean
////    UiConfiguration uiConfig() {
////        return UiConfigurationBuilder.builder()
////                .defaultModelsExpandDepth(-1)
////                .build();
////    }
////
//}