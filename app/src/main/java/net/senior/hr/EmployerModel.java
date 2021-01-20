package net.senior.hr;

public class EmployerModel {
    private String email;
    private String companyName;
    private String mobile;
    private String address;
    private String photo;
    private String id;
    private String hrName;
    private String regisdat;


    public EmployerModel() {
    }

    public EmployerModel(String email, String companyName, String mobile, String address, String photo, String id, String hrName, String regisdat) {
        this.email = email;
        this.companyName = companyName;
        this.mobile = mobile;
        this.address = address;
        this.photo = photo;
        this.id = id;
        this.hrName = hrName;
        this.regisdat = regisdat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHrName() {
        return hrName;
    }

    public void setHrName(String hrName) {
        this.hrName = hrName;
    }

    public String getRegisdat() {
        return regisdat;
    }

    public void setRegisdat(String regisdat) {
        this.regisdat = regisdat;
    }
}
