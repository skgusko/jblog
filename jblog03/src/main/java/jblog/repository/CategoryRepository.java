package jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepository {
	private final SqlSession sqlSession;
	public CategoryRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	public int insertDefaultCategory(String blogId) {
		return sqlSession.insert("category.insertDefaultCategory", blogId);
	}
	
}
