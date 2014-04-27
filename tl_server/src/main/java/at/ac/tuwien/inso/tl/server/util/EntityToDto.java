package at.ac.tuwien.inso.tl.server.util;

import java.util.ArrayList;
import java.util.List;

import at.ac.tuwien.inso.tl.dto.NewsDto;
import at.ac.tuwien.inso.tl.model.News;

public class EntityToDto {	
	public static List<NewsDto> convertNews(List<News> news){
		List<NewsDto> ret = new ArrayList<NewsDto>();
		if(null != news){
			for(News n : news){
				NewsDto dto = convert(n);
				
				ret.add(dto);
			}
		}
		
		return ret;
	}
	
	public static NewsDto convert(News news){
		NewsDto dto = new NewsDto();

		dto.setTitle(news.getTitle());
		dto.setNewsText(news.getNewsText());
		dto.setSubmittedOn(news.getSubmittedOn());
		
		return dto;
	}
}
