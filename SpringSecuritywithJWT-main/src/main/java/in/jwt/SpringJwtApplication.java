package in.jwt;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringJwtApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringJwtApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        
    }

    @Bean
    public FilterRegistrationBean<ResponseFilter> loggingFilter() {
        FilterRegistrationBean<ResponseFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ResponseFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
