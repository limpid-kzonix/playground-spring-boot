package com.omniesoft.commerce.common.ws.imagestorage.impl;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.ws.SecuredRestTemplateAbstraction;
import com.omniesoft.commerce.common.ws.TokenRestTemplate;
import com.omniesoft.commerce.common.ws.imagestorage.ImageService;
import com.omniesoft.commerce.common.ws.imagestorage.payload.ImageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class ImageServiceImpl extends SecuredRestTemplateAbstraction implements ImageService {


    public ImageServiceImpl(String baseUrl, RestTemplate restTemplate,
                            TokenRestTemplate tokenRestTemplate) {

        super(baseUrl, restTemplate, tokenRestTemplate);
    }

    @Async(value = "httpThreadPoolExecutor")
    @Override
    public CompletableFuture<Void> deleteImage(String objectId) {
        URI uri = UriComponentsBuilder.fromUriString(baseUrl + "/delete?").queryParam("image-identifier",
                objectId).build().toUri();
        return wrap(() -> send(uri, HttpMethod.DELETE, ""));
    }


    private String uploadImage(MultipartFile multipartFile) {
        log.info(Thread.currentThread().getName());
        String uploadUrl = baseUrl + "/" + "/upload/";
        LinkedMultiValueMap<String, Object> linkedMultiValueMap = getFormDataPart(multipartFile);
        HttpHeaders headers = preformHeaders(uploadUrl);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(linkedMultiValueMap, headers);
        ImageDto imageDto;
        try {
            imageDto = restTemplate.postForEntity(uploadUrl, httpEntity, ImageDto.class).getBody();
            return imageDto.getMessage();
        } catch (Exception e) {
            throw new UsefulException();
        }
    }


    private LinkedMultiValueMap<String, Object> getFormDataPart(MultipartFile multipartFile) {
        File dest;
        try {
            dest = convert(multipartFile);
        } catch (IOException e) {
            throw new UsefulException();
        }
        LinkedMultiValueMap<String, Object> linkedMultiValueMap = new LinkedMultiValueMap<>();
        linkedMultiValueMap.add("file", new FileSystemResource(dest));
        return linkedMultiValueMap;
    }

    private HttpHeaders preformHeaders(String uploadUrl) {
        HttpHeaders headers = restTemplate.headForHeaders(uploadUrl);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        return headers;
    }

    private File convert(MultipartFile file) throws IOException {
        File convertedFile = File.createTempFile(file.getOriginalFilename(), ".jpg");
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        }
        return convertedFile;
    }

}
