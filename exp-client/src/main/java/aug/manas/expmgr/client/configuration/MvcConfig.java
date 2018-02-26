package aug.manas.expmgr.client.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/expmgr").setViewName("expmgr");
		registry.addViewController("/").setViewName("expmgr");
		registry.addViewController("/modal-form").setViewName("modal-form");
	}

}