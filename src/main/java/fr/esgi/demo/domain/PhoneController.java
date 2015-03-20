package fr.esgi.demo.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/phone")
public class PhoneController {

    @Autowired
    PhoneRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(PhoneController.class);

    ArrayList<Phone> PhoneArrayList;

    private int getPhonesCalled, getIsStolenByPhoneId, putIsStolenBySerialNumber, postPhone;

    @PostConstruct
    public void init() {
        Phone savedPhone1 = new Phone();
        Phone savedPhone2 = new Phone();

        savedPhone1.setFirstName("Jean");
        savedPhone1.setLastName("Reno");
        savedPhone1.setSerialNumber("5F55A01B22G312Z31B");
        savedPhone1.setNumber("555-0223");
        savedPhone1.setStolen(false);

        savedPhone2.setFirstName("Chris");
        savedPhone2.setLastName("Dupuis");
        savedPhone2.setSerialNumber("5F551B22G312Z3A01B2");
        savedPhone2.setNumber("555-4356");
        savedPhone2.setStolen(true);

        repository.save(savedPhone1);
        repository.save(savedPhone2);

        PhoneArrayList = new ArrayList<>();
    }

    @RequestMapping(
            method = RequestMethod.GET
    )
    @ResponseStatus(HttpStatus.OK) // 200
    public ArrayList<Phone> getPhones(){
        logger.info("GET /phone called {} times", ++getPhonesCalled);
        PhoneArrayList.clear();
        for(Phone phone : repository.findAll()){
            if(phone.isStolen()){
                phone.setFirstName("*********");
                phone.setLastName("*********");
                PhoneArrayList.add(phone);
            }
        }
        return PhoneArrayList;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{phoneId}"
    )
    @ResponseStatus(HttpStatus.FOUND) //
    public boolean getIsStolenByPhoneId(@PathVariable final Long phoneId){
        logger.info("GET /phone/{phoneId} called {} times", ++getIsStolenByPhoneId);
        Phone phone = repository.findById(phoneId);
        if(phone == null){
            throw new PhoneNotFoundException(); //404
        }else{
            return phone.isStolen();
        }
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/{serialNumber}"
    )
    @ResponseStatus(HttpStatus.OK)
    public Phone putIsStolenBySerialNumber(@PathVariable String serialNumber){
        logger.info("PUT /phone/{serialNumber} called {} times", ++putIsStolenBySerialNumber);
        Phone phone = repository.findBySerialNumber(serialNumber);
        if(phone == null){
            throw new PhoneNotFoundException();
        }else{
            phone.setStolen(true);
            phone.setFirstName("*********");
            phone.setLastName("*********");
            return phone;
        }
    }

    @RequestMapping(
            method = RequestMethod.POST
    )
    @ResponseStatus(HttpStatus.CREATED)
    public Phone postPhone(Phone phone){
        logger.info("POST /phone} called {} times", ++postPhone);
        repository.save(phone);
        phone.setFirstName("*********");
        phone.setLastName("*********");
        return phone;
    }

}
