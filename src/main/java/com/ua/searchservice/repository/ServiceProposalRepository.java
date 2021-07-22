package com.ua.searchservice.repository;

import com.ua.searchservice.entity.order.ServiceProposal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceProposalRepository extends CrudRepository<ServiceProposal, Long> {
}