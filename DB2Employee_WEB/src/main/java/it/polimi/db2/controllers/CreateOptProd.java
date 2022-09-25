package it.polimi.db2.controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.persistence.PersistenceException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import it.polimi.db2.entities.OptionalProduct;
import it.polimi.db2.services.OptionalProductService;

/**
 * Servlet implementation class CreateOptProd
 */
@WebServlet("/CreateOptProd")
public class CreateOptProd extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB(name = "it.polimi.db2.services/OptionalProductService")
    private OptionalProductService ops;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateOptProd() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name, path;
		int fee;
		
		name = StringEscapeUtils.escapeJava(request.getParameter("name"));
		try {
			fee = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("fee")));
		} catch (NumberFormatException e) {
			String message= "Fee must be an integer!";
			path = getServletContext().getContextPath() + "/Home";
	        request.getSession().setAttribute("errorMsg", message);
			response.sendRedirect(path);
			return;
		}
		
		if (name==null || name.isEmpty()) {
			String message= "Missing name for optional product!";
			path = getServletContext().getContextPath() + "/Home";
	        request.getSession().setAttribute("errorMsg", message);
			response.sendRedirect(path);
			return;
		}
		
		OptionalProduct op= new OptionalProduct();
		op.setName(name);
		op.setFee(fee);
		
		try {
			ops.insertOptionalProduct(op);
		} catch(PersistenceException e) {
			path = getServletContext().getContextPath() + "/Home";
	        request.getSession().setAttribute("errorMsg", "Something went wrong while inserting the new product in the db");
			response.sendRedirect(path);
			return;
		}
		
        path = getServletContext().getContextPath() + "/Home";
        request.getSession().setAttribute("errorMsg", "Product succesfully created");
		response.sendRedirect(path);
		return;
	}

}
