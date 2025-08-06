package org.jedi_bachelor.bs.utils;

/*
Неиспользуемый класс
 */

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

@Component
@Scope("prototype")
public class BinFileReader<T> {
    private String filePath;
    private T object;

    public BinFileReader(String filePath) {
        setFilePath(filePath);
    }

    public void read() {
        try(FileInputStream fis = new FileInputStream(this.filePath)) {
            ObjectInputStream ois = new ObjectInputStream(fis);

            this.object = (T) ois.readObject();
            System.out.println(this.object);

            ois.close();
        } catch (IOException | ClassNotFoundException ignored) {

        }
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}