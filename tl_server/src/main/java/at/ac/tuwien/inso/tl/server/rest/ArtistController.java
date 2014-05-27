package at.ac.tuwien.inso.tl.server.rest;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import at.ac.tuwien.inso.tl.dto.ArtistDto;
import at.ac.tuwien.inso.tl.dto.MessageDto;
import at.ac.tuwien.inso.tl.dto.MessageType;
import at.ac.tuwien.inso.tl.model.Artist;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.ArtistService;
import at.ac.tuwien.inso.tl.server.util.DtoToEntity;
import at.ac.tuwien.inso.tl.server.util.EntityToDto;

@RestController
@RequestMapping(value = "/artists")
public class ArtistController {
	private static final Logger LOG = Logger.getLogger(ArtistController.class);
	
	@Autowired
	private ArtistService service;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public MessageDto createArtist(@Valid @RequestBody ArtistDto artist) throws ServiceException {
		LOG.info("createArtist called.");
		
		MessageDto msg = new MessageDto();
		msg.setType(MessageType.SUCCESS);
		msg.setText(service.createArtist(DtoToEntity.convert(artist)).getId().toString());
		return msg;
	}
	
	
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, consumes = "application/json")
	public void deleteArtist(@RequestBody Integer id) throws ServiceException {
		LOG.info("deleteArtist called.");
		service.deleteArtist(id);
	}

	@RequestMapping(value = "/find", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public List<ArtistDto> findArtists(@RequestBody String firstName, @RequestBody String lastName) throws ServiceException  {
		LOG.info("findArtists called.");
		return EntityToDto.convertArtists(service.findArtists(firstName, lastName));
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public List<ArtistDto> getAllArtists() throws ServiceException  {
		LOG.info("getAllArtists called.");
		return EntityToDto.convertArtists(service.getAllArtists());
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ArtistDto getArtist(@PathVariable("id") Integer id) throws ServiceException {
		LOG.info("getArtist called.");
		
		if (id < 1) {
			throw new ServiceException("Invalid ID");
		}		
		
		return EntityToDto.convert(service.getArtist(id));
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = "application/json")
	public void updateArtist(@Valid @RequestBody ArtistDto artist) throws ServiceException {
		LOG.info("updateArtist called.");
		service.updateArtist(DtoToEntity.convert(artist));
	}
	
}
