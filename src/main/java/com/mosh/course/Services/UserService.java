package com.mosh.course.Services;

import com.mosh.course.models.Address;
import com.mosh.course.models.Category;
import com.mosh.course.models.Product;
import com.mosh.course.models.User;
import com.mosh.course.repositories.AddressRepository;
import com.mosh.course.repositories.ProductRepository;
import com.mosh.course.repositories.ProfileRepository;
import com.mosh.course.repositories.UserRepository;
import com.mosh.course.repositories.specifications.ProductSpec;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final EntityManager entityManager;

    private final ProfileRepository profileRepository;

    private final AddressRepository addressRepository;

    private final ProductRepository productRepository;

    @Transactional
    public void showEntityStates(){
        User user = User.builder()
                .name("Erik")
                .email("erik.mail123")
                .password("123qwe")
                .build();

        if(entityManager.contains(user))
            System.out.println("Persistent");
        else
            System.out.println("Transient / Detached");
        userRepository.save(user);
        if(entityManager.contains(user))
            System.out.println("Persistent");
        else
            System.out.println("Transient / Detached");
    }

    @Transactional
    public void showRelatedEntities(){
        var profile = profileRepository.findById(2L).orElseThrow();
        System.out.println(profile.getUser().getEmail());
    }

    public void persistRelated(){
        User user = User.builder()
                .name("yulia")
                .email("jul@example33.com")
                .password("pass123")
                .build();

        Address address = Address.builder()
                .street("str")
                .state("state")
                .zip("zip")
                .city("city")
                .build();

        user.addAddress(address);

        userRepository.save(user);
    }

    @Transactional
    public void deleteRelated(){
        User user = userRepository.findById(6L).orElseThrow();
        Address address = user.getAddresses().get(0);
        user.removeAddress(address);
        userRepository.save(user);
    }

    @Transactional
    public void fillWishlist(){
        User user = userRepository.findById(2L).orElseThrow();
        for(Product product: productRepository.findAll()){
            user.addProductToWishList(product);
        }
        userRepository.save(user);
    }

    @Transactional
    public void updateProductPrices(){
        productRepository.updatePriceByCategory(BigDecimal.valueOf(10), (short)1);
    }

    public void fetchProducts(){
        var products = new Product();
        products.setName("lego");
        var matcher = ExampleMatcher
                .matching()
                .withIncludeNullValues()
                .withIgnorePaths("id")
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        var example = Example.of(products, matcher);
        productRepository.findAll(example).forEach(System.out::println);
    }

    public void fetchProductsByCriteria(){
        productRepository.findProductsByCriteria(null, null, null, (short) 2).forEach(System.out::println);
    }

    @Transactional
    public void fetchUsers(){
        var users = userRepository.findAll();
        users.forEach(u -> {
            System.out.println(u);
            u.getAddresses().forEach(System.out::println);
        });

    }

    public void getProfilesWithLoyalty(Integer threshold){
        userRepository.findByLoyalty(threshold).forEach(userSummary -> {
            System.out.println("Id: " + userSummary.getId() + " Email: " + userSummary.getEmail());
        });
    }

    public void fetchProductsBySpec(String name, BigDecimal minPrice, BigDecimal maxPrice, Short id){
        Specification<Product> spec = Specification.where(null);
        if (name != null) {
            spec = spec.and(ProductSpec.hasName(name));
        }
        if (minPrice != null) {
            spec = spec.and(ProductSpec.hasPriceGreaterThanOrEqualTo(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductSpec.hasPriceLessThanOrEqualTo(maxPrice));
        }
        if (id != null) {
            spec = spec.and(ProductSpec.hasCategoryId(id)); // ← was being ignored before!
        }

        productRepository.findAll(spec).forEach(System.out::println);
    }

    public void fetchSortedProducts(){
        Sort sort = Sort.by("name").and(
                Sort.by("price").descending()
        );

        productRepository.findAll(sort).forEach(System.out::println);
    }

    public void fetchPaginatedProducts(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Product> productsPage = productRepository.findAll(pageRequest);
        productsPage.getContent().forEach(System.out::println);
        System.out.println(productsPage.getTotalPages());;
        System.out.println(productsPage.getTotalElements());;
    }
}
