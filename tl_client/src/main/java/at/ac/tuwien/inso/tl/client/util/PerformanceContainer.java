package at.ac.tuwien.inso.tl.client.util;

import at.ac.tuwien.inso.tl.dto.ShowContainerDto;
import at.ac.tuwien.inso.tl.dto.ShowDto;

public class PerformanceContainer {
	private ShowDto showDto;
	private String locationDesc;
	private String performanceDesc;
	
	
	public PerformanceContainer(ShowContainerDto containerDto) {
		this.showDto = containerDto.getShowDto();
		this.locationDesc = containerDto.getLocationDesc();
		this.performanceDesc = containerDto.getPerformanceDesc();
	}
	
	public String getLocationDesc() {
		return locationDesc;
	}
	
	public String getPerformanceDesc() {
		return performanceDesc;
	}
	
	public ShowDto getShow() {
		return showDto;
	}
}
