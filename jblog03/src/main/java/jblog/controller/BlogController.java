package jblog.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import jblog.security.Auth;
import jblog.service.BlogService;
import jblog.service.CategoryService;
import jblog.service.FileUploadService;
import jblog.service.PostService;
import jblog.vo.BlogVo;
import jblog.vo.CategoryVo;
import jblog.vo.PostVo;
import jblog.vo.UserVo;

@Controller
@RequestMapping("/{id:(?!assets).*}") //정규표현식 - assets가 아닌 애가 있거나 없거나
public class BlogController {
	private final BlogService blogService;
	private final CategoryService categoryService;
	private final PostService postService;
	private final FileUploadService fileUploadService;
	public BlogController(BlogService blogService, CategoryService categoryService, PostService postService, FileUploadService fileUploadService) {
		this.blogService = blogService;
		this.categoryService = categoryService;
		this.postService = postService;
		this.fileUploadService = fileUploadService;
	}
	
	@GetMapping({"", "/", "/{path1}", "/{path1}/{path2}"})
	public String main(
			@PathVariable("id") String id,
			@PathVariable("path1") Optional<Long> path1, //null
			@PathVariable("path2") Optional<Long> path2,
						Model model) {
		
		Long categoryId = 0L;
		Long postId = 0L;
		
		if(path2.isPresent()) {
			categoryId = path1.get();
			postId = path2.get();
		} else if(path1.isPresent()) {
			categoryId = path1.get();
		}
		
		// categoryId == 0L -> default categoryId 결정해서 서비스로 넘겨  
		// postId == 0L -> default postId 결정해서 서비스로 넘겨 
		if (categoryId == 0L) { //default categoryId
			categoryId = categoryService.findLatestCategoryId(id);
		}
		if (postId == 0L) { //default postId
			postId = postService.findLatestPostId(categoryId);
		}
		
		Map<String, Object> blogData = postService.getBlogData(id, categoryId, postId);
		model.addAllAttributes(blogData);
		
		return "blog/main";
	}
	
	@Auth
	@GetMapping("/admin")
	public String adminDefault(@PathVariable("id") String id) {
		
		return "blog/admin-default";
	}
	
	@Auth
	@PostMapping("/admin")
	public String adminDefault(@PathVariable("id") String id,
							   @RequestParam("logo-file") MultipartFile file,
							   @RequestParam("title") String title,
							   @RequestParam("profile") String profile) {
		BlogVo blogVo = new BlogVo();
		blogVo.setBlogId(id);
		blogVo.setTitle(title);
		blogVo.setProfile(profile);
		
		String newProfile = fileUploadService.restore(file);
		if (newProfile != null) {
			blogVo.setProfile(newProfile);
		}
		blogService.update(blogVo);
		
		return "redirect:/" + id;
	}
	
	@Auth
	@GetMapping("/admin/write")
	public String adminWrite(@PathVariable("id") String id, Model model) {
		model.addAttribute("categoryVo", categoryService.getContents(id));
		
		return "blog/admin-write";
	}
	
	@Auth
	@PostMapping("/admin/write")
	public String adminWrite(@PathVariable("id") String id,
							 @RequestParam("title") String title,
							 @RequestParam("category") Long categoryId,
							 @RequestParam("content") String contents) {
		PostVo postVo = new PostVo();
		postVo.setTitle(title);
		postVo.setCategoryId(categoryId);
		postVo.setContents(contents);
		
		postService.write(postVo);
		
		return "redirect:/" + id + "/" + postVo.getCategoryId();
	}
	
	@Auth
	@GetMapping("/admin/category")
	public String adminCategory(@PathVariable("id") String id, Model model) {
		model.addAttribute("categoryVo", categoryService.getTotal(id));
		
		return "blog/admin-category";
	}
	
	@Auth
	@PostMapping("/admin/category/add")
	public String adminCategory(@PathVariable("id") String id,
								@RequestParam("name") String name,
								@RequestParam("desc") String description) {
		CategoryVo vo = new CategoryVo();
		vo.setBlogId(id);
		vo.setName(name);
		vo.setDescription(description);
		
		categoryService.add(vo);
		
		return "redirect:/" + id + "/admin/category";
	}
	
	@Auth
	@GetMapping("/admin/category/delete/{categoryId}")
	public String adminCategory(HttpSession session,
								@PathVariable("id") String id, 
								@PathVariable("categoryId") Long categoryId) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if (session == null || authUser == null ) {
			return "user/login";
		}
		
		categoryService.deleteContents(authUser.getId(), categoryId);
		
		return "redirect:/" + id + "/admin/category";
	}
}
