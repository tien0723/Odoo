package tdc.edu.vn.myodoo.Model;

public class Contact {
    String  city,name,email,image_128,website,phone,mobile,zip,street,street2;
    int id;
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

    public Contact(String city, String name, String email, String image_128, String website, String phone,
                   String mobile, String zip, String street, String street2, int id, Boolean is_company) {
        this.city = city;
        this.name = name;
        this.email = email;
        this.image_128 = image_128;
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
                ", image_128='" + image_128 + '\'' +
                ", website='" + website + '\'' +
                ", phone='" + phone + '\'' +
                ", mobile='" + mobile + '\'' +
                ", zip='" + zip + '\'' +
                ", street='" + street + '\'' +
                ", street2='" + street2 + '\'' +
                ", id=" + id +
                ", is_company=" + is_company +
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

    public String getImage_128() {
        return image_128;
    }

    public void setImage_128(String image_128) {
        this.image_128 = image_128;
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
