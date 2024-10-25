package com.system.announcement.services;

import com.system.announcement.models.Announcement;
import com.system.announcement.models.File;
import com.system.announcement.repositories.FileRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class FileService {

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public Set<File> createObjectsFile(Set<String> paths, Announcement announcement){
        Set<File> files = new HashSet<>();
        for(String path : paths) files.add(fileRepository.save(new File(path, announcement)));
        return files;
    }
}
