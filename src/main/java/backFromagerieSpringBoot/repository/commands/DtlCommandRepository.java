package backFromagerieSpringBoot.repository.commands;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import backFromagerieSpringBoot.entity.command.DtlCode;

public interface DtlCommandRepository extends JpaRepository<DtlCode, Integer> {
  Optional<DtlCode> findById(Integer id);

  List<DtlCode> findAll();
}
