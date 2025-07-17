package com.mosh.course;

import com.mosh.course.Services.AddressService;
import com.mosh.course.Services.ProductCategoryService;
import com.mosh.course.Services.UserService;
import com.mosh.course.models.User;
import com.mosh.course.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.math.BigDecimal;

@SpringBootApplication
public class CourseApplication {

	public static void main(String[] args) {


		ConfigurableApplicationContext context = SpringApplication.run(CourseApplication.class, args);
		UserRepository repository = context.getBean(UserRepository.class);
		var service = context.getBean(UserService.class);
		AddressService addressService = context.getBean(AddressService.class);
		ProductCategoryService productCategoryService = context.getBean(ProductCategoryService.class);

		//service.fetchProductsByCriteria();
		//service.fetchPaginatedProducts(0, 3);
		//service.fetchSortedProducts();
		service.fetchProductsBySpec(null, null, null, (short) 1);
		//service.fetchProductsByCriteria();
		//service.getProfilesWithLoyalty(2);
		//service.updateProductPrices();
		//productCategoryService.deleteProduct();
		//service.fillWishlist();
		//productCategoryService.saveNewProductToExistingCategory();
		//service.deleteRelated();
		//service.persistRelated();
		//service.showRelatedEntities();
		//addressService.showAddressEagerAndLazyLoading();
	}

}
