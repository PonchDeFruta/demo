package com.pxnch.demo.services;


import com.pxnch.demo.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pxnch.demo.models.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatusServiceImpl {

	@Autowired
	private Status statusFile;
	@Autowired
	private StatusRepository statusRepository;



	public void setEstatusService(int estatus, String nameServicio) {
		if (estatus == 1) {
			Status statusEntity = Status.builder()
				.idStatus(null)
				.status(estatus)
				.statusDescription(nameServicio)
				.statusName("online")
				.timeStmap(Timestamp.valueOf(LocalDateTime.now()))
					.build();
			statusRepository.save(statusEntity);

		} else if (estatus == 0) {
			Status statusEntity = Status.builder()
					.idStatus(null)
					.status(estatus)
					.statusDescription(nameServicio)
					.statusName("offline")
					.timeStmap(Timestamp.valueOf(LocalDateTime.now()))
					.build();
			statusRepository.save(statusEntity);

		} else if (estatus == 2) {
			Status statusEntity = Status.builder()
					.idStatus(null)
					.status(estatus)
					.statusDescription(nameServicio)
					.statusName("transfiriendo")
					.timeStmap(Timestamp.valueOf(LocalDateTime.now()))
					.build();
			statusRepository.save(statusEntity);
		}
	}

	public List<Status> getAllStatus() {
		return statusRepository.findAll();
	}



	

}