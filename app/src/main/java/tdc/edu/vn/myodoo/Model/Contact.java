package tdc.edu.vn.myodoo.Model;

import java.util.ArrayList;

public class Contact {
    String  city,name,email,image_1920,website,phone,mobile,zip,street,street2,parent_id;
    int id;
    ArrayList<String> listCompany;

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public Contact(String city, String name, String email, String image_1920,
                   String website, String phone, String mobile, String zip, String street,
                   String street2, int id, ArrayList<String> listCompany, Boolean is_company, String parent_id) {
        this.city = city;
        this.name = name;
        this.email = email;
        this.image_1920 = image_1920;
        this.website = website;
        this.phone = phone;
        this.mobile = mobile;
        this.zip = zip;
        this.street = street;
        this.street2 = street2;
        this.parent_id = parent_id;
        this.id = id;
        this.listCompany = listCompany;
        this.is_company = is_company;
    }

    public ArrayList<String> getListCompany() {
        return listCompany;
    }

    public void setListCompany(ArrayList<String> listCompany) {
        this.listCompany = listCompany;
    }





    //"country_id","company_id"
    Boolean is_company;

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public Boolean getIs_company() {
        return is_company;
    }

    public void setIs_company(Boolean is_company) {
        this.is_company = is_company;
    }


    //4
    public Contact(String city, String name, String email, String image_1920, String website, String phone,
                   String mobile, String zip, String street, String street2, int id,Boolean is_company) {
        this.city = city;
        this.name = name;
        this.email = email;
        this.image_1920 = image_1920;
        this.website = website;
        this.phone = phone;
        this.mobile = mobile;
        this.zip = zip;
        this.street = street;
        this.street2 = street2;
        this.id = id;
        this.is_company = is_company;

    }

    @Override
    public String toString() {
        return "Contact{" +
                "city='" + city + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", image_128='" + image_1920 + '\'' +
                ", website='" + website + '\'' +
                ", phone='" + phone + '\'' +
                ", mobile='" + mobile + '\'' +
                ", zip='" + zip + '\'' +
                ", street='" + street + '\'' +
                ", street2='" + street2 + '\'' +
                ", id=" + id +

                '}';
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage_1920() {
        return image_1920;
    }

    public void setImage_1920(String image_1920) {
        this.image_1920 = image_1920;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Contact() {
    }



}
