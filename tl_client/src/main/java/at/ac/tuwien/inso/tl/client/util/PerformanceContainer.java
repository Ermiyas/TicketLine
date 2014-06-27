package at.ac.tuwien.inso.tl.client.util;

import at.ac.tuwien.inso.tl.dto.ContainerDto;
import at.ac.tuwien.inso.tl.dto.LocationDto;
import at.ac.tuwien.inso.tl.dto.PerformanceDto;
import at.ac.tuwien.inso.tl.dto.ShowDto;

public class PerformanceContainer {
	private ShowDto showDto;
	private LocationDto locationDto;
	private PerformanceDto performanceDto;
	
	
	public PerformanceContainer(ContainerDto containerDto) {
		this.showDto = containerDto.getShowDto();
		this.locationDto = containerDto.getLocationDto();
		this.performanceDto = containerDto.getPerformanceDto();
	}
	
	public ShowDto getShow() {
		return showDto;
	}
	
	public LocationDto getLocation() {
		return locationDto;
	}
	
	public PerformanceDto getPerformance() {
		return performanceDto;
	}
}
