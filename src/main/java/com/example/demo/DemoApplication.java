package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

@RestController
class demoController{

	private final JdbcAggregateTemplate jdbcAggregateTemplate;
	private final hogeRepo hogeRepo;

	public demoController(JdbcAggregateTemplate jdbcAggregateTemplate, com.example.demo.hogeRepo hogeRepo) {
		this.jdbcAggregateTemplate = jdbcAggregateTemplate;
		this.hogeRepo = hogeRepo;
	}

	@GetMapping
	List<hoge> getter(){
		return hogeRepo.findAll();
	}

	@PostMapping
	void poster(){
		jdbcAggregateTemplate.insert(new hoge(1, "aaa"));
		this.hogeRepo.save(new hoge(1, "bbbb"));
	}
}

interface hogeRepo extends ListCrudRepository<hoge, Integer>{}

/*
interface WithInsert<T>{
	T insert(T t);
}

class WithInsertImpl<T> implements WithInsert<T>{
	private final JdbcAggregateTemplate template;

	public WithInsertImpl(JdbcAggregateTemplate template) {
		this.template = template;
	}

	@Override
	public T insert(T t) {
		return template.insert(t);
	}
}*/

record hoge(
		@Id
		Integer id,
		String hoge
){}