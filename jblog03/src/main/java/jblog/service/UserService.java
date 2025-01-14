package jblog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jblog.repository.BlogRepository;
import jblog.repository.CategoryRepository;
import jblog.repository.UserRepository;
import jblog.vo.CategoryVo;
import jblog.vo.UserVo;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final BlogRepository blogRepository;
	private final CategoryRepository categoryRepository;
	
	public UserService(UserRepository userRepository, BlogRepository blogRepository, CategoryRepository categoryRepository) {
		this.userRepository = userRepository;
		this.blogRepository = blogRepository;
		this.categoryRepository = categoryRepository;
	}
	
	@Transactional
	public void join(UserVo userVo) {
		// 1. 회원 등록 (User)
		System.out.println(userVo);
		int count = userRepository.insert(userVo);
		if (count == 0) {
			return;
		}
		
		// 2. Default Title, Profile 등록 (Blog)
		count = blogRepository.insertDefaultTitleAndProfile(userVo.getName(), userVo.getId());
		if (count == 0) {
			return;
		}
		
		// 3. Default Category 등록 (Category)
		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setName("미분류");
		categoryVo.setBlogId(userVo.getId());
		count = categoryRepository.insert(categoryVo);
	}

	public UserVo getUser(String id, String password) {
		return userRepository.findByIdAndPassword(id, password);
	}
}
