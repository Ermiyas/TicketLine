package at.ac.tuwien.inso.tl.server.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.ac.tuwien.inso.tl.dao.ArtistDao;
import at.ac.tuwien.inso.tl.model.Artist;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.ArtistService;

@Service
public class ArtistServiceImpl implements ArtistService {

	private static final Logger LOG = Logger.getLogger(ArtistServiceImpl.class);
	
	@Autowired
	private ArtistDao artistDao;
	
	@Override
	public Artist createArtist(Artist artist) throws ServiceException {
		LOG.info("createArtist called.");
		if(artist.getId() != null)
			throw new ServiceException("ID must be null.");		
		try {
			return artistDao.save(artist);
		} catch (Exception e) {
			throw new ServiceException(e);
		}		
	}

	@Override
	public void deleteArtist(Integer id) throws ServiceException {
		LOG.info("deleteArtist called.");
		if(id == null)
			throw new ServiceException("ID must not be null.");
		try {	
			artistDao.delete(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Artist> findArtists(String firstName, String lastName) throws ServiceException  {
		LOG.info("findArtists called.");
		try {	
			return artistDao.findArtists(firstName, lastName);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Artist> getAllArtists() throws ServiceException  {
		LOG.info("getAllArtists called.");
		try {
			return artistDao.findAll();		
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Artist getArtist(Integer id) throws ServiceException {
		LOG.info("getArtist called.");
		if(id == null)
			throw new ServiceException("ID must not be null.");
		try {	
			return artistDao.findOne(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Artist updateArtist(Artist artist) throws ServiceException {
		LOG.info("updateArtist called.");
		if(artist.getId() == null)
			throw new ServiceException("ID must not be null.");
		try {	
			return artistDao.save(artist);		
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	// -------------------- For Testing purposes --------------------
	
			public void setArtistDao(ArtistDao dao){
				LOG.info("setSeatDao called.");
				this.artistDao = dao;
			}
			
	   // -
}
