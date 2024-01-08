package com.pxnch.demo.services;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.pxnch.demo.response.ResponseFile;
import org.springframework.stereotype.Service;

import com.pxnch.demo.models.PostBody;
import com.pxnch.demo.models.User;
import com.pxnch.demo.models.*;
import com.pxnch.demo.models.*;
import org.springframework.web.multipart.MultipartFile;

public interface FileServices {
	
	File store(MultipartFile file) throws IOException;
	Optional<File> getFile (UUID id)throws FileNotFoundException;
	List<ResponseFile> getAllFiles();
}
