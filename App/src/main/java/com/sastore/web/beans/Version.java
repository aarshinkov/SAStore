package com.sastore.web.beans;

import com.sastore.web.base.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public class Version extends Base {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ServletContext context;

    private String CURRENT_VERSION = null;

    public synchronized String getVersion() {
        if (CURRENT_VERSION != null) {
            CURRENT_VERSION = getMessage("footer.progress");
        }

        try {
            if (context != null) {
                InputStream is = context.getResourceAsStream("/META-INF/MANIFEST.MF");
                Properties props = new Properties();
                props.load(is);
                CURRENT_VERSION = "v" + props.get("Implementation-Version") + " " + getMessage("footer.from") + " " + props.get("Implementation-Date");
            }
        } catch (Exception e) {
            CURRENT_VERSION = getMessage("footer.progress");
//      CURRENT_VERSION = "Work in progress...";
        }

        return CURRENT_VERSION;
    }
}
