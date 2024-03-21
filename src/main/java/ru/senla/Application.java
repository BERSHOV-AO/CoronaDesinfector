package ru.senla;

import java.util.Map;

public class Application {
    public static ApplicationContext run(String packageToScan, Map<Class, Class> ifc2ImplClass) {
       JavaConfig config = new JavaConfig(packageToScan, ifc2ImplClass);
       ApplicationContext context = new ApplicationContext(config);
       ObjectFactory objectFactory = new ObjectFactory(context);
      // ObjectFactory
        context.setFactory(objectFactory);
        return context;
    }

}
