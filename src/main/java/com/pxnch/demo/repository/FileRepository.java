package com.pxnch.demo.repository;

import com.pxnch.demo.models.File;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<File, UUID> {

}
