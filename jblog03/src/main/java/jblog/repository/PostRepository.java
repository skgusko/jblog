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
	
	public List<PostVo> findTitlesByCategoryId(Long categoryId) {
		return sqlSession.selectList("post.findTitlesByCategoryId", categoryId); 
	}

	public Long findLatestPostId(Long categoryId) {
		return sqlSession.selectOne("post.findLatestPostId", categoryId);
	}

	public PostVo findByPostId(Long postId) {
		return sqlSession.selectOne("post.findByPostId", postId);
	}

	public int insert(PostVo postVo) {
		return sqlSession.insert("post.insert", postVo);
	}

	public int findByPostIdAndCategoryId(Long postId, Long categoryId) {
		return sqlSession.selectOne("post.findByPostIdAndCategoryId",
									Map.of("postId", postId, "categoryId", categoryId));
	}


}
