package mobi.puut.config;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import mobi.puut.services.utils.RestProviderBeanScanner;
import mobi.puut.services.utils.RestServiceBeanScanner;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.ext.RuntimeDelegate;
import java.util.List;

@Configuration
//@ComponentScan(AppConfig.SERVICE_PACKAGE)
public class AppConfig {

    public static final String BASE_PACKAGE = "mobi.puut";

    //
    public static final String SERVICE_PACKAGE = "mobi.puut.services";

    private static final String RESOURCES_PACKAGE = "mobi.puut.services";

    private static final String PROVIDER_PACKAGE = "mobi.puut.services.utils.provider";

    // public static final String API_BASE = "/api/*";
    public static final String API_BASE = "/*";

    @ApplicationPath("/")
    public class JaxRsApiApplication extends Application {
    }

    @Bean(destroyMethod = "shutdown")
    public SpringBus cxf() {
        return new SpringBus();
    }

    @Bean
    @DependsOn("cxf")
    public Server jaxRsServer(ApplicationContext appContext) {
        JAXRSServerFactoryBean factory = RuntimeDelegate.getInstance().createEndpoint(jaxRsApiApplication(), JAXRSServerFactoryBean.class);
        factory.setServiceBeans(restServiceList(appContext));
        factory.setAddress("/" + factory.getAddress());
        factory.setProviders(restProviderList(appContext, jsonProvider()));
        return factory.create();
    }

    @Bean
    public JaxRsApiApplication jaxRsApiApplication() {
        return new JaxRsApiApplication();
    }

    @Bean
    public JacksonJsonProvider jsonProvider() {
        return new JacksonJsonProvider();
    }

    private List<Object> restServiceList(ApplicationContext appContext) {
        return RestServiceBeanScanner.scan(appContext, AppConfig.RESOURCES_PACKAGE);
    }

    private List<Object> restProviderList(final ApplicationContext appContext,
                                          final JacksonJsonProvider jsonProvider) {
        final List<Object> providers = RestProviderBeanScanner.scan(appContext, PROVIDER_PACKAGE);
        providers.add(jsonProvider);
        return providers;
    }
}
