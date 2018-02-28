package com.omniesoft.commerce.imagestorage.controllers;

import com.omniesoft.commerce.common.responce.ResponseMessage;
import com.omniesoft.commerce.imagestorage.models.dto.ImageDto;
import com.omniesoft.commerce.imagestorage.models.services.ImageStorageService;
import com.omniesoft.commerce.imagestorage.models.services.ImageType;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.entity.ContentType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@AllArgsConstructor
public class PictureOperationsController {

    private ImageStorageService service;

    @PostMapping(path = "/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseMessage uploadImage(@RequestParam("file") MultipartFile file) {
        return new ResponseMessage(service.store(file));
    }

    @DeleteMapping(path = "/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImage(@RequestParam("image-identifier") String imageId) {

        service.delete(imageId);

    }

    // TODO: decide what type of image fetching is needed
    // Secured - with ability to get user-data from request-context
    @GetMapping(path = "/fetch")
    public ResponseEntity<byte[]> fetchImage(@RequestParam("image_identifier") String imageId,
                                             @RequestParam("image_type") ImageType type,
                                             @ApiIgnore Authentication authentication) {

        return fetchImageAsByteArray(imageId, type);
    }

    // TODO: decide what type of image fetching is needed
    // Unsecured - without ability to get user-data from request-context
    @GetMapping(path = "/fetch/open")
    public ResponseEntity<byte[]> fetchOpenImage(@RequestParam("image_identifier") String imageId,
                                                 @RequestParam("image_type") ImageType type) {

        return fetchImageAsByteArray(imageId, type);
    }

    // TODO: decide what type of image fetching is needed
    // Secured - with ability to get user-data from request-context
    // Response model - image as base64 encoded string
    @GetMapping(path = "/fetch/base64")
    public ResponseEntity<String> fetchBase64EncodeImage(
            @RequestParam("image_identifier") String imageId,
            @RequestParam("image_type") ImageType type,
            @ApiIgnore Authentication authentication) {

        return fetchImageAsBase64EncodedString(imageId, type);
    }


    // --------------------------PRIVATE METHODS---------------------------------


    private ResponseEntity<String> fetchImageAsBase64EncodedString(@RequestParam("image_identifier") String imageId, @RequestParam("image_type") ImageType type) {
        ImageDto imageDto = service.fetchImageByIdAndType(imageId, type);
        String base64EncodedImage = Base64.encodeBase64String(imageDto.getStream());

        return getResponseEntity(
                base64EncodedImage,
                ContentType.DEFAULT_TEXT.getMimeType(),
                base64EncodedImage.length()
        );
    }


    private ResponseEntity<byte[]> fetchImageAsByteArray(@RequestParam("image_identifier") String imageId, @RequestParam("image_type") ImageType type) {
        ImageDto imageDto = service.fetchImageByIdAndType(imageId, type);

        return getResponseEntity(
                imageDto.getStream(),
                imageDto.getContentType(),
                imageDto.getStream().length
        );
    }

    private <T> ResponseEntity<T> getResponseEntity(T source, String contentType, int contentLength) {

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, contentType);

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(contentType))
                .headers(headers)
                .contentLength(contentLength)
                .body(source);
    }
}
