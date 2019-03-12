package es.carlop.petclinic.services;

import es.carlop.petclinic.model.Owner;

import java.util.List;

public interface OwnerService extends CrudService<Owner, Long> {

    Owner findByLastName(String lastName);

    List<Owner> findAllByLastNameLikeIgnoreCase(String lastName);

}