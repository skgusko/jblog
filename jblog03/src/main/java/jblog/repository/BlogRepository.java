package jblog.repository;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import jblog.vo.BlogVo;

@Repository
public class BlogRepository {
	private final SqlSession sqlSession;
	public BlogRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	public int insertDefaultTitleAndProfile(String userName, String blogId) {
		String title = userName + "의 blog";
		String profile = "/assets/images/profile.png";
		
		return sqlSession.insert(
				"blog.insertDefaultTitleAndProfile", 
				Map.of("blogId", blogId, "title", title, "profile", profile)
		);
	}
	public BlogVo findById(String id) {
		return sqlSession.selectOne("blog.findById", id);
	}
	public int update(BlogVo blogVo) {
		return sqlSession.update("blog.update", blogVo);
	}
	
	
}
