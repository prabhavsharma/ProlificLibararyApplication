package android.prabhav.com.prolificlibrary.Interfaces;

/**
 * Created by Prabhav on 06-12-2015.
 */

import android.prabhav.com.prolificlibrary.Model.Book;

        import java.util.List;

        import retrofit.Callback;
        import retrofit.http.Body;
        import retrofit.http.DELETE;
        import retrofit.http.GET;
        import retrofit.http.Multipart;
        import retrofit.http.POST;
        import retrofit.http.PUT;
        import retrofit.http.Part;
        import retrofit.http.Path;


public interface retrofitInterface {

    //retrofit method for GET request.
    @GET("/books/")
    void getData(Callback<List<Book>> response);

    //method for POST request.
    @POST("/books/")
    void addBook(@Body Book book, Callback<Book> upload);

    //method to DELETE a Book.
    @DELETE("/books/{id}/")
    void deleteBook(@Path("id") int id, Callback<Book> callback);

    //method for Deleting all book entries
    @DELETE("/clean/")
    void deleteAllBooks(Callback<Book> deleteCallback);

    //method for updating param "lastCheckedOutBy"
    @Multipart
    @PUT("/books/{id}/")
    void updateCheckout(@Path("id") int id, @Part("lastCheckedOutBy") String checkedOutBy, Callback<Book> update);

    //method for updating a book
    @PUT("/books/{id}/")
    void updateBook(@Path("id") int id, @Body Book book, Callback<Book> update);

}