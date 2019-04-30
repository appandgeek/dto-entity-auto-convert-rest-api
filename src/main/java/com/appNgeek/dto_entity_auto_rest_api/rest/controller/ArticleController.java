package com.appNgeek.dto_entity_auto_rest_api.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appNgeek.dto_entity_auto_rest_api.convertor.annotation.RequestBodyDTO;
import com.appNgeek.dto_entity_auto_rest_api.convertor.annotation.ResponseBodyDTO;
import com.appNgeek.dto_entity_auto_rest_api.domain.Article;
import com.appNgeek.dto_entity_auto_rest_api.dto.ArticleDTO;
import com.appNgeek.dto_entity_auto_rest_api.exception.BlogAppException;
import com.appNgeek.dto_entity_auto_rest_api.repo.ArticleRepository;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {

	@Autowired
	private ArticleRepository articleRepository;

	@GetMapping
	@ResponseBodyDTO(ArticleDTO.class)
	public Page<Article> findAll(Pageable pageable) {
		return articleRepository.findAll(pageable);
	}
	
	@GetMapping("/list")
	@ResponseBodyDTO(ArticleDTO.class)
	public List<Article> findAllAsList(Pageable pageable) {
		return articleRepository.findAll(pageable).getContent();
	}

	@GetMapping("/find")
	@ResponseBodyDTO(ArticleDTO.class)
	public Article findByTitle(@RequestParam String title) {
		return articleRepository.findByTitle(title);
	}

	@GetMapping("/{id}")
	@ResponseBodyDTO(ArticleDTO.class)
	public Article findOne(@PathVariable Long id) throws BlogAppException {
		Optional<Article> result = articleRepository.findById(id);
		if (result.isPresent())
			return result.get();
		else
			throw new BlogAppException("Article with given id not found");
	}

	@PostMapping
	@ApiOperation(value = "Create Article", response = ArticleDTO.class)
	@ResponseBodyDTO(ArticleDTO.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Article DTO", value = "article", required = true, dataType = "ArticleDTO", paramType = "body")})
	public Article create(@RequestBodyDTO(ArticleDTO.class) @ApiIgnore Article article) {
		return articleRepository.save(article);
	}

	@DeleteMapping("/{id}")
	@ResponseBodyDTO(ArticleDTO.class)	
	public void delete(@PathVariable Long id) {
		articleRepository.deleteById(id);
	}

}
