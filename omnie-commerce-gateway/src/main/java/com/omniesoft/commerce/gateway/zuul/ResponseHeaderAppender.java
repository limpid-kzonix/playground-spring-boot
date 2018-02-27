package com.omniesoft.commerce.gateway.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import javax.servlet.http.HttpServletResponse;

public class ResponseHeaderAppender extends ZuulFilter {

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 999;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        String serviceIdentifier = "undefined";
        Object serviceId = currentContext.get("serviceId");
        if (serviceId instanceof String) {
            serviceIdentifier = serviceId.toString();
        }
        HttpServletResponse response = currentContext.getResponse();
        response.addHeader("X-Returned-From", getXReturnedFrom(serviceIdentifier));
        response.addHeader("X-Proxy-Response", "true");


        return null;
    }

    private String getXReturnedFrom(String serviceIdentifier) {
        return "SERVICE: " + serviceIdentifier.trim().toUpperCase();
    }
}
