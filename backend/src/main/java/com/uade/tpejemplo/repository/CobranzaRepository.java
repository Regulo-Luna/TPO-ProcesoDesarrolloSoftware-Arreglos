package com.uade.tpejemplo.repository;

import com.uade.tpejemplo.model.Cobranza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CobranzaRepository extends JpaRepository<Cobranza, Long> {

    @Query("SELECT c FROM Cobranza c WHERE c.cuota.id.idCredito = :idCredito")
    List<Cobranza> buscarPorCredito(@Param("idCredito") Long idCredito);

    @Query("""
        SELECT COUNT(c) > 0 FROM Cobranza c
        WHERE c.cuota.id.idCredito = :idCredito AND c.cuota.id.idCuota = :idCuota
        """)
    boolean existeCobranzaDeLaCuota(@Param("idCredito") Long idCredito, @Param("idCuota") Integer idCuota);

    @Query("SELECT COUNT(c) > 0 FROM Cobranza c WHERE c.cuota.id.idCredito = :idCredito")
    boolean existeCobranzaDelCredito(@Param("idCredito") Long idCredito);

    @Query("SELECT COALESCE(SUM(c.importe), 0) FROM Cobranza c")
    Double sumarImporteTotal();
}
