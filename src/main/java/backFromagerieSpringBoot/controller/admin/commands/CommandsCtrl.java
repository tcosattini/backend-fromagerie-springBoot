package backFromagerieSpringBoot.controller.admin.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backFromagerieSpringBoot.service.admin.commands.CommandsService;
import backFromagerieSpringBoot.service.authentication.AuthenticationService;

@RestController
@RequestMapping("admin/commands")
public class CommandsCtrl {

  @Autowired
  CommandsService commandsService;

  public CommandsCtrl(CommandsService commandsService) {
    this.commandsService = commandsService;
  }

  // @PostMapping
  // public ResponseEntity<String> getAllCommands() {
  // return commandsService.
  // }

}
