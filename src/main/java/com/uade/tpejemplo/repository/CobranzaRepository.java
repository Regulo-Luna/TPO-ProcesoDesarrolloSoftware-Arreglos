package com.uade.tpejemplo.repository;

import com.uade.tpejemplo.model.Cobranza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CobranzaRepository extends JpaRepository<Cobranza, Long> {

    List<Cobranza> findByCuotaIdIdCredito(Long idCredito);

    boolean existsByCuotaIdIdCreditoAndCuotaIdIdCuota(Long idCredito, Integer idCuota);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Cobranza c WHERE c.cuota.credito.id = :id")
    boolean existsByCreditoId(@Param("id") Long idCredito);

    @Query("SELECT COALESCE(SUM(c.importe), 0) FROM Cobranza c")
    Double sumMontoTotal();
    
}
