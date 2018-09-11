package com.flavor.order;

import java.util.stream.Stream;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EnableDiscoveryClient
@SpringBootApplication
public class ItemCatlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItemCatlogApplication.class, args);
	}
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
class Item {
	
	Item(){
		
	}
	public Item(String item) {
		this.name = item;
	}

	@Id
	@GeneratedValue
	private Long Id;

	private String name;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}

@RepositoryRestResource
interface ItemRepository extends JpaRepository<Item, Long> {
}

// implementing commandLimeRunner interface to overwriting run method to init
// the default data into database

@Component
class ItemInitializer implements CommandLineRunner {

	private final ItemRepository itemRepository;

	ItemInitializer(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	// Run after after application context loaded and right after spring run method
	// completed
	@Override
	public void run(String... args) throws Exception {
		Stream.of("Linking", "Puma", "Bad Boy", "Air Jordan", "Nike", "Addidas", "Reebok")
				.forEach(item -> itemRepository.save(new Item(item)));

		itemRepository.findAll().forEach(System.out::println);
	}

}