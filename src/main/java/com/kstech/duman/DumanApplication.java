package com.kstech.duman;

import com.kstech.duman.util.DefaultProfileUtil;
import com.kstech.duman.util.InitializeLogUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableScheduling
@EnableAsync
public class DumanApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(DumanApplication.class);
		DefaultProfileUtil.addDefaultProfile(app);
		Environment env = app.run(args).getEnvironment();
		InitializeLogUtil.logApplicationStartup(env);
	}

}
