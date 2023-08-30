package ds.project1task2;
/**
 * Author: Kathy Chiang
 * Last Modified: Feb 10, 2023
 *
 * This program serves as a servlet to handle HTTP requests sent by clients and generate an HTTP response for those requests.
 * It determines which view the request transfers control to by judging whether the country variable is null or not
 * The program takes the chosen country as the input to search for its nickname, capital city, top scorer in 2019 and also its flags.
 */

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

/*
 * The following WebServlet annotation gives instructions to the web container.
 * It states that when the user browses to the URL path /worldCup
 * then the servlet with the name WorldCupServlet should be used.
 */

@WebServlet(name = "WorldCupServlet",
        urlPatterns = {"/worldCup"})
public class WorldCupServlet extends HttpServlet {

    // The "business model" for this app
    WorldCupModel wm;

    // Initialize this servlet by instantiating the model that it will use
    @Override
    public void init() {wm = new WorldCupModel();}

    // This servlet will reply to HTTP GET requests via this doGet method
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // get the search parameter if it exists
        String country = request.getParameter("country-name");

        String nextView;
        /*
         * Check if the search parameter is present.
         * If not, then give the user instructions and prompt for a search string.
         * If there is a search parameter, then do the search and return the result.
         */
        if (country != null) {

            // use model to do the search and choose the result view
            String nickName = wm.searchNickName(country);
            String capital = wm.searchCapital(country);
            String[] topScore = wm.searchScorer(country);
            String pictureURL = wm.searchFlag(country);
            String emojiPath = wm.searchEmoji(country);

            // set the attributes so that it can be called and shown on the web page
            request.setAttribute("nickName",nickName);
            request.setAttribute("capital", capital);
            request.setAttribute("scorer", topScore[0]);
            request.setAttribute("goal", topScore[1]);
            request.setAttribute("pictureURL", pictureURL);
            request.setAttribute("emojiPath", emojiPath);

            // Pass the user search string (pictureTag) also to the view.
            nextView = "result.jsp";
        } else {
            // no search parameter so choose the prompt view
            nextView = "index.jsp";
        }
        // Transfer control over the correct "view"
        RequestDispatcher view = request.getRequestDispatcher(nextView);
        view.forward(request, response);
    }
}