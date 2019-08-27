/*package edu.spring.springnews;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.NewsDAO;
import model.vo.NewsVO;

@WebServlet("/asdf")
public class NewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String stid = request.getParameter("id");
		String action = request.getParameter("action");

		NewsDAO dao = new NewsDAO();
		if (action != null) {
			if (action.equals("read")) {
				// ==을 하면 안되는이유 : action에는
				NewsVO vo = dao.listOne(Integer.parseInt(stid));
				request.setAttribute("vo", vo);
				request.setAttribute("list", dao.listAll());
			} else if (action.equals("delete")) {
				if (dao.delete(Integer.parseInt(stid)))
					request.setAttribute("msg", "뉴스가 성공적으로 삭제되었습니다.");
				else
					request.setAttribute("msg", "뉴스를 삭제하는데  실패했습니다.");
				request.setAttribute("list", dao.listAll());
			} else if (action.equals("find")) {
				String option = request.getParameter("option");
				String search = request.getParameter("search");
				if (option != null && search != null) {
					request.setAttribute("r_title", dao.search(search, option));

				}
			} else if (action.equals("s_writer")) {
				String wrt = request.getParameter("writer");
				request.setAttribute("r_writer", dao.listWriter(wrt));
			}

		} else {
			request.setAttribute("list", dao.listAll());
		}

		request.getRequestDispatcher("jspexam/news.jsp").forward(request, response);
	}

}
*/