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

	public void deleteContents(String authUserId, Long categoryId) {
		CategoryVo vo = categoryRepository.findById(categoryId); //카테고리의 id로 찾은 row의 blogId가 authUserId와 동일하면
		if (authUserId.equals(vo.getBlogId())) {
			categoryRepository.deleteById(categoryId);
		}
	}
}
