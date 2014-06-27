package at.ac.tuwien.inso.tl.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.data.jpa.domain.Specification;

import at.ac.tuwien.inso.tl.model.Customer;

public class PropertySpecifiations {
	
	public static Specification<Customer> searchMatch(final Customer input) {
		
		return new Specification<Customer>() {
			
			@Override
			public Predicate toPredicate(Root<Customer> customerRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				List<Predicate> preds = new ArrayList<Predicate>();
				
				Predicate city_pred = null;
				Predicate country_pred = null;
				Predicate dateofbirth_pred = null;
				Predicate email_pred = null;
				Predicate firstname_pred = null;
				Predicate isfemale_pred = null;
				Predicate lastname_pred = null;
				Predicate points_pred = null;
				Predicate postalcode_pred = null;
				Predicate street_pred = null;
				Predicate telephonenumber_pred = null;
				Predicate title_pred = null;
				
				if (input.getCity() != null) {
					
					Path<String> city_path = customerRoot.get("city");
					
					city_pred = cb.like(cb.lower(city_path), "%" + input.getCity().toLowerCase() + "%");
					preds.add(city_pred);
					
				}
				
				if (input.getCountry() != null) {
					
					Path<String> country_path = customerRoot.get("country");
					
					country_pred = cb.like(cb.lower(country_path), "%" + input.getCountry().toLowerCase() + "%");
					preds.add(country_pred);
					
				}
				
				if (input.getDateOfBirth() != null) {
					
					Expression<Date>  dateofbirth_exp = customerRoot.get("dateOfBirth");
					dateofbirth_pred = cb.equal(dateofbirth_exp, new java.sql.Date(input.getDateOfBirth().getTime()));
					preds.add(dateofbirth_pred);
					
				}
				
				if (input.getEmail() != null) {
					
					Path<String> email_path = customerRoot.get("email");
					
					email_pred = cb.like(cb.lower(email_path), "%" + input.getEmail().toLowerCase() + "%");
					preds.add(email_pred);
					
				}
				
				if (input.getFirstname() != null) {
					
					Path<String> firstname_path = customerRoot.get("firstname");
					
					firstname_pred = cb.like(cb.lower(firstname_path), "%" + input.getFirstname().toLowerCase() + "%");
					preds.add(firstname_pred);
					
				}
				
				if (input.getIsFemale() != null) {
					
					isfemale_pred = cb.equal(customerRoot.get("isFemale"), input.getIsFemale());
					preds.add(isfemale_pred);
					
				}
				
				if (input.getLastname() != null) {
					
					Path<String> lastname_path = customerRoot.get("lastname");
					
					lastname_pred = cb.like(cb.lower(lastname_path), "%" + input.getLastname().toLowerCase() + "%");
					preds.add(lastname_pred);
					
				}
				
				// Die Punkte sind hier als LowerBound zu verstehen.
				if (input.getPoints() != null) {
					
					Expression<Integer>  points_exp = customerRoot.get("points");
					points_pred = cb.ge(points_exp, input.getPoints());
					preds.add(points_pred);
					
				}
				
				if (input.getPostalcode() != null) {
					
					postalcode_pred = cb.equal(customerRoot.get("postalcode"), input.getPostalcode());
					preds.add(postalcode_pred);
					
				}
				
				if (input.getStreet() != null) {
					
					Path<String> street_path = customerRoot.get("street");
					
					street_pred = cb.like(cb.lower(street_path), "%" + input.getStreet().toLowerCase() + "%");
					preds.add(street_pred);
					
				}
				
				if (input.getTelephonenumber() != null) {
					
					Path<String> telephonenumber_path = customerRoot.get("telephonenumber");
					
					telephonenumber_pred = cb.like(telephonenumber_path, "%" + input.getTelephonenumber() + "%");
					preds.add(telephonenumber_pred);
					
				}
				
				if (input.getTitle() != null) {
					
					title_pred = cb.equal(customerRoot.get("title"), input.getTitle());
					preds.add(title_pred);
					
				}
	
				Predicate[] preds_ar = preds.toArray(new Predicate[preds.size()]);
				
				return cb.and(preds_ar);
				
			}
		};
	}
}
