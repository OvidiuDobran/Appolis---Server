package com.ovi.appolis_server.news.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ovi.appolis_server.news.data.model.NewsArticle;
import com.ovi.appolis_server.news.service.NewsService;

@RestController
@RequestMapping("/news")
@CrossOrigin(origins = "http://localhost:4200")
public class NewsController {

	@Autowired
	private NewsService newsService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public NewsArticle createNews(@RequestBody NewsArticle news) {
		return newsService.createNews(news);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public NewsArticle updateNews(@RequestBody NewsArticle news) {
		return newsService.createNews(news);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public NewsArticle deleteNewsArticle(@PathVariable Long id) {
		return newsService.delete(id);
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<NewsArticle> getAllByCity(@RequestParam(required = true) String cityName,
			@RequestParam(required = true) String countryName) {
		return newsService.getAllByCity(cityName, countryName);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public NewsArticle getById(@PathVariable Long id) {
		return newsService.getById(id);
	}
}
