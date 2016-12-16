package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import json.Error;
import services.Evaluation.Mode;

/**
 * Servlet de la ressource Evaluation
 * 
 * @author Daniel RADEAU
 *
 */

public class Evaluation extends HttpServlet {

	private static final long serialVersionUID = 5918145233011311785L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String resource = request.getParameter("resource");
		String action = request.getParameter("action");
		String userId = (String) request.getSession().getAttribute("userId");
		String evaluationId = request.getParameter("evaluation-id");
		String evaluationRequestId = request.getParameter("evaluation-request-id");
		String evaluationResponseId = request.getParameter("evaluation-response-id");
		Mode mode = Mode.RECEIVED;
		if (request.getParameter("mode") != null)
			mode = (request.getParameter("mode").equals("given")) ? Mode.GIVEN : Mode.RECEIVED;

		switch (resource) {
		case "request" :
			switch (action) {
			case "list":
				response.getWriter().println(services.Evaluation.listRequestNotifications(userId));
				break;
			case "find" :
				response.getWriter().println(services.Evaluation.findRequest(evaluationRequestId));
				break;
			default:
				response.getWriter().print(new Error("Le paramètre action n'est pas reconnu"));
			}
			break;
		case "evaluation" :
			switch (action) {
			case "list":
				response.getWriter().println(services.Evaluation.listEvaluations(mode, userId));
				break;
			case "find" :
				response.getWriter().println(services.Evaluation.findEvaluation(evaluationId));
				break;
			default:
				response.getWriter().print(new Error("Le paramètre action n'est pas reconnu"));
			}
			break;
		case "response" :
			response.getWriter().println(services.Evaluation.listReplies(userId));
			switch (action) {
			case "list":
				response.getWriter().println(services.Evaluation.listReplies(userId));
				break;
			case "find" :
				response.getWriter().println(services.Evaluation.findReply(evaluationResponseId));
				break;
			default:
				response.getWriter().print(new Error("Le paramètre action n'est pas reconnu"));
			}
			break;
		default :
			response.getWriter().print(new json.Error("Le paramètre de ressource n'est pas reconnu"));
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String resource = request.getParameter("resource");
		String action = request.getParameter("action");
		String evaluationId = request.getParameter("evaluation-id");
		String evaluationRequestId = request.getParameter("evaluation-request-id");
		String evaluationResponseId = request.getParameter("evaluation-response-id");
		String senderId = request.getParameter("sender-id");
		String receiverId = request.getParameter("receiver-id");
		String loanId = request.getParameter("loan-id");
		String comment = request.getParameter("comment");
		String mark = request.getParameter("mark");

		switch (resource) {
		case "request" :
			switch (action) {
			case "insert" :
				services.Evaluation.insertRequest(senderId, receiverId, loanId);
				break;
			case "remove" :
				services.Evaluation.removeRequest(evaluationRequestId);
				break;
			default:
				response.getWriter().print(new Error("Le paramètre action n'est pas reconnu"));
			}
			break;
		case "evaluation" :
			switch (action) {
			case "insert" :
				services.Evaluation.insertEvaluation(senderId, receiverId, loanId, comment, mark);
				break;
			case "remove" :
				services.Evaluation.removeEvaluation(evaluationId);
				break;
			default:
				response.getWriter().print(new Error("Le paramètre action n'est pas reconnu"));
			}
			break;
		case "response" :
			switch (action) {
			case "insert" :
				services.Evaluation.insertReply(senderId, receiverId, loanId);
				break;
			case "remove" :
				services.Evaluation.removeReply(evaluationResponseId);
				break;
			default:
				response.getWriter().print(new Error("Le paramètre action n'est pas reconnu"));
			}
			break;
		default:
			response.getWriter().print(new json.Error("Le paramètre de ressource n'est pas reconnu"));
		}
	}
}
