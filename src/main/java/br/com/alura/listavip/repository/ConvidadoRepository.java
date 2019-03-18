package br.com.alura.listavip.repository;

import br.com.alura.listavip.model.Convidado;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConvidadoRepository extends CrudRepository<Convidado, Long> {
    List<Convidado> findByNomeIgnoreCase(@Param("nome") String nome);
}