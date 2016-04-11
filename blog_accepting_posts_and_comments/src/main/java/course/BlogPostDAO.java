package course;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Indexes.ascending;
import static com.mongodb.client.model.Indexes.descending;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import org.bson.Document;

import java.util.List;
import org.bson.conversions.Bson;

public class BlogPostDAO {
    MongoCollection<Document> postsCollection;

    public BlogPostDAO(final MongoDatabase blogDatabase) {
        postsCollection = blogDatabase.getCollection("posts");
        if (postsCollection.count() < 1) {
            Document newDoc = new Document();
            newDoc.append("title", "Martians to use MongoDB")
                    .append("author", "andrew")
                    .append("body","Representatives from the planet Mars announced today that the planet would adopt MongoDB as a planetary standard. Head Martian Flipblip said that MongoDB was the perfect tool to store the diversity of life that exists on Mars.")
                    .append("permalink", "martians_to_use_mongodb")
                    .append("tags", Arrays.asList("martians","seti","nosql","worlddomination"))
                    .append("comments", Arrays.asList(""))
                    .append("date", new Date());
            postsCollection.insertOne(newDoc);
        }
    }

    // Return a single post corresponding to a permalink
    public Document findByPermalink(String permalink) {

        // XXX HW 3.2,  Work Here
        Bson filter = eq("permalink",permalink);
        
        Document post = postsCollection.find(filter).first();



        return post;
    }

    // Return a list of posts in descending order. Limit determines
    // how many posts are returned.
    public List<Document> findByDateDescending(int limit) {

        // XXX HW 3.2,  Work Here
        // Return a list of DBObjects, each one a post from the posts collection
        
        Bson sort = descending("date");
        List<Document> posts = postsCollection.find().sort(sort).limit(limit).into(new ArrayList<Document>());

        return posts;
    }


    public String addPost(String title, String body, List tags, String username) {

        System.out.println("inserting blog entry " + title + " " + body);

        String permalink = title.replaceAll("\\s", "_"); // whitespace becomes _
        permalink = permalink.replaceAll("\\W", ""); // get rid of non alphanumeric
        permalink = permalink.toLowerCase();


        // XXX HW 3.2, Work Here
        // Remember that a valid post has the following keys:
        // author, body, permalink, tags, comments, date
        //
        // A few hints:
        // - Don't forget to create an empty list of comments
        // - for the value of the date key, today's datetime is fine.
        // - tags are already in list form that implements suitable interface.
        // - we created the permalink for you above.

        // Build the post object and insert it
        
        Document newDoc = new Document();
            newDoc.append("title", title)
                    .append("author", username)
                    .append("body",body)
                    .append("permalink", permalink)
                    .append("tags", tags)
                    .append("comments", new ArrayList<Document>())
                    .append("date", new Date());
            postsCollection.insertOne(newDoc);


        return permalink;
    }




    // White space to protect the innocent








    // Append a comment to a blog post
    public void addPostComment(final String name, final String email, final String body,
                               final String permalink) {

        // XXX HW 3.3, Work Here
        // Hints:
        // - email is optional and may come in NULL. Check for that.
        // - best solution uses an update command to the database and a suitable
        //   operator to append the comment on to any existing list of comments
        Document doc = findByPermalink(permalink);
        Document comment = new Document();
        comment.append("author", name)
                .append("body", body);
        if (email != null && email.length() > 0) {
            comment.append("email", email);
        }
        
        List<Document> oldComments = doc.get("comments", ArrayList.class);
        oldComments.add(comment);
        
        postsCollection.updateOne(eq("_id",doc.get("_id")), new Document("$set", new Document("comments", oldComments)));
    }
}
