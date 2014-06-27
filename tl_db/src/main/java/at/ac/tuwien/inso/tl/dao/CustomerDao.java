package at.ac.tuwien.inso.tl.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import at.ac.tuwien.inso.tl.model.Customer;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Integer>, JpaSpecificationExecutor<Customer>{

}
