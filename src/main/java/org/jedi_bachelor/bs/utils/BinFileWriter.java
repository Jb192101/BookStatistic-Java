package org.jedi_bachelor.bs.utils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Scope("prototype")
public class BinFileWriter<T> {
    private String filePath;
    private T object;

    public BinFileWriter(String filePath) {
        this.filePath = filePath;
    }

    public void write() {
        if(object != null) {
            try (FileOutputStream fos = new FileOutputStream(this.filePath);) {
                clearFile();

                ObjectOutputStream oos = new ObjectOutputStream(fos);

                oos.writeObject(this.object);

                oos.close();
            } catch (IOException ignored) {
            }
        }
    }

    private void clearFile() throws IOException {
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            Files.delete(path);
        }
        Files.createFile(path);
    }

    public File getFile() {
        return new File(this.filePath);
    }

    public void setFile(String _filePath) {
        this.filePath = _filePath;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}