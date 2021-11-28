package de.htwberlin.webtech.web;


import de.htwberlin.webtech.service.AutorService;
import de.htwberlin.webtech.web.api.Autor;
import de.htwberlin.webtech.web.api.AutorManipulationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class AutorRestController {

    private final AutorService autorService;

    public AutorRestController(AutorService autorService) {
        this.autorService = autorService;
    }

    @GetMapping(path = "/api/v1/autor")
    public ResponseEntity<List<Autor>> fetchAutor(){
        return ResponseEntity.ok(autorService.findAll());
    }

    @GetMapping(path = "/api/v1/autor/{id}")
    public ResponseEntity<Autor> fetchAutorById(@PathVariable Long id){
        var autor = autorService.findById(id);
        return autor != null? ResponseEntity.ok(autor) : ResponseEntity.notFound().build();

    }

    @PostMapping(path = "/api/v1/autor")
    public ResponseEntity<Void> createAutor(@RequestBody AutorManipulationRequest request) throws URISyntaxException {
        var autor = autorService.create(request);
        URI uri = new URI("/api/v1/autor/" + autor.getId());
        return ResponseEntity.created(uri).build();

    }

    @PutMapping(path = "/api/v1/autor/{id}")
    public ResponseEntity<Autor> updateAutor(@PathVariable Long id, @RequestBody AutorManipulationRequest request){
        var autor = autorService.update(id, request);
        return autor != null? ResponseEntity.ok(autor) : ResponseEntity.notFound().build();

    }

    @DeleteMapping(path = "/api/v1/autor/{id}")
    public ResponseEntity<Void> deleteAutor(@PathVariable Long id) {
        boolean successful = autorService.deleteById(id);
        return successful ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}
