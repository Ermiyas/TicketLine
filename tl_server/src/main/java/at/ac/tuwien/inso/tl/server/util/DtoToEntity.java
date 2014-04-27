package at.ac.tuwien.inso.tl.server.util;

import at.ac.tuwien.inso.tl.dto.NewsDto;
import at.ac.tuwien.inso.tl.model.News;

public class DtoToEntity {

	public static News convert(NewsDto newsDto) {
		News news = new News();
		news.setTitle(newsDto.getTitle());
		news.setNewsText(newsDto.getNewsText());
		return news;
	}
}
