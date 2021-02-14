package ru.ncedu.spring.thymeleaf.SpringBootThymeleaf.form;
import javax.validation.constraints.*;

public class PersonForm {

    @Pattern(regexp = "^([А-Я]{1}[а-яё]{1,30}|[A-Z]{1}[a-z]{1,30})$",message = "Incorrect!")
    private String firstName;
    private String secondName;
    @Pattern(regexp = "^([А-Я]{1}[а-яё]{1,30}|[A-Z]{1}[a-z]{1,30})$",message = "Incorrect!")
    private String lastName;
    @NotNull
    @Min(1)
    private int age;
    @NotNull
    @Min(1)
    private double salary;
    @NotEmpty
    private String workPlace;
    @NotEmpty
    @Email(message = "Incorrect!")
    private String email;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
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

    public void setSalary(Double salary) {
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

}
