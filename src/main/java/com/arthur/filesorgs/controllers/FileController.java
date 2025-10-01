package com.arthur.filesorgs.controllers;

import com.arthur.filesorgs.entities.File;
import com.arthur.filesorgs.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/files")
public class FileController {

    @Autowired
    private FileService service;

    @GetMapping("/get-all")
    public ResponseEntity<List<File>> findAll(){
        return ResponseEntity.ok().body(service.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<File> insertFile(@RequestBody File file){
        try {
            File newFile = service.insertFile(file);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newFile.getId()).toUri();
            return ResponseEntity.created(uri).body(newFile);
        }catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }
}
