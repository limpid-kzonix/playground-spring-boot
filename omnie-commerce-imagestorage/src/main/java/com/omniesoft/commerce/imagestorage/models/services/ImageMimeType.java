package com.omniesoft.commerce.imagestorage.models.services;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.ImageModuleErrorCodes;

public enum ImageMimeType {
    PNG("image/png", "png"),  //# Portable Network Graphics[9](RFC 2083)
    GIF("image/gif", "gif"), //# GIF(RFC 2045 и RFC 2046)
    JPEG("image/jpeg", "jpeg"), //#  (RFC 2045 и RFC 2046)
    JPEG_8("image/pjpeg", "jpeg"), //#JPEG[8]
    TIFF("image/tiff", "tiff"), // #TIFF(RFC 3302)
    WEBP("image/webp", "webp");

    private String type;
    private String format;

    ImageMimeType(String type, String format) {
        this.type = type;
        this.format = format;
    }

    public static ImageMimeType lookup(String type) {
        for (ImageMimeType imageMimeType : ImageMimeType.values()) {
            if (imageMimeType.getMimeType().equalsIgnoreCase(type)) {
                return imageMimeType;
            }
        }
        throw new UsefulException("Unsupported image mime-type: " + type).withCode(ImageModuleErrorCodes.UNSUPPORTED_IMAGE_MIME_TYPE);
    }

    public String getMimeType() {
        return type;
    }

    public String getFormat() {
        return format;
    }
}
