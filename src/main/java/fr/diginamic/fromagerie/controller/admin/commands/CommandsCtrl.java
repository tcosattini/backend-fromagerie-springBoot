package fr.diginamic.fromagerie.controller.admin.commands;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.fromagerie.authentication.LoginDto;
import fr.diginamic.fromagerie.entity.command.EntCde;
import fr.diginamic.fromagerie.service.admin.commands.CommandsService;

@RestController
@RequestMapping("admin/commands")
public class CommandsCtrl {

  @Autowired
  CommandsService commandsService;

  public CommandsCtrl(CommandsService commandsService) {
    this.commandsService = commandsService;
  }

  @GetMapping
  public ResponseEntity<Object> getCommands() {

    List<EntCde> commands = this.commandsService.findAllCommands();
    System.out.println(commands);

    return ResponseEntity.status(HttpStatus.OK).body(commands);
  }

}
