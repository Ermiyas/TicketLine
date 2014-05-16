package at.ac.tuwien.inso.tl.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import at.ac.tuwien.inso.tl.model.Employee;


@Repository
public interface EmployeeDao extends JpaRepository<Employee, Integer>{
	public List<Employee> findByUsername(String username);
}
