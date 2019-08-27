package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vo.NewsVO;

@Repository
public class NewsMyBatisDAO {

	@Autowired // sql세션객체를 autowired해서 받아온거임
	// 그냥 mvc에서는 직접 객체를 생성해야하지만
	// 스프링프레임워크가 servlet-context.xml이 거기에다가 설정해줘서 가능한거임.
	// sql세션객체 생성,관리 모두 스프링의ioc컨테이너가 수행한다.
	SqlSession session = null;

	public List<NewsVO> listAll() {
		List<NewsVO> list = null;
		String statement = "resource.NewsMapper.selectNews";
		list = session.selectList(statement);
		return list;
	}
	
	public List<NewsVO> search(String keyword){
		List<NewsVO> list=null;
		String statement = "resource.NewsMapper.searchNews";
		list = session.selectList(statement, keyword);
        return list;
	}
	
	public boolean insert(NewsVO vo) {
		boolean result=true;
		String statement = "resource.NewsMapper.insertNews";
		if(session.insert(statement, vo) != 1)
			result = false;
		return result;
	}
	
	public NewsVO listOne(int id) {
		System.out.println("DAO에 들어왔는지 확인"+id);
		NewsVO vo = null;
		String statement = "resource.NewsMapper.listOneNews";
		vo = session.selectOne(statement,id);
		return vo;
	}
	
	
	public List<NewsVO> search(String key, String searchType){
		List<NewsVO> li = null;
		Map<String, String> map = new HashMap<String, String>();
		map.put("key", key);
		map.put("searchType", searchType);
		System.out.println("DAO에서 Search map"+map);
		String statement = "resource.NewsMapper.searchNews";
		li = session.selectList(statement, map);
		return li;
	}
	
	
	public boolean update(NewsVO vo) {
		String statement = "resource.NewsMapper.updateNews";
		if (session.update(statement, vo) != 1) return false;
		return true;
	}
	
	public boolean delete(int id) {
		String statement = "resource.NewsMapper.deleteNews";
		if (session.delete(statement, id) != 1) return false;
		return true;
	}
}
