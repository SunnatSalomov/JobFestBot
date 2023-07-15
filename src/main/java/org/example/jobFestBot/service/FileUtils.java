package org.example.jobFestBot.service;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@UtilityClass
public class FileUtils {

    @SneakyThrows
    public static void write(String path,String data){
        Files.writeString(Path.of(path),data, StandardOpenOption.WRITE);
    }

}
