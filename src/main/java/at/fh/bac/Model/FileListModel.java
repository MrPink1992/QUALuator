package at.fh.bac.Model;


import org.json.JSONObject;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FileListModel {

    private String dateOfTest;
    private List<File> fileList;
    private HashMap<File, List<Exception>> errorFileMap = new HashMap<>();
    private JSONObject testJSON = new JSONObject();

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

    public HashMap<File, List<Exception>> getErrorFileMap() {
        return errorFileMap;
    }

    public void setErrorFileMap(HashMap<File, List<Exception>> errorFileMap) {
        this.errorFileMap = errorFileMap;
    }

    public void addFileError(File file, List<Exception> exceptions){
        errorFileMap.put(file, exceptions);
    }

    public String getDateOfTest() {
        return dateOfTest;
    }

    public void setDateOfTest(String dateOfTest) {
        this.dateOfTest = dateOfTest;
    }

    public JSONObject getTestJSON() {
        return testJSON;
    }

    public void setTestJSON(JSONObject testJSON) {
        this.testJSON = testJSON;
    }
}
