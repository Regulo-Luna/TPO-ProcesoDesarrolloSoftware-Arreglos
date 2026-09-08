package com.uade.tpejemplo.repository;

import com.uade.tpejemplo.model.Cuota;
import com.uade.tpejemplo.model.CuotaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuotaRepository extends JpaRepository<Cuota, CuotaId> {

    @Query("SELECT c FROM Cuota c WHERE c.id.idCredito = :idCredito ORDER BY c.id.idCuota")
    List<Cuota> buscarPorCredito(@Param("idCredito") Long idCredito);
}
