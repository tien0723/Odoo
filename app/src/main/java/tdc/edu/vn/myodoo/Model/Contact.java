package tdc.edu.vn.myodoo.Model;

public class Contact {
    Object  city,name,email,image_128;
    String id;

    public Contact() {
    }

    public Object getImage_128() {
        return image_128;
    }

    public void setImage_128(Object image_128) {
        this.image_128 = image_128;
    }

    public Contact(Object city, Object name, Object email, Object image_128, String id) {
        this.city = city;
        this.name = name;
        this.email = email;
        this.image_128 = image_128;
        this.id = id;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "{" +
                "city=" + city +
                ", name=" + name +
                ", email=" + email +
                ", image_1920=" + image_128 +
                ", id=" + id +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
