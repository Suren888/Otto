package com.example.ottoservice.utils;

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileUtility {

    public static final String QUOTE = ",";
    public static final String PNG = ".png";
    public static final String SLASH = "/";
    public static final String COMMA = ",";

    @Value( "${pictures.save.directory}" )
    private String picturesSaveDirectory;

    public String getPicturesSaveDirectory(String productId) {
        return picturesSaveDirectory + "products" + SLASH + productId + SLASH;
    }

    public  byte[] readImage(String pictureId, String fileName) {
        String fullPath = getPicturesSaveDirectory(pictureId) + fileName;
        byte[] result = null;
        try {
            result = Files.readAllBytes(new File(fullPath).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void createDirectoryIfNotExists(String directoryPath) {
        File directory = new File(directoryPath);
        if (! directory.exists()){
            directory.mkdirs();
        }
    }

    public String getBase64ImgPart(String img) {
        if (img == null || !img.contains(QUOTE)) {
            return img;
        }
        return img.split(QUOTE)[1];
    }

    public void writeImageFile(String base64Img, String productId, String title) {
        String saveDirectory = getPicturesSaveDirectory(productId);
        createDirectoryIfNotExists(saveDirectory);

        byte[] data = Base64.decodeBase64(getBase64ImgPart(base64Img));
        try (OutputStream stream = new FileOutputStream(saveDirectory + title)) {
            stream.write(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> listFilesUsingJavaIO(String productId) {
        String dir = getPicturesSaveDirectory(productId);
        return Stream.of(new File(dir).listFiles())
                .filter(file -> !file.isDirectory() && file.getName().contains(PNG))
                .map(File::getName)
                .collect(Collectors.toList());
    }

    public void deleteProductImages(Collection<String> imgNames, long productId) throws IOException {
        String directory = getPicturesSaveDirectory(String.valueOf(productId));
        for (String imgName: imgNames) {
            new File(directory + imgName).delete();
        }
    }

    public void deleteProductImageUploadDirectory(long productId) throws IOException {
        String directory = getPicturesSaveDirectory(String.valueOf(productId));
        FileUtils.deleteDirectory(new File(directory));

    }
}
