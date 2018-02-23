package com.omniesoft.commerce.gateway.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

public class ResponseHeaderAppender extends ZuulFilter {

    private final static String INFO =
            "0041006C006500780061006E" + "006400650072002000420061006C" + "0079007300680079006E002" + "0003A0020006200610" + "06C00790073007A007900" + "6E00400067006D006100" + "69006C002E0063006F006D";

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
        response.addHeader("X-Software-Developer", preformHeaderValue());
        currentContext.addZuulResponseHeader("X-Feedback", preformHeaderValue());


        return null;
    }

    private String getXReturnedFrom(String serviceIdentifier) {
        return "SERVICE: " + serviceIdentifier.trim().toUpperCase();
    }

    private String preformHeaderValue() {
        try {
            byte[] bytes = Hex.decodeHex(INFO.trim().toCharArray());
            return new String(bytes, "UTF-16");
        } catch (DecoderException | UnsupportedEncodingException e) {
            return "undefined";
        }
    }
}
