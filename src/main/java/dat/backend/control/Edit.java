package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.Item;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.ItemFacade;
import dat.backend.model.services.Authentication;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Edit", value = "/edit")
public class Edit extends HttpServlet
{
    ConnectionPool connectionPool = ApplicationStart.getConnectionPool();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setCharacterEncoding("UTF-8");

            int item_id = Integer.parseInt(request.getParameter("item_id"));
            Item item = ItemFacade.getItemById(item_id, connectionPool);
            request.setAttribute("item", item);
            request.getRequestDispatcher("WEB-INF/edititemform.jsp").forward(request, response);


    }
}
