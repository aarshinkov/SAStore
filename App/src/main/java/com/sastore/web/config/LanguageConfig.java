package com.sastore.web.config;

import com.sastore.web.utils.AppConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Configuration
public class LanguageConfig implements WebMvcConfigurer {

  @Bean(name = "localeResolver")
  public CookieLocaleResolver cookieLocaleResolver() {
    CookieLocaleResolver localeResolver = new CookieLocaleResolver();
    localeResolver.setCookieName(AppConstants.LANG_COOKIE_NAME);
    localeResolver.setDefaultLocale(new Locale(AppConstants.LANG_DEFAULT));

    return localeResolver;
  }

  @Bean(name = "localeInterceptor")
  public LocaleChangeInterceptor localeInterceptor() {
    LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
    interceptor.setParamName(AppConstants.LANG_COOKIE_NAME);

    return interceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(localeInterceptor());
  }
}
