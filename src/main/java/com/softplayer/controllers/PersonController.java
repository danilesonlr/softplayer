package com.softplayer.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.softplayer.domain.Person;
import com.softplayer.service.PersonService;

@Controller
@RequestMapping("/person/v1")
public class PersonController {

	@Autowired
	private PersonService personService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView savePerson(@Validated Person person, Errors errors) throws Exception {
		ModelAndView mv = new ModelAndView("CadastroPessoa");
		if (errors.hasErrors()) {
			return mv;
		}
		try {
			personService.save(person);
		} catch (Exception e) {
			mv.addObject("erro", e.getMessage());
			return mv;
		}

		mv.addObject("mensagem", "Pessoa salva com sucesso!!!");
		return mv;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView saveUpdate(@Validated Person person, Errors errors) throws Exception {
		ModelAndView mv = new ModelAndView("AtualizarPessoa");
		if (errors.hasErrors()) {
			return mv;
		}
		try {
			personService.update(person);
		} catch (Exception e) {
			mv.addObject("erro", e.getMessage());
			return mv;
		}
		mv.addObject("mensagem", "Pessoa atualizada com sucesso!!!");
		return mv;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		List<Person> PersonList = personService.findAll();
		ModelAndView mv = new ModelAndView("PesquisaPessoas");
		mv.addObject("persons", PersonList);
		return mv;
	}

	@RequestMapping(value = "/delete/{codigo}", method = RequestMethod.GET)
	public String excluir(@PathVariable("codigo") Long codigo, RedirectAttributes attributes) throws Exception {
		personService.delete(codigo);
		attributes.addFlashAttribute("mensagem", "Título excluído com sucesso!");
		return "redirect:/person/v1/list";
	}

	@RequestMapping(value = "/findById/{codigo}", method = RequestMethod.GET)
	public ModelAndView updatePerson(@PathVariable("codigo") Long codigo) {
		Person storedperson = personService.findByID(codigo);
		ModelAndView mv = new ModelAndView("AtualizarPessoa");
		mv.addObject("person", storedperson);

		return mv;
	}
}
