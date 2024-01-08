package com.pxnch.demo.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.pxnch.demo.models.Status;
import com.pxnch.demo.models.User;
import com.pxnch.demo.repository.FileRepository;
import com.pxnch.demo.repository.StatusRepository;
import com.pxnch.demo.response.ResponseFile;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.pxnch.demo.models.File;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@Service
public class FileServicesImpl implements FileServices {

    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private Status status;
    @Autowired
    private StatusRepository statusRepository;

    @Override
    public File store(MultipartFile file) throws IOException {


        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File fileEntity = File.builder()
                .name(fileName)
                .type(file.getContentType())
                .data(file.getBytes())
                .build();
        return fileRepository.save(fileEntity);
    }

    @Override
    public Optional<File> getFile(UUID id) throws FileNotFoundException {
        Optional<File> file = fileRepository.findById(id);
        if (file.isPresent()) {
            return file;
        }
        throw new FileNotFoundException();
    }

    @Override
    public List<ResponseFile> getAllFiles() {
        List<ResponseFile> files = fileRepository.findAll().stream().map(dbFiLe -> {
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("api/fileManager/")
                    .path(dbFiLe.getId().toString())
                    .toUriString();
            return ResponseFile.builder()
                    .name(dbFiLe.getName())
                    .url(fileDownloadUri)
                    .type(dbFiLe.getType())
                    .size(dbFiLe.getData().length).build();
        }).collect(Collectors.toList());
        return files;
    }
}
