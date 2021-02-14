package ru.ncedu.spring.thymeleaf.SpringBootThymeleaf.model;

public class Person {

    private String firstName;
    private String secondName;
    private String lastName;
    private int age;
    private double salary;
    private String workPlace;
    private String email;
    private String browser;
    private String date;

    public Person() {

    }
    //если вдруг нет отчества
    public Person(String firstName, String lastName,int age,double salary, String workPlace, String email ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.salary = salary;
        this.workPlace = workPlace;
        this.email = email;
    }
    public Person(String firstName,String secondName, String lastName,int age,double salary, String workPlace, String email ) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.age = age;
        this.salary = salary;
        this.workPlace = workPlace;
        this.email = email;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String middleName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        if(this.secondName != null) {
            return firstName + " " + secondName + " " + lastName + " "+age+" " + salary + " " + workPlace + " " + email + "\n";
        }else
            return  firstName + " " + lastName + " "+age+" " + salary + " " + workPlace + " " + email+"\n";
    }
}
