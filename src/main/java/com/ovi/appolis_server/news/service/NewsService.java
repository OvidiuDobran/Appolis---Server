package com.ovi.appolis_server.news.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ovi.appolis_server.clerk.data.model.Clerk;
import com.ovi.appolis_server.clerk.repository.ClerkRepository;
import com.ovi.appolis_server.geography.data.model.City;
import com.ovi.appolis_server.geography.repository.CityRepository;
import com.ovi.appolis_server.news.data.model.NewsArticle;
import com.ovi.appolis_server.news.repository.NewsRepository;

@Service
public class NewsService {

	@Autowired
	private NewsRepository newsRepository;

	@Autowired
	private ClerkRepository clerkRepository;

	@Autowired
	private CityRepository cityRepository;

	public NewsArticle createNews(NewsArticle newsArticle) {
		City city = cityRepository.findByNameAndCountry(newsArticle.getCity().getName(),
				newsArticle.getCity().getCountry().getName());
		if (city == null) {
			return null;
		}

		Clerk clerk = clerkRepository.findByEmailAndCity(newsArticle.getAuthor().getEmail(), city);
		if (clerk == null) {
			return null;
		}

		String title = newsArticle.getTitle();
		String description = newsArticle.getContent();

		if (newsArticle.getId() != null) {
			NewsArticle articleFromDb = newsRepository.findById(newsArticle.getId());
			if (articleFromDb != null) {
				newsArticle = articleFromDb;
			}
		}

		newsArticle.setCity(city);
		newsArticle.setTitle(title);
		newsArticle.setAuthor(clerk);
		newsArticle.setCreationDate(new Timestamp(System.currentTimeMillis()));
		newsArticle.setContent(description);

		newsRepository.save(newsArticle);
		return newsArticle;
	}

	public List<NewsArticle> getAllByCity(String cityName, String countryName) {
		City city = cityRepository.findByNameAndCountry(cityName, countryName);
		if (city == null) {
			return new ArrayList<>();
		}
		return newsRepository.findByCity(city);
	}

	public NewsArticle getById(Long id) {
		if (id == null) {
			return null;
		}
		return newsRepository.findById(id);
	}

	public NewsArticle delete(Long id) {
		NewsArticle newsArticle = newsRepository.findById(id);
		if (newsArticle != null) {
			return newsRepository.delete(newsArticle);
		}
		return null;
	}

}
