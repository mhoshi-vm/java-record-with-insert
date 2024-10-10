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
class DemoController{

	private final JdbcAggregateTemplate jdbcAggregateTemplate;
	private final HogeRepo hogeRepo;

	public DemoController(JdbcAggregateTemplate jdbcAggregateTemplate, com.example.demo.HogeRepo hogeRepo) {
		this.jdbcAggregateTemplate = jdbcAggregateTemplate;
		this.hogeRepo = hogeRepo;
	}

	@GetMapping
	List<Hoge> getter(){
		return hogeRepo.findAll();
	}

	@PostMapping
	void poster(){
		this.hogeRepo.findById(1).ifPresentOrElse(
				hoge -> jdbcAggregateTemplate.update(new Hoge(hoge.id(), "bbbb", new Foobar( "FOO", "BAR"))),
				() -> jdbcAggregateTemplate.insert(new Hoge(1, "aaa", new Foobar( "foo", "bar")))
		);
	}
}

interface HogeRepo extends ListCrudRepository<Hoge, Integer>{}

record Hoge(
		@Id
		Integer id,
		String hoge,

		Foobar foobar
){}

record Foobar(String foo, String bar){}