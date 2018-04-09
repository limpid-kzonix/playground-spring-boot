package com.omniesoft.commerce.gateway;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.List;

@Component
@Primary
@Profile("!prod")
public class DocumentationProvider implements SwaggerResourcesProvider {

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = Lists.newArrayList();
        resources.add(swaggerResource("security-service", "/omnie-security/api/v2/api-docs?group=internal-api", "1.0"));
        resources.add(swaggerResource("security-service", "/omnie-security/api/v2/api-docs?group=security-custom-api", "2.0"));
        resources.add(swaggerResource("user-service", "/omnie-user/api/v2/api-docs?group=user-api", "2.0"));
        resources.add(swaggerResource("owner-service", "/omnie-owner/api/v2/api-docs?group=owner-api", "2.0"));
        resources.add(swaggerResource("support-service", "/omnie-support/api/v2/api-docs?group=support-api", "2.0"));
        resources.add(swaggerResource("image-service", "/omnie-imagestorage/api/v2/api-docs?group=image-api", "2.0"));
        resources.add(swaggerResource("notification-service", "/omnie-notification/api/v2/api-docs?group=notification-api", "2.0"));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }

}