package jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import jblog.vo.CategoryVo;

@Repository
public class CategoryRepository {
	private final SqlSession sqlSession;
	public CategoryRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	public int insertDefaultCategory(String blogId) {
		return sqlSession.insert("category.insertDefaultCategory", blogId);
	}
	public List<CategoryVo> findAllById(String blogId) {
		return sqlSession.selectList("category.findAllById", blogId);
	}
	public Long findLatestCategoryId(String id) {
		return sqlSession.selectOne("category.findLatestCategoryId", id);
	}
	
}
