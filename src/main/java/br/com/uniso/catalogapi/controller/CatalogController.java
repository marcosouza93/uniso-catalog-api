package br.com.uniso.catalogapi.controller;

import br.com.uniso.catalogapi.model.Product;
import br.com.uniso.catalogapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    private ProductRepository repository;

    @GetMapping
    public ResponseEntity<List<Product>> findAllProducts() {
        return ResponseEntity.ok().body(repository.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> findById(@PathVariable("id") final Long id) {
        return repository.findById(id)
                .map(response -> ResponseEntity.ok().body(response))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Product> save(@RequestBody final Product product) {
        return new ResponseEntity<>(repository.save(product), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Product> update(@PathVariable("id") final Long id, @RequestBody final Product product) {
        Optional<Product> response = repository.findById(id);
        if (response.isPresent()) {
            product.setId(id);
            return ResponseEntity.ok().body(repository.save(product));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") final Long id){
        repository.deleteById(id);
    }

}
