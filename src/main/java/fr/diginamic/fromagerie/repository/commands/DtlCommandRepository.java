package fr.diginamic.fromagerie.repository.commands;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.diginamic.fromagerie.entity.command.DtlCode;

import java.util.List;
import java.util.Optional;

public interface DtlCommandRepository extends JpaRepository<DtlCode, Integer> {
  Optional<DtlCode> findById(Integer id);
  List<DtlCode> findAll();
}
