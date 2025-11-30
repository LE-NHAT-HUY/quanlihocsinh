package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.Post;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDAO {

    private final String URL = "jdbc:sqlserver://LENOVO\\HUY123:1433;databaseName=StudentManagementDB;integratedSecurity=true;encrypt=true;trustServerCertificate=true";
    private final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    public PostDAO() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Post> getFeaturedPosts() {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT * FROM posts WHERE isActive = 1 AND isFeatured = 1 ORDER BY postOrder ASC";

        try (Connection conn = getConnection()) {
            if (conn == null) {
                System.out.println("DB connection null!");
                return posts; // trả về rỗng để tránh NPE
            }

            try (PreparedStatement ps = conn.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Post p = new Post();
                    p.setPostID(rs.getInt("postID"));
                    p.setTitle(rs.getString("title"));
                    p.setSummary(rs.getString("summary"));
                    p.setContent(rs.getString("content"));
                    p.setImageUrl(rs.getString("imageUrl"));
                    p.setPublishDate(rs.getTimestamp("publishDate"));
                    p.setActive(rs.getBoolean("isActive"));
                    p.setFeatured(rs.getBoolean("isFeatured"));
                    p.setCreatedBy(rs.getString("createdBy"));
                    p.setPostOrder(rs.getInt("postOrder"));
                    posts.add(p);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posts;
    }

}
