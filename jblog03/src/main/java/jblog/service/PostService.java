package jblog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jblog.repository.CategoryRepository;
import jblog.repository.PostRepository;
import jblog.vo.PostVo;

@Service
public class PostService {
	private final CategoryRepository categoryRepository;
	private final PostRepository postRepository;
	
	public PostService(CategoryRepository categoryRepository, PostRepository postRepository) {
		this.categoryRepository = categoryRepository;
		this.postRepository = postRepository;
	}
	
	public List<PostVo> getContents(String id) {
		Long latestCategoryId = categoryRepository.findLatestCategoryId(id);
		
		return postRepository.findTitlesByIdAndCategoryId(id, latestCategoryId);
	}

}
