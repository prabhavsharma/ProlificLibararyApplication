package android.prabhav.com.prolificlibrary.Model;

/**
 * Created by Prabhav on 06-12-2015.
 */

//This is the model layer of the application. It contains all the attributes of the JSON data available on server and
// the basic getters and setters for each attribute. Data fetched and parsed through retrofit is stored in this model.
public class Book {

    private int id;
    private String title;
    private String author;
    private String categories;
    private String publisher;
    private String url;
    private String lastCheckedOutBy;
    private String lastCheckedOut;


    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setLastCheckedOutBy(String lastCheckedOutBy) {
        this.lastCheckedOutBy = lastCheckedOutBy;
    }

    public void setLastCheckedOut(String getLastCheckedOut) {
        this.lastCheckedOut = getLastCheckedOut;
    }


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategories() {
        return categories;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getUrl() {
        return url;
    }

    public String getLastCheckedOutBy() {
        return lastCheckedOutBy;
    }

    public String getLastCheckedOut() {
        return lastCheckedOut;
    }


}