package com.arthur.filesorgs.services;

import com.arthur.filesorgs.entities.File;
import com.arthur.filesorgs.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class FileService {
    @Autowired
    private FileRepository repository;

    public List<File> findAll(){
        return repository.findAll();
    }

    public File insertFile(File file){
        File newFile = new File(file.getFile_name(), file.getCategory(), file.getBase64(), file.getMimetype(), file.getCreated_at(), file.getUpdated_at());
        repository.save(newFile);
        return newFile;
    }
}
