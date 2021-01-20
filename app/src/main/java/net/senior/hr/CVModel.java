package net.senior.hr;

import java.util.ArrayList;

public class CVModel {
    private String  fullName,mobile,gender,marital,bd,address,faculty,university,gradYear,experience,photo,fileUrl;
private ArrayList<String> intrestedJobs;
    public CVModel() {
    }

    public CVModel(String fullName, String mobile, String gender, String marital, String bd, String address, String faculty, String university, String gradYear, String experience, String photo, String fileUrl) {
        this.fullName = fullName;
        this.mobile = mobile;
        this.gender = gender;
        this.marital = marital;
        this.bd = bd;
        this.address = address;
        this.faculty = faculty;
        this.university = university;
        this.gradYear = gradYear;
        this.experience = experience;
        this.photo = photo;
        this.fileUrl = fileUrl;
    }
    public CVModel(String fullName, String mobile, String gender, String marital, String bd, String address, String faculty, String university, String gradYear, String experience, String photo, String fileUrl,ArrayList<String> intrestedJobs) {
        this.fullName = fullName;
        this.mobile = mobile;
        this.gender = gender;
        this.marital = marital;
        this.bd = bd;
        this.address = address;
        this.faculty = faculty;
        this.university = university;
        this.gradYear = gradYear;
        this.experience = experience;
        this.photo = photo;
        this.fileUrl = fileUrl;
        this.intrestedJobs = intrestedJobs;
    }

    public ArrayList<String> getIntrestedJobs() {
        return intrestedJobs;
    }

    public void setIntrestedJobs(ArrayList<String> intrestedJobs) {
        this.intrestedJobs = intrestedJobs;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMarital() {
        return marital;
    }

    public void setMarital(String marital) {
        this.marital = marital;
    }

    public String getBd() {
        return bd;
    }

    public void setBd(String bd) {
        this.bd = bd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getGradYear() {
        return gradYear;
    }

    public void setGradYear(String gradYear) {
        this.gradYear = gradYear;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
