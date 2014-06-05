package at.ac.tuwien.inso.tl.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import at.ac.tuwien.inso.tl.model.Entry;

@Repository
public interface EntryDao extends JpaRepository<Entry, Integer>, JpaSpecificationExecutor<Entry>, EntryDaoCustom{

}