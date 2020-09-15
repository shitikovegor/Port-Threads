package com.shitikov.port.reader;

import com.shitikov.port.exception.ProjectException;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataReader {
    private static DataReader instance = new DataReader();

    private DataReader() {
    }

    public static DataReader getInstance() {
        return instance;
    }

    public List<String> readFile(String fileName) throws ProjectException {
        List<String> text = new ArrayList<>();
        Path path = Paths.get(fileName);

        if (Files.exists(path) && !Files.isDirectory(path) && Files.isReadable(path)) {
            try (Stream<String> dataStream = Files.lines(path)) {
                text = dataStream.collect(Collectors.toList());
            } catch (IOException | UncheckedIOException e) {
                throw new ProjectException("Program error.", e);
            }
        }
        return text;
    }
}
