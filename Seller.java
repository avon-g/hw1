/** record class */
public class Seller {

    private int id;
    private String email;
    private String city;
    private String phone;
    private String name;
    public Seller(int id, String email, String city, String phone, String name) {
        this.id = id;
        this.email = email;
        this.city = city;
        this.phone = phone;
        this.name = name;
    }

    public Seller(){};
    public int getId() {     return id;    }
    public String getEmail() {        return email;    }
    public String getCity() {        return city;    }
    public String getPhone() {        return phone;    }
    public String getName() {        return name;    }

    public void setId(int id) {        this.id = id;    }
    public void setEmail(String email) {        this.email = email;    }
    public void setCity(String city) {        this.city = city;    }
    public void setPhone(String phone) {        this.phone = phone;    }

}
