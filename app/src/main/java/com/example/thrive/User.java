package com.example.thrive;

public class User {

    private String id;
    private String name;
    private String email;
    private String age;
    private String weight;

    public User() {
    }

    public User(String id, String name, String email, String age, String weight) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWeight() { return weight; }

    public void setWeight(String weight) { this.weight = weight; }

}
