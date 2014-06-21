package at.ac.tuwien.inso.tl.client.util;

import at.ac.tuwien.inso.tl.dto.ContainerDto;
import at.ac.tuwien.inso.tl.dto.LocationDto;
import at.ac.tuwien.inso.tl.dto.PerformanceDto;
import at.ac.tuwien.inso.tl.dto.ShowDto;

public class PerformanceContainer {
	private LocationDto locationDto;
	private PerformanceDto performanceDto;
	private ShowDto showDto;
	
	public PerformanceContainer(ContainerDto containerDto) {
		this.locationDto = containerDto.getLocationDto();
		this.performanceDto = containerDto.getPerformanceDto();
		this.showDto = containerDto.getShowDto();
	}
	
	public String getLocationDesc() {
		return locationDto.getDescription();
	}
	
	public String getPerformanceDesc() {
		return performanceDto.getDescription();
	}
	
	public ShowDto getShow() {
		return showDto;
	}
}
