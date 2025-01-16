package jblog.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import jblog.repository.PostRepository;
import jblog.vo.PostVo;

@Service
public class PostService {
	private final PostRepository postRepository;
	private final BlogService blogService;
	private final CategoryService categoryService;
	
	
	public PostService(PostRepository postRepository, BlogService blogService, CategoryService categoryService) {
		this.postRepository = postRepository;
		this.blogService = blogService;
		this.categoryService = categoryService;
	}

	public Long findLatestPostId(Long categoryId) {
		return postRepository.findLatestPostId(categoryId);
	}
	
	public List<PostVo> getContents(Long categoryId) {
		return postRepository.findTitlesByCategoryId(categoryId);
	}
	
	public PostVo getMainPost(Long postId) {
		return postRepository.findByPostId(postId);
	}
	
	public Map<String, Object> getBlogData(String blogId, Long categoryId, Long postId) {
		Map<String, Object> result = new HashMap<>();
		
//		result.put("blogVo", blogService.getContents(blogId)); // title, profile
		result.put("categoryVo", categoryService.getContents(blogId)); // category list
		result.put("postVo", getContents(categoryId)); //카테고리의 게시글들
		result.put("mainPostVo", getMainPost(postId)); //메인 게시글
		
		return result;
	}

	public int write(PostVo postVo) {
		return postRepository.insert(postVo);
	}

	public int findByPostIdAndCategoryId(Long postId, Long categoryId) {
		return postRepository.findByPostIdAndCategoryId(postId, categoryId);
	}

}
