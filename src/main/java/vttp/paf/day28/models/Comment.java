package vttp.paf.day28.models;

import org.bson.Document;

public class Comment {

    private String user; 
    private int rating; 
    private String text;


    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    } 
    
    public static Comment create(Document doc) {
        final Comment comment = new Comment();
        comment.setText(doc.getString("c_text"));
        comment.setUser(doc.getString("user"));
        comment.setRating(doc.getInteger("rating"));
        return comment;
        
    }
}
