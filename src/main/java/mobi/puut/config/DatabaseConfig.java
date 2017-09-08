package mobi.puut.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by Chaklader on 8/1/17.
 */

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = {"mobi.puut.database"})
public class DatabaseConfig {

    @Bean
    public LocalSessionFactoryBean sessionFactory() {

        //  mobi.puut.entities
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(
                new String[]{"mobi.puut.entities"});
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(
            SessionFactory sessionFactory) {

        HibernateTransactionManager txManager
                = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);

        return txManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        // dataSource.setUrl("jdbc:mysql://localhost:3306/wallet?createDatabaseIfNotExist=true");
        dataSource.setUrl("jdbc:mysql://localhost:3306/Wallet");
        dataSource.setUsername("testuser");
        dataSource.setPassword("testpassword");

        return dataSource;
    }

    Properties hibernateProperties() {

        Properties properties = new Properties();
//        properties.setProperty("hibernate.hbm2ddl.auto", "saveOrUpdate-drop");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        return properties;
    }


//    It will increase the length of the JPA session or as the documentation says, it
//    is used "to allow for lazy loading in web views despite the original transactions
//    already being completed. So this way the JPA session will be open a bit longer
//    and because of that you can lazily load collections in your jsp files and controller
//    classes.


//    <filter>
//    <filter-name>SpringOpenEntityManagerInViewFilter</filter-name>
//    <filter-class>org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter</filter-class>
//</filter>
//<filter-mapping>
//    <filter-name>SpringOpenEntityManagerInViewFilter</filter-name>
//    <url-pattern>/*</url-pattern>
//</filter-mapping>

}


