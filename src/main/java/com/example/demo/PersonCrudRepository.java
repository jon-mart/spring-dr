
package com.example.demo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PersonCrudRepository extends CrudRepository<Person, Long>{
    Person  save(Person p);
	Person findOne(long Id);
	Iterable<Person> findAll();
	long count();
	void delete(Person entity);
	boolean exists(long id);
}