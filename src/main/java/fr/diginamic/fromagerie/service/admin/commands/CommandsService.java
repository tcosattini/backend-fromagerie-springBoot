package fr.diginamic.fromagerie.service.admin.commands;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import fr.diginamic.fromagerie.entity.command.EntCde;
import fr.diginamic.fromagerie.repository.ActiveUserRepository;
import fr.diginamic.fromagerie.repository.commands.EntCdeRepository;

@Service
public class CommandsService {

  @Autowired
  EntCdeRepository entCdeRepository;

  public List<EntCde> findAllCommands() {

    new ResponseEntity<>("Email already exist", HttpStatus.BAD_REQUEST);
    return entCdeRepository.findAll();
  }

}
