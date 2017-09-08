//package mobi.puut.config;
//
//import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.context.support.XmlWebApplicationContext;
//import org.springframework.web.filter.CharacterEncodingFilter;
//import org.springframework.web.filter.HiddenHttpMethodFilter;
//import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;
//
//import javax.servlet.Filter;
//
///**
// * Created by Chaklader on Sep, 2017
// */
//public class ServletInitializer extends AbstractDispatcherServletInitializer {
//
//    @Override
//    protected WebApplicationContext createServletApplicationContext() {
//        XmlWebApplicationContext cxt = new XmlWebApplicationContext();
////        cxt.setConfigLocation("/WEB-INF/spring/dispatcher-config.xml");
////        return cxt;
//        return null;
//    }
//
//    @Override
//    protected String[] getServletMappings() {
////        return new String[0];
//        return new String[]{"/*"};
//
//    }
//
//    @Override
//    protected WebApplicationContext createRootApplicationContext() {
//        return null;
//    }
//
//    @Override
//    protected Filter[] getServletFilters() {
//
////        return new Filter[]{
////                new OpenEntityManagerInViewFilter(),
////                new HiddenHttpMethodFilter(),
////                new CharacterEncodingFilter()
////        };
//
//        return new Filter[]{};
//    }
//}
