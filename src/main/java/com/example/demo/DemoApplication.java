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
		this.hogeRepo.findById(1).ifPresentOrElse(
				hoge -> jdbcAggregateTemplate.update(new hoge(hoge.id(), "bbbb", new Foobar( "FOO", "BAR"))),
				() -> jdbcAggregateTemplate.insert(new hoge(1, "aaa", new Foobar( "foo", "bar")))
		);
	}
}

interface hogeRepo extends ListCrudRepository<hoge, Integer>{}

record hoge(
		@Id
		Integer id,
		String hoge,

		Foobar foobar
){}

record Foobar(String foo, String bar){}