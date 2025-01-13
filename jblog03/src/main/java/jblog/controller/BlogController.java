package jblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jblog.service.BlogService;
import jblog.service.CategoryService;

@Controller
@RequestMapping("/blog")
public class BlogController {
	private final BlogService blogService;
	private final CategoryService categoryService;
	public BlogController(BlogService blogService, CategoryService categoryService) {
		this.blogService = blogService;
		this.categoryService = categoryService;
	}
	
	@GetMapping("/{id}")
	public String index(@PathVariable("id") String id,
						Model model) {
		model.addAttribute("blogVo", blogService.getContents(id)); // title, profile
		
		
		return "blog/main";
	}
}
