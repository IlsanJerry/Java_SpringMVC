package edu.spring.springnews;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dao.NewsMyBatisDAO;
import vo.NewsVO;

@Controller
public class NewsController {
	@Autowired
	NewsMyBatisDAO dao;

	@RequestMapping(value="/newsmain", method=RequestMethod.GET)
	//NewsVO vo, String action, String newsid, String key, String searchType
	protected ModelAndView select(NewsVO vo, String keyword, String action , 
			String newsid, String read, String key, String searchType ) {
		ModelAndView mav = new ModelAndView();
		System.out.println("read " + read);
		System.out.println("action "+action);
		System.out.println("keyword "+keyword);
		System.out.println("vo "+vo);
		
		
		if(action==null) {
			System.out.println("action != null 걸림");
			List<NewsVO> list = dao.listAll();
			mav.addObject("list", list);
		}
		
		//http://localhost:8000/springnews/newsmain?action=listOne&read=7
		else if(action.equals("listOne")) {
			int newsid1 = Integer.parseInt(read);
			vo=dao.listOne(newsid1);
			if (vo != null) {
				vo.setCnt(vo.getCnt() + 1);
				dao.update(vo);
				System.out.println("update한 vo값"+vo);
				mav.addObject("readVO", vo);
			}
			mav.addObject("list", dao.listAll());
		}
		
		//http://localhost:8000/springnews/newsmain?action=delete&newsid=7
		else if(action.equals("delete")) {
			int newsid2 = Integer.parseInt(newsid);
			
			System.out.println("delete 들어왔고 newsid값 +"+newsid2);
			if (dao.delete(newsid2)) mav.addObject("msg", "삭제 완료");
			else mav.addObject("msg", "삭제 실패");
			mav.addObject("list", dao.listAll());
		}
		else if (action.equals("search")) {
			System.out.println("search걸림");
			mav.addObject("list", dao.search(key, searchType));
		}
		/*
		 * else if (action.equals("writer")) mav.addObject("list",
		 * dao.listWriter(vo.getWriter()));
		 */
		
		mav.setViewName("news");
		return mav;
	}
	
	
	//http://70.12.113.165:8000/springnews/newsmain?action=search_writer&writer=TeeMo
	@RequestMapping(value="/newsmain", method=RequestMethod.POST)
	//NewsVO vo, String action, String newsid, String key, String searchType
	protected ModelAndView select(NewsVO vo, String action, String read, String newsidd ) {
		ModelAndView mav = new ModelAndView();
		System.out.println("POST의 read " + read);
		System.out.println("POST의 newsidd " + newsidd);
		System.out.println("POST의 action "+action);
		System.out.println("POST 받아올떄 vo "+vo);
		
		String writer = vo.getWriter();
		String title = vo.getTitle();
		String content = vo.getContent();
		String writedate = vo.getWritedate();
		vo.setWriter(writer);
		vo.setTitle(title);
		vo.setContent(content);
		vo.setWritedate(writedate);
		

		if(action.equals("update")) {
			System.out.println("여긴? 걸리냐");
			int newsId = Integer.parseInt(newsidd);
			System.out.println("여긴?");
			vo = dao.listOne(newsId);
			vo.setWriter(writer);
			vo.setTitle(title);
			vo.setContent(content);
			vo.setWritedate(writedate);
			vo.setCnt(vo.getCnt() + 1);
			
			System.out.println(vo);
			if (dao.update(vo)) mav.addObject("msg", "수정 완료");
			else mav.addObject("msg", "수정 실패");
			
			mav.addObject("list", dao.listAll());
		}
		
		else if(action.equals("insert")) {
			System.out.println("insert들어옴");
			if (dao.insert(vo)) mav.addObject("msg", "작성 완료");
			else mav.addObject("msg", "작성 실패");
			
			mav.addObject("list", dao.listAll());
		}
		
		mav.setViewName("news");
		return mav;
	}
}
