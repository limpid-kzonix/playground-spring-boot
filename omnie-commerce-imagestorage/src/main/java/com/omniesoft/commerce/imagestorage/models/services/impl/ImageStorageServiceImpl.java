package com.omniesoft.commerce.imagestorage.models.services.impl;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.ImageModuleErrorCodes;
import com.omniesoft.commerce.imagestorage.models.dto.ImageDto;
import com.omniesoft.commerce.imagestorage.models.repositories.PicturesRepository;
import com.omniesoft.commerce.imagestorage.models.services.ImageOperationsService;
import com.omniesoft.commerce.imagestorage.models.services.ImageStorageService;
import com.omniesoft.commerce.imagestorage.models.services.ImageType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

import static java.util.concurrent.CompletableFuture.runAsync;

@Slf4j
@Service
@CacheConfig(cacheNames = "pictures")
public class ImageStorageServiceImpl implements ImageStorageService {


    private final MongoTemplate mongoTemplate;
    private final PicturesRepository picturesRepository;
    private final RandomStringGenerator randomStringGenerator;
    private final ImageOperationsService imageOperationsService;

    private Executor imageProcessingExecutor;
    private Executor mongoWriterExecutor;

    public ImageStorageServiceImpl(MongoTemplate mongoTemplate, PicturesRepository picturesRepository, RandomStringGenerator randomStringGenerator, ImageOperationsService imageOperationsService) {
        this.mongoTemplate = mongoTemplate;
        this.picturesRepository = picturesRepository;
        this.randomStringGenerator = randomStringGenerator;
        this.imageOperationsService = imageOperationsService;
    }

    @Autowired
    @Required
    public void setImageProcessingExecutor(@Qualifier("imageProcessableContext") Executor imageProcessingExecutor) {
        this.imageProcessingExecutor = imageProcessingExecutor;
    }

    @Autowired
    @Required
    public void setMongoWriterExecutor(@Qualifier("mongoExecutionWritableContext") Executor mongoWriterExecutor) {
        this.mongoWriterExecutor = mongoWriterExecutor;
    }

    @Override
    public String store(MultipartFile file) {

        BufferedImage read = getBufferedImage(file);
        mongoTemplate.getDb().getStats().throwOnError();
       
        String generated = randomStringGenerator.generate(40);

        try {
            prepareAndSave(file, read, generated);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return generated;

    }


    @Override
    @Cacheable(value = "pictures", key = "{ #root.methodName, #imageId, #type }")
    public ImageDto fetchImageByIdAndType(String imageId, ImageType type) {
        log.info(" Loading images with image-id {} and image-type {}", imageId, type);
        ImageDto imageDto = picturesRepository.fetchPicturesSource(imageId, type);
        return imageDto;
    }

    @Async("mongoExecutionWritableContext")
    @Override
    public void delete(String imageId) {

        picturesRepository.deleteImage(imageId);
    }

//----------------------PRIVATE METHODS-------------------------

    private BufferedImage getBufferedImage(MultipartFile file) {
        try {
            return ImageIO.read(file.getInputStream());
        } catch (IOException e) {
            throw new UsefulException();
        }
    }

    @Async(value = "imageProcessableContext")
    void prepareAndSave(MultipartFile file, BufferedImage read, String generated) throws ExecutionException, InterruptedException {
        CompletableFuture.supplyAsync(() -> compress(read), imageProcessingExecutor).thenApplyAsync(c -> {
            try {
                writeSmall(file, c, generated).get();
                return c;
            } catch (InterruptedException | ExecutionException e) {
                throw new UsefulException("Occurred during the blocking operation").withCode(ImageModuleErrorCodes.IMAGE_PROCESSING_ERROR);
            }
        }, imageProcessingExecutor).thenApply(c -> {
            writeOriginal(file, c, generated);
            writeLarge(file, c, generated);
            writeMedium(file, c, generated);
            return Optional.empty();
        }).get();
    }

    private BufferedImage compress(BufferedImage read) {
        try {
            return imageOperationsService.compress(read);
        } catch (IOException e) {
            throw new UsefulException("Can`t compress the image");
        }
    }

    @Async(value = "mongoExecutionWritableContext")
    CompletableFuture<Void> writeMedium(MultipartFile file, BufferedImage read, String generated) {

        return CompletableFuture.runAsync(() -> {
            try {
                picturesRepository
                        .storeSource(imageOperationsService.prepareMedium(read), generated, file
                                .getContentType(), ImageType.MEDIUM);
            } catch (IOException e) {
                throw new UsefulException(ImageModuleErrorCodes.IMAGE_PROCESSING_ERROR);
            }
        }, mongoWriterExecutor);

    }

    @Async(value = "mongoExecutionWritableContext")
    CompletableFuture<Void> writeSmall(MultipartFile file, BufferedImage read, String generated) {

        return runAsync(() -> {
            try {
                picturesRepository
                        .storeSource(imageOperationsService.prepareSmall(read), generated, file
                                .getContentType(), ImageType.SMALL);
            } catch (IOException e) {
                throw new UsefulException(ImageModuleErrorCodes.IMAGE_PROCESSING_ERROR);
            }
        }, mongoWriterExecutor);

    }

    @Async(value = "mongoExecutionWritableContext")
    CompletableFuture<Void> writeLarge(MultipartFile file, BufferedImage read, String generated) {

        return runAsync(() -> {
            try {
                picturesRepository
                        .storeSource(imageOperationsService.prepareLarge(read), generated, file
                                .getContentType(), ImageType.LARGE);
            } catch (IOException e) {
                throw new UsefulException(ImageModuleErrorCodes.IMAGE_PROCESSING_ERROR);
            }
        }, mongoWriterExecutor);

    }

    @Async(value = "mongoExecutionWritableContext")
    CompletableFuture<Void> writeOriginal(MultipartFile file, BufferedImage read, String generated) {

        return runAsync(() -> {
            try {
                picturesRepository
                        .storeSource(imageOperationsService.prepareOriginal(read), generated, file
                                .getContentType(), ImageType.ORIGINAL);
            } catch (IOException e) {
                throw new UsefulException(ImageModuleErrorCodes.IMAGE_PROCESSING_ERROR);
            }
        }, mongoWriterExecutor);

    }
}
