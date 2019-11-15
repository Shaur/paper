package ru.comics.mq.transfer;

import java.io.Serializable;
import java.util.Objects;

public class FileUploadInfo implements Serializable {
    private String hash;
    private String name;

    public FileUploadInfo() {
    }

    public FileUploadInfo(String hash) {
        this.hash = hash;
    }

    public FileUploadInfo(String hash, String name) {
        this.hash = hash;
        this.name = name;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileUploadInfo that = (FileUploadInfo) o;
        return Objects.equals(hash, that.hash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hash);
    }
}
