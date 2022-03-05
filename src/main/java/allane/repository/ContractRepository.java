package allane.repository;

import allane.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

    @Query(value = "SELECT vehicle_vehicle_id FROM Contract WHERE vehicle_vehicle_id = :vehicleId", nativeQuery = true)
    public Long getVehicleById(@Param("vehicleId") Long vehicleId);

}
