package at.fh.bac.Model;

import javafx.collections.ObservableList;

import java.io.File;
import java.util.List;

public class FileListModel {

    private List<File> fileList;

    public FileListModel() {
    }

    public FileListModel(List<File> fileList) {
        this.fileList = fileList;
    }

    public List<File> getFileList() {
        return fileList;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }

    @Override
    public String toString() {
        return "FileListModel{" +
                "fileList=" + fileList +
                '}';
    }
}
