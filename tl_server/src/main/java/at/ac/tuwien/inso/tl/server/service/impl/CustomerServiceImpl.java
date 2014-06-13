package at.ac.tuwien.inso.tl.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.ac.tuwien.inso.tl.dao.CustomerDao;
import at.ac.tuwien.inso.tl.dao.PropertySpecifiations;
import at.ac.tuwien.inso.tl.model.Customer;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerDao customerDao;

	@Override
	public Customer getById(Integer id) throws ServiceException {
		
		try {
			
			return customerDao.findOne(id);
			
		} catch (Exception e) {
			
			throw new ServiceException(e);
			
		}
	}

	@Override
	public Customer create(Customer customer) throws ServiceException {
		
		try {
			
			return customerDao.save(customer);
			
		} catch (Exception e) {
			
			throw new ServiceException(e);
			
		}
	}

	@Override
	public List<Customer> find(Customer customer) throws ServiceException {
		
		List<Customer> results = null;

		results = customerDao.findAll(PropertySpecifiations.searchMatch(customer));

		return results;
		
	}

	@Override
	public Customer update(Customer customer) throws ServiceException {
		
		try {
			
			return customerDao.save(customer);
			
		} catch (Exception e) {
			
			throw new ServiceException(e);
			
		}
	}

	@Override
	public void deleteById(Integer id) throws ServiceException {
		
		try {
			
			customerDao.delete(id);
			
		} catch (Exception e) {
			
			throw new ServiceException(e);
			
		}
		
	}

	@Override
	public List<Customer> getAll() throws ServiceException {
		
		try {
			
			return customerDao.findAll();
			
		} catch (Exception e) {
			
			throw new ServiceException(e);
			
		}
	}

	// Zum Testen.
	public void setCustomerDao(CustomerDao dao) {
		
		this.customerDao = dao;
		
	}

}