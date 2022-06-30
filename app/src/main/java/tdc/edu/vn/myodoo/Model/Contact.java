package tdc.edu.vn.myodoo.Model;

public class Contact {
    String  city,name,email,image_128;
    int id;

    public Contact(String city, String name, String email, String image_128, int id) {
        this.city = city;
        this.name = name;
        this.email = email;
        this.image_128 = image_128;
        this.id = id;
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
}
