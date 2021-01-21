package com.ovi.appolis_server.news.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.ovi.appolis_server.geography.data.model.City;
import com.ovi.appolis_server.news.data.model.NewsArticle;

public interface NewsRepository extends Repository<NewsArticle, Long> {

	public NewsArticle save(NewsArticle news);

	public List<NewsArticle> findAll();

	public NewsArticle findById(Long id);

	public List<NewsArticle> findByCity(City city);

	public NewsArticle delete(NewsArticle reward);
}
