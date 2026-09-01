package com.uade.tpejemplo.repository;

import com.uade.tpejemplo.model.MetaCobranza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetaCobranzaRepository extends JpaRepository<MetaCobranza, Long> {
}
