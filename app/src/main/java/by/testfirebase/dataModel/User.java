package by.testfirebase.dataModel;

public class User {

    private String uId;
    private String eMail;
    private String name;
    private String surname;
//    private String sex;
    private String age;

    public User(String uId, String eMail, String name, String surname, String  age) {
        this.uId = uId;
        this.eMail = eMail;
        this.name = name;
        this.surname = surname;
//        this.sex = sex;
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

//    public String getSex() {
//        return sex;
//    }
//
//    public void setSex(String sex) {
//        this.sex = sex;
//    }

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
