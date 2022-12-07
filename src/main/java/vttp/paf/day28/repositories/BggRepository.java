package vttp.paf.day28.repositories;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import vttp.paf.day28.models.BoardGame;

@Repository
public class BggRepository {

    @Autowired
    private MongoTemplate mongoTemplate;
    /* db.game.aggregate([
    {
        $match: {
            name: 'Twilight Imperium'
        }
    },
    {
        $lookup: {
            from: 'comment',
            foreignField: 'gid',
            localField: 'gid',
            as: 'comments',
        }
    },
    {
        $unwind: "$comments"
    },
    {
        $sort: {"comments.rating": -1}
    },
    {
        $limit: 10
    },
    {
        $group: { _id: "$name", comments: {$push: "$comments"} }
    }
])
*/

    public List<BoardGame> search( String name) {

        List<BoardGame> boardGames = new LinkedList<>();
        //create stages
        MatchOperation matchName = Aggregation.match(
                            Criteria.where("name").is(name)
        );
        
        //lookup
        LookupOperation findComments = Aggregation.lookup("comment", "gid", "gid", "comments");


        //unwind function:
        UnwindOperation unwindByComments= Aggregation.unwind("comments");

        // //project: id, name
        // ProjectionOperation idAndNAmeOnly= Aggregation.project("_id", "name", "comments");

        //sort function
        SortOperation sortByRating = Aggregation.sort(Direction.DESC, "comments.rating");

        //limit function
        LimitOperation limitToTen = Aggregation.limit(10);

        //group operation
        GroupOperation groupByNameAndComments = Aggregation.group("name").push("comments").as("comments");

        //create pipeline
        Aggregation pipeline = Aggregation.newAggregation(matchName, findComments, unwindByComments, sortByRating, limitToTen, groupByNameAndComments);

        //Query the collection
        //use AggregationResults instead of LIst
        AggregationResults<Document> results = mongoTemplate.aggregate(pipeline,"game", Document.class);
        

        for (Document d: results) {
            boardGames.add(BoardGame.create(d));
            
        }

        return boardGames;
    }
    
}
