package tests;

import io.restassured.response.Response;
import models.Post;
import models.User;
import utils.ApiUtils;
import utils.ConfigReader;
import utils.DataUtils;

import org.testng.annotations.Test;
import java.util.Arrays;
import java.util.List;
import static org.testng.Assert.*;
import io.restassured.path.json.JsonPath;

public class ApiTest {
     
    @Test
    public void testGetAllPosts() {
        String baseUrl = ConfigReader.getBaseUrl();
        Response response = ApiUtils.sendGetRequest(baseUrl, "/posts");
        assertEquals(200, response.getStatusCode());

        List<Post> posts = Arrays.asList(response.as(Post[].class));
        assertTrue(posts.size() > 0);

        // to check if id is in the ascending order or not
        int previousId = 0;
        for (Post post : posts) {
            assertTrue(post.id > previousId);
            previousId = post.id;
        }
    }

    
    @Test
    public void testGetPostById() {
        String baseUrl = ConfigReader.getBaseUrl();
        int postId = 99;
        Response response = ApiUtils.sendGetRequest(baseUrl, "/posts/" + postId);
        assertEquals(200, response.getStatusCode());

        Post post = response.as(Post.class);
        assertEquals(10, post.userId);
        assertEquals(99, post.id);
        assertNotNull(post.title);
        assertFalse(post.title.isEmpty());
        assertNotNull(post.body);
        assertFalse(post.body.isEmpty());
    }

    @Test
    public void testIsResponseBodyEmpty(){
        String baseUrl = ConfigReader.getBaseUrl();
        int postId = 150;
        Response response = ApiUtils.sendGetRequest(baseUrl, "/posts/" + postId);
        assertEquals(404, response.getStatusCode());

        String responseBody = response.getBody().asString();
        assertEquals("{}",responseBody);
    } 

    @Test
    public void testCreatePost() {

        String baseUrl = ConfigReader.getBaseUrl();
        
        Post post = new Post();
        post.setUserId(1);
        post.setTitle(DataUtils.generateRandomString());
        post.setBody(DataUtils.generateRandomString());

        Response response = ApiUtils.sendPostRequest(ApiUtils.getPostRequest(baseUrl), "/posts", post);

        assertEquals(201, response.statusCode());

        Post createdPost = response.as(Post.class);
        assertEquals(post.getUserId(), createdPost.getUserId());
        assertEquals(post.getTitle(), createdPost.getTitle());
        assertEquals(post.getBody(), createdPost.getBody());
        assertEquals(101, createdPost.getId());
    }

    @Test
    public void testGetUsers() {
        String baseUrl = ConfigReader.getBaseUrl();
        Response response = ApiUtils.sendGetRequest(baseUrl, "/users");
        assertEquals(200, response.getStatusCode());

        JsonPath jsonPath = response.jsonPath();
        User user = jsonPath.getObject("find { it.id == 5 }", User.class);
        assertNotNull(user);

        assertEquals("Chelsey Dietrich", user.name);
        assertEquals("Kamren", user.username);
        assertEquals("Lucio_Hettinger@annie.ca", user.email);
        assertEquals("Skiles Walks", user.address.street);
        assertEquals("Suite 351", user.address.suite);
        assertEquals("Roscoeview", user.address.city);
        assertEquals("33263", user.address.zipcode);
        assertEquals("-31.8129", user.address.geo.lat);
        assertEquals("62.5342", user.address.geo.lng);
        assertEquals("(254)954-1289", user.phone);
        assertEquals("demarco.info", user.website);
        assertEquals("Keebler LLC", user.company.name);
        assertEquals("User-centric fault-tolerant solution", user.company.catchPhrase);
        assertEquals("revolutionize end-to-end systems", user.company.bs);
    }
    
    
    @Test
    public void testGetUserById() {
        String baseUrl = ConfigReader.getBaseUrl();
        int userId = 5;
        Response response = ApiUtils.sendGetRequest(baseUrl, "/users/" + userId);
        assertEquals(200, response.getStatusCode());

        User user = response.as(User.class);
        assertEquals(5, user.id);
        assertEquals("Chelsey Dietrich", user.name);
        assertEquals("Kamren", user.username);
        assertEquals("Lucio_Hettinger@annie.ca", user.email);

        assertNotNull(user.address);
        assertEquals("Skiles Walks", user.address.street);
        assertEquals("Suite 351", user.address.suite);
        assertEquals("Roscoeview", user.address.city);
        assertEquals("33263", user.address.zipcode);

        assertNotNull(user.address.geo);
        assertEquals("-31.8129", user.address.geo.lat);
        assertEquals("62.5342", user.address.geo.lng);

        assertEquals("(254)954-1289", user.phone);
        assertEquals("demarco.info", user.website);

        assertNotNull(user.company);
        assertEquals("Keebler LLC", user.company.name);
        assertEquals("User-centric fault-tolerant solution", user.company.catchPhrase);
        assertEquals("revolutionize end-to-end systems", user.company.bs);
    }
}