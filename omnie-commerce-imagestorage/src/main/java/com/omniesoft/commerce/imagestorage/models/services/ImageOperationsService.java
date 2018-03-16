package com.omniesoft.commerce.imagestorage.models.services;

import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public interface ImageOperationsService {

    InputStream prepareSmall(BufferedImage originalImage, ImageMimeType mimeType) throws IOException;

    InputStream prepareMedium(BufferedImage originalImage, ImageMimeType mimeType) throws IOException;

    InputStream prepareLarge(BufferedImage originalImage, ImageMimeType mimeType) throws IOException;

    InputStream prepareOriginal(BufferedImage originalImage, ImageMimeType mimeType) throws IOException;

    BufferedImage compress(BufferedImage original, ImageMimeType mimeType) throws IOException;

    ImageMimeType lookupType(MultipartFile file);


}
