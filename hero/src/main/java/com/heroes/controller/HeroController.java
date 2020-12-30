package com.heroes.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.heroes.exception.ResourceNotFoundException;
import com.heroes.model.Hero;
import com.heroes.repository.HeroRepository;

@RestController
@CrossOrigin(origins= "http://localhost:4200")
@RequestMapping("/hero")
public class HeroController {
	@Autowired
	HeroRepository heroRepository;
	
	@GetMapping("/heroes")
	public List<Hero> getAllHeroes() {
		return heroRepository.findAll();
	}
	
	@GetMapping("/heroes/{id}")
	public ResponseEntity<Hero> getHeroById(@PathVariable(value= "id") int id)
		throws ResourceNotFoundException {
			Hero hero = heroRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hero not found for hero with id " + id));
		return ResponseEntity.ok().body(hero);
		}
	
	@PostMapping("/heroes")
	public Hero createHero(@Valid @RequestBody Hero hero) {
		return heroRepository.save(hero);
	}
	
	@PutMapping("/heroes/{id}")
	public ResponseEntity<Hero> updateHero(@PathVariable(value= "id") int id, @Valid @RequestBody Hero heroDetails)
		throws ResourceNotFoundException {
		Hero hero = heroRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hero not found for hero with id " + id));
		
		hero.setName(heroDetails.getName());
		hero.setPower(heroDetails.getPower());
		final Hero updateHero = heroRepository.save(hero);
		return ResponseEntity.ok(updateHero);
	}
	
	@DeleteMapping("/heroes/{id}")
    public Map<String, Boolean> deleteHero(@PathVariable(value = "id") int id)
         throws ResourceNotFoundException {
        Hero hero = heroRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hero not found for hero with id " + id));

        heroRepository.delete(hero);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
