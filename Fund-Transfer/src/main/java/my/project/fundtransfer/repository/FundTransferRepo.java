package my.project.fundtransfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import my.project.fundtransfer.entity.FundTransferEntity;

@Repository
public interface FundTransferRepo extends JpaRepository<FundTransferEntity, Long> {

}
