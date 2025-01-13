package jblog.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import jblog.vo.PostVo;

@Repository
public class PostRepository {
	private final SqlSession sqlSession;
	public PostRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}	

	public Long findLatesCategoryId(String id) {
		return sqlSession.selectOne("post.findLatesCategoryId", id);
	}
	
	public List<PostVo> findTitlesByIdAndCategoryId(String id, Long categoryId) {
		return sqlSession.selectList(
					"post.findTitlesByIdAndCategoryId", 
					Map.of("id", id, "categoryId", categoryId));
	}


}
