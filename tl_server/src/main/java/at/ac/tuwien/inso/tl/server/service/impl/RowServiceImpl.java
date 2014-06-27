package at.ac.tuwien.inso.tl.server.service.impl;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.ac.tuwien.inso.tl.dao.RowDao;
import at.ac.tuwien.inso.tl.dao.SeatDao;
import at.ac.tuwien.inso.tl.dto.KeyValuePairDto;
import at.ac.tuwien.inso.tl.dto.RowDto;
import at.ac.tuwien.inso.tl.dto.SeatDto;
import at.ac.tuwien.inso.tl.model.Row;
import at.ac.tuwien.inso.tl.model.Seat;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.RowService;
import at.ac.tuwien.inso.tl.server.util.EntityToDto;

@Service
public class RowServiceImpl implements RowService {

	private static final Logger LOG = Logger.getLogger(RowServiceImpl.class);
	
	@Autowired
	private RowDao rowDao;
	
	@Autowired
	private SeatDao seatDao;
	
	@Override
	public Row createRow(Row row) throws ServiceException {
		LOG.info("createRow called.");
		if(row.getId() != null)
			throw new ServiceException("ID must be null.");		
		try {
			return rowDao.save(row);
		} catch (Exception e) {
			throw new ServiceException(e);
		}		
	}

	@Override
	public void deleteRow(Integer id) throws ServiceException {
		LOG.info("deleteRow called.");
		if(id == null)
			throw new ServiceException("ID must not be null.");
		try {	
			rowDao.delete(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public List<Row> findRows(Integer showID) throws ServiceException {
		LOG.info("findRows called.");
		try {	
			return rowDao.findRows(showID);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Row> getAllRows() throws ServiceException {
		LOG.info("getAllRows called.");
		try {
			return rowDao.findAll();		
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Row getRow(Integer id) throws ServiceException {
		LOG.info("getRow called.");
		if(id == null)
			throw new ServiceException("ID must not be null.");
		try {	
			return rowDao.findOne(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Row updateRow(Row row) throws ServiceException {
		LOG.info("updateRow called.");
		if(row.getId() == null)
			throw new ServiceException("ID must not be null.");
		try {	
			return rowDao.save(row);		
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	// -------------------- For Testing purposes --------------------
	
			public void setRowDao(RowDao dao){
				LOG.info("setRowDao called.");
				this.rowDao = dao;
			}
			
	   // -
}
