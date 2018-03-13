package com.omniesoft.commerce.imagestorage.models.services.impl;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.ImageModuleErrorCodes;
import com.omniesoft.commerce.imagestorage.models.services.ImageOperationsService;
import lombok.extern.slf4j.Slf4j;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class ImageOperationsServiceImpl implements ImageOperationsService {

    @Override
    public InputStream prepareSmall(BufferedImage originalImage) throws IOException {

        return transform(
                Scalr.resize(crop(originalImage, getImageDimension(originalImage)), Scalr.Method.ULTRA_QUALITY,
                        Scalr.Mode.AUTOMATIC, 150, 150));
    }

    @Override
    public InputStream prepareMedium(BufferedImage originalImage) throws IOException {


        return transform(
                Scalr.resize(crop(originalImage, getImageDimension(originalImage)),
                        Scalr.Method.ULTRA_QUALITY,
                        Scalr.Mode.AUTOMATIC, 500, 500)
        );
    }


    @Override
    public InputStream prepareLarge(BufferedImage originalImage) throws IOException {

        return transform(
                Scalr.resize(crop(originalImage, getImageDimension(originalImage)),
                        Scalr.Method.ULTRA_QUALITY,
                        Scalr.Mode.AUTOMATIC, 1000, 1000)
        );
    }

    @Override
    public InputStream prepareOriginal(BufferedImage originalImage) throws IOException {


        return transform(
                Scalr.resize(originalImage,
                        Scalr.Method.ULTRA_QUALITY,
                        Scalr.Mode.AUTOMATIC, originalImage.getWidth(), originalImage.getHeight())
        );
    }

    public BufferedImage compress(BufferedImage original) throws IOException {
        if (original == null) {
            throw new UsefulException("Can`t to compress NULL image file. Image received from client is corrupted").withCode(ImageModuleErrorCodes.RECEIVED_IMAGE_IS_CORRUPTED);
        }

        log.info("Pre compress ::: original =  {}", original.toString());
        ByteArrayOutputStream compressed = new ByteArrayOutputStream();
        ImageOutputStream outputStream = ImageIO.createImageOutputStream(compressed);

        // NOTE: The rest of the code is just a cleaned up version of your code

        // Obtain writer for JPEG format
        ImageWriter jpgWriter = null;
        ImageWriteParam jpgWriteParam = null;

        jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();
        log.info("Obtained JPEG writer");

        // Configure JPEG compression: 12% quality
        jpgWriteParam = jpgWriter.getDefaultWriteParam();

        if (!jpgWriteParam.canWriteCompressed()) {
            return original;
        }
        jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        jpgWriteParam.setCompressionQuality(0.1f);

        // Set your in-memory stream as the output
        jpgWriter.setOutput(outputStream);

        // Write image as JPEG w/configured settings to the in-memory stream
        // (the IIOImage is just an aggregator object, allowing you to associate
        // thumbnails and metadata to the image, it "does" nothing)
        try {
            jpgWriter.write(null, new IIOImage(original, null, null), jpgWriteParam);
        } catch (Exception e) {
            throw new UsefulException("Can`t to compress invalid image file. Image received from client is corrupted", e.getMessage()).withCode(ImageModuleErrorCodes.RECEIVED_IMAGE_IS_CORRUPTED);
        }


        // Dispose the writer to free resources
        jpgWriter.dispose();

        // close streams
        outputStream.close();
        compressed.close();

        BufferedImage compressedBI = ImageIO.read(new ByteArrayInputStream(compressed.toByteArray()));
        log.info("Post compress ::: compressed image = {}", compressedBI.toString());
        return compressedBI;

    }

    private BufferedImage crop(BufferedImage bufferedImage, Rectangle2D property) {

        return Scalr.crop(bufferedImage, (int) property.getX(), (int) property.getY(), (int) property.getWidth(),
                (int) property.getHeight());
    }

    private Rectangle2D getImageDimension(BufferedImage image) {

        int width = image.getWidth();
        int height = image.getHeight();

        int hCenter = width / 2;
        int vCenter = height / 2;

        int dimension = image.getWidth() > image.getHeight
                () ? image.getHeight() : image.getWidth();

        return new Rectangle(hCenter - dimension / 2, vCenter - dimension / 2, dimension, dimension);
    }

    private InputStream transform(BufferedImage image) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpeg", baos);
        return new ByteArrayInputStream(baos.toByteArray());
    }
}
