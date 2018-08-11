package by.testfirebase.dataModel;

public class User {

    private String uId;
    private String eMail;
    private String password;
    private String name;
    private String surname;
    private String gender;
    private String age;


    public User(String uId, String eMail, String password, String name, String surname, String gender, String  age) {
        this.uId = uId;
        this.eMail = eMail;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.age = age;

    }

    public User() {
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String  getAge() {
        return age;
    }

    public void setAge(String  age) {
        this.age = age;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }
}
