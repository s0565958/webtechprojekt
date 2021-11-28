package de.htwberlin.webtech.service;


import de.htwberlin.webtech.persistence.AutorEntity;
import de.htwberlin.webtech.persistence.AutorRepository;
import de.htwberlin.webtech.web.api.Autor;
import de.htwberlin.webtech.web.api.AutorManipulationRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public List<Autor> findAll(){
        List<AutorEntity> autor = autorRepository.findAll();
        return autor.stream()
                .map(this::transformEntity)
                .collect(Collectors.toList());
    }

    public de.htwberlin.webtech.web.api.Autor findById(Long id){
        var autorEntity = autorRepository.findById(id);
        return autorEntity.map(this::transformEntity).orElse(null);

    }

    public de.htwberlin.webtech.web.api.Autor create(AutorManipulationRequest request){
        var autorEntity = new AutorEntity(request.getUsername(), request.getPassword(), request.getEmail());
        autorEntity = autorRepository.save(autorEntity);
        return transformEntity(autorEntity);
    }

    public Autor update(Long id, AutorManipulationRequest request){
        var autorEntityOptional = autorRepository.findById(id);
        if (autorEntityOptional.isEmpty()){
            return null;
        }

        var autorEntity = autorEntityOptional.get();
        autorEntity.setUsername(request.getUsername());
        autorEntity.setPassword(request.getPassword());
        autorEntity.setEmail(request.getEmail());
        autorEntity = autorRepository.save(autorEntity);

        return transformEntity(autorEntity);
    }

    public boolean deleteById(Long id){
        if (!autorRepository.existsById(id)){
            return false;
        }
        autorRepository.deleteById(id);
        return true;
    }

    private de.htwberlin.webtech.web.api.Autor transformEntity(AutorEntity userEntity){
        return new de.htwberlin.webtech.web.api.Autor(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getEmail()
        );

    }
}