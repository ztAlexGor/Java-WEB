package controllers;

import command.Command;
import command.factory.CommandFactory;
import command.factory.CommandFactoryImpl;
import command.CommandResult;
import dao.DaoHelper;
import exception.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

import connection.DBUtil;
@WebServlet("/controller")
public class Controller extends HttpServlet {

    public static final String COMMAND_PARAMETER = "command";
    private static final Logger LOGGER = LogManager.getLogger(Controller.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException {

    	Connection connection = DBUtil.getConnection();
        DaoHelper daoHelper = new DaoHelper(connection);
        CommandFactory commandFactory = new CommandFactoryImpl(daoHelper);
        String commandName = request.getParameter(COMMAND_PARAMETER);
        Command command = commandFactory.createCommand(commandName);
        try {
            CommandResult commandResult = command.execute(request, response);
            dispatch(request, response, commandResult);
        } catch (ServletException | IOException | ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }
    }

    private void dispatch(HttpServletRequest request, HttpServletResponse response, CommandResult commandResult)
            throws ServletException, IOException {
        String page = commandResult.getPage();
        if (commandResult.isRedirect()) {
            String contextPath = getServletContext().getContextPath();
            response.sendRedirect(contextPath + page);
        } else {
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(page);
            try {
            	requestDispatcher.forward(request, response);
            }
            catch(Exception e) {
            	System.out.println(requestDispatcher);
            }
          
            
        }
    }

}
