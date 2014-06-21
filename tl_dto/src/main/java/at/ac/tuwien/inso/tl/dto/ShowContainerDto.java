package at.ac.tuwien.inso.tl.dto;

public class ShowContainerDto {
	private ShowDto showDto;
	private String performanceDesc;
	private String locationDesc;
	
	public ShowDto getShowDto() {
		return showDto;
	}
	
	public void setShowDto(ShowDto showDto) {
		this.showDto = showDto;
	}
	
	public String getPerformanceDesc() {
		return performanceDesc;
	}
	
	public void setPerformanceDesc(String performanceDesc) {
		this.performanceDesc = performanceDesc;
	}
	
	public String getLocationDesc() {
		return locationDesc;
	}
	
	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}
	
	@Override
	public String toString() {
		return "ShowContainerDto [showDto=" + showDto + ", performance=" + performanceDesc + ", location=" + locationDesc + "]";
	}
}
