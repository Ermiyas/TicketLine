package at.ac.tuwien.inso.tl.dao;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import at.ac.tuwien.inso.tl.model.Customer;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Integer>{

	List<Customer> findAll(Specification<Customer> searchMatch);

}
