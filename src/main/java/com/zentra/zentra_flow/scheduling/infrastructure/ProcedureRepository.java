package com.zentra.zentra_flow.scheduling.infrastructure;

import com.zentra.zentra_flow.scheduling.domain.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProcedureRepository extends JpaRepository<Procedure, UUID> {

    List<Procedure> findAllByIdInAndClinic_Id(List<UUID> ids, UUID clinicId);

}
