package it.unitn.shoppinglesto.utils;

import java.util.Map;
import java.io.StringWriter;
import java.util.Properties;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

/**
 *
 *
 */

public class VelocityHelper {
    private static VelocityEngine velocityEngine;

    /**
     * Returns instance of VelocityEngine after initializing it if null.
     * @return the instance of VelocityEngine
     */
    public static VelocityEngine getVelocityEngine(){
        if(velocityEngine == null)
            init();
        return velocityEngine;
    }

    /**
     * Configures VelocityEngine object and initializes it.
     */
    public static void init(){
        velocityEngine = new VelocityEngine();
        Properties velocityProperties = new Properties();
        velocityProperties.put("resource.loader", "class");
        velocityProperties.put("class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        velocityProperties.put("class.resource.loader", "/WEB-INF/classes/");
        velocityEngine.init(velocityProperties);
    }

    /**
     * Creates a dynamic html template.
     * @param templateName the name of the template.
     * @param model map containing key/value pairs of objects to be dynamically included in the template.
     * @return String value of output writer for rendered template.
     */
    public static String createVelocityContent(String templateName, Map<String, ? extends Object> model){
        VelocityContext context = new VelocityContext();

        for(String key : model.keySet()){
            context.put(key, model.get(key));
        }

        VelocityEngine ve = getVelocityEngine();
        Template template = ve.getTemplate(templateName);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString();

    }

}
