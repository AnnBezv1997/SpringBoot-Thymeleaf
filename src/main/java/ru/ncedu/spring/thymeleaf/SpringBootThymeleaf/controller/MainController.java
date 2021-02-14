package ru.ncedu.spring.thymeleaf.SpringBootThymeleaf.controller;
import java.io.*;
import java.util.*;
import eu.bitwalker.useragentutils.UserAgent;
import ru.ncedu.spring.thymeleaf.SpringBootThymeleaf.form.EmailForm;
import ru.ncedu.spring.thymeleaf.SpringBootThymeleaf.form.PersonForm;
import ru.ncedu.spring.thymeleaf.SpringBootThymeleaf.model.Person;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class MainController {
    @Autowired
    MailSender mailSender;
    private Person person = new Person();
    private List<Person> personsList = Collections.synchronizedList(new ArrayList<Person>());
    private static File persons = new File("person");

    private static void addFile(String person){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(persons,true);
            fileWriter.write(person);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                fileWriter.close();
            }catch (IOException e){
            e.printStackTrace();
            }
        }
    }
    //из properties
    @Value("${welcome.message}")
    private String message;
    @Value("${error.message}")
    private String errorMessage;
    @Value("${done.message}")
    private String doneMessage;
    @Value("${search.message}")
    private String searchMessage;
    @Value("${send.message}")
    private String sendMessage;

    @GetMapping( "/index" )
    public String index(Model model) {
        model.addAttribute("message", message);
        return "index";
    }
    @GetMapping( "/errorSearch" )
    public String errSearch(Model model) {
        model.addAttribute("searchMessage", searchMessage);
        return "errorSearch";
    }

    @GetMapping( "/person")
    public String personShow(Model model) {
        model.addAttribute("person", person);
        return "person";
    }
    @GetMapping("/personSearch" )
    public String personSearch(Model model) {
        PersonForm personForm = new PersonForm();
        model.addAttribute("personForm", personForm);
        return "personSearch";
    }
    @GetMapping("/addPerson" )
    public String addPerson(Model model) {
        PersonForm personForm = new PersonForm();
        model.addAttribute("personForm", personForm);

        return "addPerson";
    }
    @GetMapping("/sendEmail" )
    public String sendEmail(Model model) {
        EmailForm emailForm = new EmailForm();
        model.addAttribute("emailForm", emailForm);
        return "sendEmail";
    }

    @PostMapping("/addPerson")
    public String savePerson(Model model,  @ModelAttribute("personForm") @Valid PersonForm personForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            model.addAttribute("errorMessage", errorMessage);
            return "addPerson";
        }

        String firstName = personForm.getFirstName();
        String secondName = personForm.getSecondName();
        String lastName = personForm.getLastName();
        int age = personForm.getAge();
        double salary = personForm.getSalary();
        String workPlace = personForm.getWorkPlace();
        String email = personForm.getEmail();

        if (secondName !=null ) {
            Person newPerson = new Person(firstName, secondName,lastName,age,salary,workPlace,email);
            addFile(newPerson.toString());
            model.addAttribute("doneMessage", doneMessage);
        }else if (secondName  == null ){
            Person newPerson = new Person(firstName,lastName,age,salary,workPlace,email);
            addFile(newPerson.toString());
            model.addAttribute("doneMessage", doneMessage);
        } else{
            model.addAttribute("errorMessage", errorMessage);
        }
        return "addPerson";
    }

    //считывает из файла и записывает в коллекцию
    private void parseFilePerson(){
       String s="";
      try {
          Scanner in = new Scanner(persons);
          while (in.hasNext()){
              s += in.nextLine()+"\n";
          }
          in.close();
      }catch (FileNotFoundException e){
         e.printStackTrace();
      }
      String[] personString = s.split("\n");

       for (int i=0; i< personString.length;i++){
           String[] data =personString[i].split(" ");
          if(data.length == 6){  //без отчества
               String firstName = data[0];
               String lastName = data[1];
               int age = Integer.parseInt(data[2]);
               double salary = Double.parseDouble(data[3]);
               String workPlace = data[4];
               String email = data[5];
               Person person = new Person(firstName,lastName,age,salary,workPlace,email);
               personsList.add(person);
           }else if(data.length==7){   //с отчеством
               String firstName = data[0];
               String secondName = data[1];
               String lastName = data[2];
               int age = Integer.parseInt(data[3]);
               double salary = Double.parseDouble(data[4]);
               String workPlace = data[5];
               String email = data[6];
               Person person = new Person(firstName,secondName,lastName,age,salary,workPlace,email);
               personsList.add(person);
           }
       }
    }

    @PostMapping("/personSearch" )
    public String searchPerson(@ModelAttribute("personForm") PersonForm personForm,HttpServletRequest request){

        String firstName = personForm.getFirstName();
        String lastName = personForm.getLastName();
        person=search(firstName,lastName);
        if(person == null){
           return "redirect:/errorSearch";
        }
        getUserAgent(person,request);
        return "redirect:/person";
    }
    private Person search( String firstName,String lastName){
        parseFilePerson();
        synchronized (personsList) {
            return personsList.stream().filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)).findAny().orElse(null);
        }
    }
    private void getUserAgent(Person person,HttpServletRequest request){
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        person.setBrowser(userAgent.getBrowser().getName());
        person.setDate(new Date().toString());
    }

    @PostMapping("/sendEmail")
    public String triggerEmail(Model model, @ModelAttribute("emailForm")EmailForm emailForm) {
        String textEmail = emailForm.getTextEmail();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(textEmail);
        message.setTo(person.getEmail());
        message.setFrom("***********@gmail.com");//моя почта
        try {
            mailSender.send(message);
            model.addAttribute("doneMessage", doneMessage);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("sendMessage", sendMessage);
        }
        return "sendEmail";
    }

}

