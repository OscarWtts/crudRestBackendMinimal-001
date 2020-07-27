package com.jes.crud.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jes.crud.model.Persona;
import com.jes.crud.repository.PersonaRepository;

@RestController
@RequestMapping(value = "/api") // Ruta inicial por defecto\
@CrossOrigin("*")
public class PersonaRestController {

	@Autowired
	PersonaRepository personaRepository;

	// *** LISTAR (READ) ****
	// http://localhost:8888/api/all (GET)
	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
	public List<Persona> getAll() {

		List<Persona> returnList = new ArrayList<>();
		personaRepository.findAll().forEach(obj -> returnList.add(obj));

		return returnList;
	}

	// *** CREAR Y ACTUALIZAR (CREATE AND UPDATE) ***
	// http://localhost:8888/api/save (POST)
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Persona> save(@RequestBody Persona persona) {
		Persona obj = personaRepository.save(persona);
		return new ResponseEntity<Persona>(obj, HttpStatus.OK);
	}

	// *** ELIMINAR (DELETE) ***
	// http://localhost:8888/api/delete/{id} (GET)
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Persona> delete(@PathVariable Long id) {

		Persona persona = null;
		Optional<Persona> obj = personaRepository.findById(id);
		if (obj.isPresent()) {
			persona = obj.get();
			personaRepository.deleteById(id);
			return new ResponseEntity<Persona>(persona, HttpStatus.OK);
		} else {
			return new ResponseEntity<Persona>(persona, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// http://localhost:8888/api/find/{id} (GET)
	@RequestMapping(value = "/find/{id}", method = RequestMethod.GET, produces = "application/json")
	public Persona showSave(@PathVariable("id") Long id) {
		Optional<Persona> obj = personaRepository.findById(id);
		if (obj.isPresent())
			return obj.get();

		return null;
	}
}
