package fr.diginamic.fromagerie.repository.commands;

import fr.diginamic.fromagerie.entity.command.EntCde;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EntCdeRepository extends JpaRepository<EntCde, Integer> {
  List<EntCde> findAll();
}


