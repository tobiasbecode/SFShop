package de.tobiasbecode.sfshop.products.data.repository;

import de.tobiasbecode.sfshop.products.data.domain.Products;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProductsRepository extends CrudRepository<Products, Long> {

}
