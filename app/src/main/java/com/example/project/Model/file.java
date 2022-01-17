package com.example.project.Model;

public class file {
    private String fileId ;
    private String matiereId;
    private String filesPath;
    private String fileTitle;

    public file(String fileId, String matiereId, String filesPath, String fileTitle) {
        this.fileId = fileId;
        this.matiereId = matiereId;
        this.filesPath = filesPath;
        this.fileTitle = fileTitle;
    }

    public file(String fileId,String filesPath, String fileTitle) {
        this.fileId = fileId;
        this.filesPath = filesPath;
        this.fileTitle = fileTitle;
    }
    public file() {
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getMatiereId() {
        return matiereId;
    }

    public void setMatiereId(String matiereId) {
        this.matiereId = matiereId;
    }

    public String getFilesPath() {
        return filesPath;
    }

    public void setFilesPath(String filesPath) {
        this.filesPath = filesPath;
    }

    public String getFileTitle() {
        return fileTitle;
    }

    public void setFileTitle(String fileTitle) {
        this.fileTitle = fileTitle;
    }
}
