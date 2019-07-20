package com.shanjiancaofu.massmapleapi;

import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Map;

@Component
public class WebListener implements ServletContextListener {

    @Value("${server.address}")
    String serverAddress;


    @Value("${server.port}")
    int serverport;

    @Autowired
    private ServiceRegistry serviceRegistry;


    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext servletContext = event.getServletContext();
        ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        RequestMappingHandlerMapping bean = applicationContext.getBean(RequestMappingHandlerMapping.class);


        Map<RequestMappingInfo, HandlerMethod> handlerMethods = bean.getHandlerMethods();

        for (RequestMappingInfo requestMappingInfo : handlerMethods.keySet()) {
            String name = requestMappingInfo.getName();
            if (name != null) {
                try {
                    serviceRegistry.rigistry(name, "localhost:8082");
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
