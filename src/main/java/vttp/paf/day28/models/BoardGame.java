package vttp.paf.day28.models;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;

public class BoardGame {

    private String name;
    private String id;
    private List<Comment> gameComments = new LinkedList<>();


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public List<Comment> getGameComments() {
        return gameComments;
    }
    public void setGameComments(List<Comment> comments) {
        this.gameComments = comments;
    }

    public static BoardGame create(Document doc) {
        final BoardGame boardGame = new BoardGame();
        // boardGame.setId(doc.getString("id"));
        boardGame.setName(doc.getString("_id"));
        List<Document> commentDoc = (List<Document>) doc.get("comments");
        for (Document d: commentDoc) {
            Comment c = Comment.create(d);
            boardGame.gameComments.add(c);
        }

        return boardGame;
        

        
    }


}
