package fr.diginamic.fromagerie.controller.admin.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.fromagerie.service.admin.commands.CommandsService;

@RestController
@RequestMapping("admin/commands")
public class CommandsCtrl {

  @Autowired
  CommandsService commandsService;

  public CommandsCtrl(CommandsService commandsService) {
    this.commandsService = commandsService;
  }

}