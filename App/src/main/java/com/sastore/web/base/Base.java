package com.sastore.web.base;

import com.sastore.web.security.SecurityChecks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Component
public class Base {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected SecurityChecks sc;

    @Autowired
    private MessageSource messageSource;

    protected String getMessage(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }

    protected String getMessage(String key, Object... params) {
        return messageSource.getMessage(key, params, LocaleContextHolder.getLocale());
    }
}
