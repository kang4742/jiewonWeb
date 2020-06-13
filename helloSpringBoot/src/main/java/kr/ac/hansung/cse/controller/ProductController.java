package kr.ac.hansung.cse.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import kr.ac.hansung.cse.model.Products;
import kr.ac.hansung.cse.repo.ProductRepository;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	ProductRepository repository;

	@GetMapping("/v1/products")
	public ResponseEntity<List<Products>> getAllProducts() {
		List<Products> products = new ArrayList<>();
		try {
			repository.findAll().forEach(products::add);

			if (products.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(products, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/v1/products/{id}")
	public ResponseEntity<Products> getProductById(@PathVariable("id") long id) {
		Optional<Products> productData = repository.findById(id);

		if (productData.isPresent()) {
			return new ResponseEntity<>(productData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value = "/v1/products")
	public ResponseEntity<Products> postProduct(@RequestBody Products products) {
		try {
			Products _product = repository.save(new Products(products.getName(), products.getCategory(), products.getPrice(), products.getManufacturer(), products.getUnitInStock(), products.getDescription()));
			return new ResponseEntity<>(_product, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@DeleteMapping("/v1/products/{id}")
	public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") long id) {
		try {
			repository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@DeleteMapping("/v1/products")
	public ResponseEntity<HttpStatus> deleteAllProducts() {
		try {
			repository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}

	}

	//fetch all products of a category
	  @GetMapping(value = "/v1/products/category/{category}") public
	  ResponseEntity<List<Products>> findByCategory(@PathVariable String category) { try {
	  List<Products> products = repository.findByCategory(category);
	  
	  if (products.isEmpty()) { return new
	  ResponseEntity<>(HttpStatus.NO_CONTENT); } return new
	  ResponseEntity<>(products, HttpStatus.OK); } catch (Exception e) { return
	  new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED); } }
	 

	@PutMapping("/v1/products/{id}")
	public ResponseEntity<Products> updateProduct(@PathVariable("id") long id, @RequestBody Products products) {
		Optional<Products> productData = repository.findById(id);

		if (productData.isPresent()) {
			Products _product = productData.get();
			_product.setName(products.getName());
			_product.setCategory(products.getCategory());
			_product.setPrice(products.getPrice());
			_product.setManufacturer(products.getManufacturer());
			_product.setUnitInStock(products.getUnitInStock());
			_product.setDescription(products.getDescription());
			return new ResponseEntity<>(repository.save(_product), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}