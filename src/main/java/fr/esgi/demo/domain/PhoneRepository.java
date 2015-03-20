package fr.esgi.demo.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends CrudRepository<Phone, Long> {

    Phone findById(long id);

    Phone findBySerialNumber(String serialNumber);



}
