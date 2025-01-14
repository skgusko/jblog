package jblog.service;

import org.springframework.stereotype.Service;

import jblog.repository.BlogRepository;
import jblog.vo.BlogVo;

@Service
public class BlogService {
	private final BlogRepository blogRepository;
	public BlogService(BlogRepository blogRepository) {
		this.blogRepository = blogRepository;
	}
	
	public BlogVo getContents(String id) {
		return blogRepository.findById(id);
	}

	public int update(BlogVo blogVo) {
		return blogRepository.update(blogVo);
		
	}

}
