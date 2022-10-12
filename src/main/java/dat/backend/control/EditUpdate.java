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
import java.util.Locale;

@WebServlet(name = "EditUpdate", value = "/editupdate")
public class EditUpdate extends HttpServlet
{
    private final ConnectionPool connectionPool = ApplicationStart.getConnectionPool();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setCharacterEncoding("UTF-8");


            int item_id = Integer.parseInt(request.getParameter("item_id"));
            String name = request.getParameter("name");

            ItemFacade.updateItemName(item_id, name, connectionPool);
            List<Item> itemList = ItemFacade.getItems(connectionPool);
            request.setAttribute("itemList", itemList);
            request.getRequestDispatcher("WEB-INF/welcome.jsp").forward(request, response);


    }
}
