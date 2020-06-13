package kr.ac.hansung.cse.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import kr.ac.hansung.cse.model.Products;

public interface ProductRepository extends CrudRepository<Products, Long> {
	List<Products> findByCategory(String category);
}