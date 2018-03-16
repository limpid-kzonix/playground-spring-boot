package com.omniesoft.commerce.imagestorage.models.services.impl;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.ImageModuleErrorCodes;
import com.omniesoft.commerce.imagestorage.models.services.ImageMimeType;
import com.omniesoft.commerce.imagestorage.models.services.ImageOperationsService;
import lombok.extern.slf4j.Slf4j;
import org.imgscalr.Scalr;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public InputStream prepareSmall(BufferedImage originalImage, ImageMimeType mimeType) throws IOException {

        return transformation(originalImage, mimeType, 150, 150);
    }

    @Override
    public InputStream prepareMedium(BufferedImage originalImage, ImageMimeType mimeType) throws IOException {


        return transformation(originalImage, mimeType, 500, 500);
    }

    @Override
    public InputStream prepareLarge(BufferedImage originalImage, ImageMimeType mimeType) throws IOException {

        return transformation(originalImage, mimeType, 1000, 1000);
    }

    @Override
    public InputStream prepareOriginal(BufferedImage originalImage, ImageMimeType mimeType) throws IOException {


        return transformation(originalImage, mimeType, originalImage.getHeight(), originalImage.getWidth());
    }

    @NotNull
    private InputStream transformation(BufferedImage originalImage, ImageMimeType mimeType, int w, int h) throws IOException {
        if (isCompressed(mimeType))
            return transform(
                    Scalr.resize(crop(originalImage, getImageDimension(originalImage)),
                            Scalr.Method.ULTRA_QUALITY,
                            Scalr.Mode.AUTOMATIC, w, h), mimeType
            );
        else {
            return transform(resize(originalImage, w, h), mimeType);
        }

    }


    @Override
    public ImageMimeType lookupType(MultipartFile file) {
        return ImageMimeType.lookup(file.getContentType());

    }

    @Override
    public BufferedImage compress(BufferedImage original, ImageMimeType mimeType) throws IOException {
        if (original == null) {
            throw new UsefulException("Can`t to compress NULL image file. Image received from client is corrupted").withCode(ImageModuleErrorCodes.RECEIVED_IMAGE_IS_CORRUPTED);
        }
        if (isCompressed(mimeType) || original.getType() == BufferedImage.TYPE_INT_RGB)
            return compressJPEG(original);
        else
            return original;
    }

    @NotNull
    private BufferedImage compressJPEG(BufferedImage original) throws IOException {
        // create a blank, RGB, same width and height, and a white background
        BufferedImage result = convertToJpeg(original);


        log.info("Pre compress ::: original =  {}", original.toString());
        ByteArrayOutputStream compressed = new ByteArrayOutputStream();
        ImageOutputStream outputStream = ImageIO.createImageOutputStream(compressed);

        // NOTE: The rest of the code is just a cleaned up version of your code

        // Obtain writer for JPEG format
        ImageWriter jpgWriter = null;
        ImageWriteParam jpgWriteParam = null;

        jpgWriter = ImageIO.getImageWritersByFormatName("jpeg").next();
        log.info("Obtained JPEG writer");

        // Configure JPEG compression: 12% quality
        jpgWriteParam = jpgWriter.getDefaultWriteParam();

        if (jpgWriteParam.canWriteCompressed()) {
            jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            jpgWriteParam.setCompressionQuality(0.1f);
        }

        // Set your in-memory stream as the output
        jpgWriter.setOutput(outputStream);

        // Write image as JPEG w/configured settings to the in-memory stream
        // (the IIOImage is just an aggregator object, allowing you to associate
        // thumbnails and metadata to the image, it "does" nothing)
        try {
            jpgWriter.write(null, new IIOImage(result, null, null), jpgWriteParam);
        } catch (Exception e) {
            close(compressed, outputStream, jpgWriter);
            return original;
        }
        // Dispose the writer to free resources
        close(compressed, outputStream, jpgWriter);
        BufferedImage compressedBI = ImageIO.read(new ByteArrayInputStream(compressed.toByteArray()));
        log.info("Post compress ::: compressed image = {}", compressedBI.toString());
        return compressedBI;
    }

    private BufferedImage convertToJpeg(BufferedImage original) {
        BufferedImage result = new BufferedImage(original.getWidth(),
                original.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = result.createGraphics();
        g.drawImage(original, 0, 0, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        return result;
    }

    private void close(ByteArrayOutputStream compressed, ImageOutputStream outputStream, ImageWriter jpgWriter) throws IOException {
        // Dispose the writer to free resources
        jpgWriter.dispose();

        // close streams
        outputStream.close();
        compressed.close();
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

    private InputStream transform(BufferedImage image, ImageMimeType mimeType) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, mimeType.getFormat(), baos);
        return new ByteArrayInputStream(baos.toByteArray());
    }

    private BufferedImage resize(BufferedImage original, int w, int h) {

        BufferedImage resizedImage = new BufferedImage(w, h, original.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(original, 0, 0, w, h, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        return resizedImage;
    }

    private boolean isCompressed(ImageMimeType mimeType) {
        return mimeType.equals(ImageMimeType.JPEG) || mimeType.equals(ImageMimeType.JPEG_8);
    }
}
