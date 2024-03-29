package com.sastore.web.config;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Configuration
public class MvcViewConfig implements WebMvcConfigurer {

  @Bean
  public SpringResourceTemplateResolver srtr() {
    SpringResourceTemplateResolver srtr = new SpringResourceTemplateResolver();
    srtr.setPrefix("/WEB-INF/views/");
    srtr.setSuffix(".html");
    srtr.setTemplateMode("HTML");
    srtr.setCharacterEncoding("UTF-8");
    srtr.setCacheable(false);
    srtr.setOrder(1);

    return srtr;
  }

  @Bean
  public LayoutDialect layoutDialect() {
    return new LayoutDialect();
  }

  @Bean
  public SpringSecurityDialect ssd() {
    return new SpringSecurityDialect();
  }

  @Bean
  public Set<IDialect> additionalDialects() {
    Set<IDialect> additionalDialects = new HashSet<>();
    additionalDialects.add(layoutDialect());
    additionalDialects.add(ssd());
    return additionalDialects;
  }

  @Bean
  public Set<ITemplateResolver> templateResolvers() {
    Set<ITemplateResolver> templateResolvers = new HashSet<>();
    templateResolvers.add(srtr());

    return templateResolvers;
  }

  @Bean
  public SpringTemplateEngine templateEngine() {
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.setTemplateResolvers(templateResolvers());
    templateEngine.setAdditionalDialects(additionalDialects());

    return templateEngine;
  }

  @Bean
  public ThymeleafViewResolver viewResolver() {
    ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
    viewResolver.setTemplateEngine(templateEngine());
    viewResolver.setCharacterEncoding("UTF-8");

    return viewResolver;
  }
}
