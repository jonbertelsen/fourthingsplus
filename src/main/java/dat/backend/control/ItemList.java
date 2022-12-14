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
import java.util.List;

@WebServlet(name = "ItemList", value = "/itemlist")
public class ItemList extends HttpServlet
{
    private static ConnectionPool connectionPool = ApplicationStart.getConnectionPool();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if (Authentication.isUserLoggedIn(request))
        {
            List<Item> itemList = ItemFacade.getItems(connectionPool);
            request.setAttribute("itemList", itemList);
            request.getRequestDispatcher("WEB-INF/welcome.jsp").forward(request, response);
        }
        else
        {
            response.sendRedirect("index.jsp");
        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }
}
