package vttp.paf.day28.controllers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp.paf.day28.models.BoardGame;
import vttp.paf.day28.models.Comment;
import vttp.paf.day28.repositories.BggRepository;

@Controller
@RequestMapping(path="/")
public class BoardGameController {
    
    @Autowired
    private BggRepository bggRepo;

    @GetMapping(path="/reviews")
    public String getReviewsByName(@RequestParam String name, Model model) {
        //Make the query
        List<BoardGame> boardGames = bggRepo.search(name);
        System.out.println(boardGames.get(0).getGameComments().get(0).getText());
        model.addAttribute("boardGames", boardGames);
        return "reviews";
    }


}
