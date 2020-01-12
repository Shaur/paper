package ru.comics.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;

public class HashUtils {

    public static String hashFile(File file) {
        String hashFleString = "";
        try (InputStream inputStream = Files.newInputStream(file.toPath())) {
            hashFleString = DigestUtils.md5Hex(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return hashFleString;
    }

    public static boolean checkFile(File file){
        return file.exists() && !file.isDirectory();
    }

    public static String multiPartFiletomd5(MultipartFile multipartFile)  {
        String hashFileString = "";
        try ( InputStream inputStream = multipartFile.getInputStream()){
            hashFileString = DigestUtils.md5Hex(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hashFileString;
    }


}
