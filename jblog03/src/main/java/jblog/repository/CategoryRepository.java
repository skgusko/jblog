package jblog.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import jblog.vo.CategoryVo;

@Repository
public class CategoryRepository {
	private final SqlSession sqlSession;
	public CategoryRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	public int insert(CategoryVo categoryVo) {
		return sqlSession.insert("category.insert", categoryVo);
	}
	public List<CategoryVo> findAllById(String blogId) {
		return sqlSession.selectList("category.findAllById", blogId);
	}
	public Long findLatestCategoryId(String id) {
		return sqlSession.selectOne("category.findLatestCategoryId", id);
	}
	public List<CategoryVo> getTotal(String id) {
		return sqlSession.selectList("category.getTotal", id);
	}
	public CategoryVo findById(Long categoryId) {
		return sqlSession.selectOne("category.findById", categoryId);
	}
	public int deleteById(Long categoryId) {
		return sqlSession.delete("category.deleteById", categoryId);
	}
	public int findByCategoryIdAndBlogId(Long categoryId, String blogId) {
		return sqlSession.selectOne("category.findByCategoryIdAndBlogId",
									Map.of("categoryId", categoryId, "blogId", blogId));
	}
}
