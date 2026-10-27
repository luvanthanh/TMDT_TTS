package com.tmdttts.backend.repository;
import com.tmdttts.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface ProductRepository extends JpaRepository<Product,Long>{
 Optional<Product> findByProductSlug(String slug);
 List<Product> findByProductStatus(String status);
 List<Product> findByProductCategory_CategoryIdAndProductStatus(Long categoryId,String status);
 List<Product> findByProductNameContainingIgnoreCaseAndProductStatus(String keyword,String status);
}
