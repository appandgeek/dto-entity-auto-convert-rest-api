package com.appNgeek.dto_entity_auto_rest_api.repo;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.appNgeek.dto_entity_auto_rest_api.domain.Article;
import com.appNgeek.dto_entity_auto_rest_api.domain.User;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

	Article findByTitle(String title);
	
	Collection<Article> findByUser(User user);

}