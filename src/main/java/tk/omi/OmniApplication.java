package tk.omi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import tk.omi.model.Role;
import tk.omi.model.User;
import tk.omi.service.AppService;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@SpringBootApplication
public class OmniApplication extends SpringBootServletInitializer {

    @Autowired
    AppService appService;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(OmniApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(OmniApplication.class, args);
    }

    @PostConstruct
    public void init(){
        User user = new User("admin", "admin");
                Arrays.asList(
                        new Role("ROLE_USER"),
                        new Role("ROLE_ADMIN"));

        if (appService.findByUsername(user.getUsername()) == null){
            appService.save(user);
        }
    }
}
