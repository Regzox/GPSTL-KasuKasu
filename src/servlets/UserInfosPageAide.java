package servlets;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.items.ItemsDB;
import dao.users.UserDao;
import services.ExchangePoints;
import services.Items;
import servlets.tools.templates.online.OnlineGetServlet;

public class UserInfosPageAide extends OnlineGetServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doBusiness(HttpServletRequest request, HttpServletResponse response, Map<String, String> params)
			throws Exception {
		response.getWriter().print(Items.userInfos(
				(String) request.getSession().getAttribute("userId")));	
	}
}
