package jblog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jblog.repository.CategoryRepository;
import jblog.vo.CategoryVo;

@Service
public class CategoryService {
	private final CategoryRepository categoryRepository;
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public List<CategoryVo> getContents(String id) {
		return categoryRepository.findAllById(id);
	}

	public Long findLatestCategoryId(String id) {
		return categoryRepository.findLatestCategoryId(id);
	}

	public int add(CategoryVo vo) {
		return categoryRepository.insert(vo);
	}

	public List<CategoryVo> getTotal(String id) {
		return categoryRepository.getTotal(id);
	}
}
