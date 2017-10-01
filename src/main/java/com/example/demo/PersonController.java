
package com.example.demo;

import java.util.Arrays;

import java.util.Map;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.boot.autoconfigure.mustache.MustacheAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

//import javax.persisten

@Controller
@ControllerAdvice

//@EnableConfigurationProperties(value=MustacheAutoConfiguration.class)
@RequestMapping(value = "/")
public class PersonController {

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	private RequestMappingHandlerMapping handlerMapping;

	@Value("${app.person.message}")
	private String message = "";
	
	@Value("${app.person.image_path}")
	public String _imagePath = "";

	@Value("${app.person.title}")
	private String title = "";
	

	@Qualifier("person")
	private Person person;

	@Autowired
	private PersonValidator personvalidator;

	@Autowired
	private PersonCrudRepository personcrud;
    
//	private String fileLoc = tmpdir;
//	private File createTempFile(String fileName) {
//		File temp = null;
//		try {
//			temp = File.createTempFile(fileName, ".temp");
//			
//		}
//		catch (IOException e) {
//			e.printStackTrace();
//		}
//		return temp;
//	}
	
	public String imagePath() {
		return this._imagePath+File.separator;
	}

	@InitBinder
	protected void initBinder(WebDataBinder webdata) {
		webdata.setValidator(personvalidator);
	}

	@Autowired
	public void setPersonCrud(PersonCrudRepository pcr) {
		this.personcrud = pcr;
	}

	@ModelAttribute
	public void globalModelAttrs(Map<String, Object> model) {
		model.put("message", this.message);
		model.put("title", this.title);
		model.put("personPerDetails", "");
	}

	@GetMapping(value= {"persons", "/"})
	public String index(Map<String, Object> model) {
		model.put("index", true);

		model.put("count", this.personcrud.count());

		model.put("listOfPersons", personcrud.findAll());
//		System.out.println(System.getProperty("java.io.tmpdir"));
		return "person";
	}

	@GetMapping(value= {"/person/{id}", "person/{id}/person/{id}"})
	public String findOne(@PathVariable("id") long id, Map<String, Object> model) {
		if (this.personcrud.exists(id) == false) {
			model.put("status", 404);
			model.put("error", "this id is not exit in our database");
			model.put("createPerson", true);
			return "error";
		}
		model.put("showOne", true);
		model.put("onePerson", personcrud.findOne(id));
		return "person";
	}

	@GetMapping("/person")
	public String newPerson(Map<String, Object> model) {
		model.put("createPerson", true);
		model.put("actionUrl", "/person");
		return "person";
	}
	

	@PostMapping(value="/person")
	public String createPerson(@RequestParam Map<String, String> params, Map<String, Object> model, @RequestParam("image") MultipartFile file) {
	
		try {
			
//			System.out.println(file.getContentType());
//			params.put("image", file.getBytes().toString());
			BufferedImage image = ImageIO.read(file.getInputStream());
//			File.separator+params.get("id")
		
			
			Person person_ = new Person(params);
			
			person_ = personcrud.save(person_);
			System.out.println(person_.getId()+ "emila"+ person_.getEmail());
			File temp = new File(this.imagePath()+person_.getId()+".jpg");
			System.out.println(temp.mkdirs() );
			ImageIO.write(image, "jpg", temp);
			person_.setImage(person_.getId().toString());
		} catch (Exception e) {
			model.put("status", 403);
			model.put("error", e.getMessage());
			e.printStackTrace();
			return "/error";
		}
		return "redirect:/persons";
	}

	@GetMapping("/person/{id}/edit")
	public String editPerson(@PathVariable("id") long id, Map<String, Object> model) {

		if (this.personcrud.exists(id) == false) {
			model.put("status", 404);
			model.put("error", "this id is not exit in our database");
			return "redirect:error";
		}	
		model.put("editPerson", true);
//		model.put("status", "fdajflka");
		model.put("actionUrl", "person/"+id);
        model.put("personPerDetails", this.personcrud.findOne(id));
		return "person";
	}

	@PostMapping(value= {"person/{id}"})
	public void updatePerson(@PathVariable("id") long id, @ModelAttribute Person params) {

//		System.out.println("person id: \n"+ id);
		params.setId(id);
		System.out.println(" "+ params.getEmail() +" data types \n");
		this.personcrud.save(params);
//		return "redirect:person/" + id;
	}
	
	@GetMapping("/person/{id}/delete")
	public String deletePerson(@ModelAttribute Person params) {
		this.personcrud.delete(params.getId());
		return "redirect:/persons";
	}

}