package com.proyectofinal.persistence.repositories;

import com.proyectofinal.persistence.entities.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.xml.stream.events.Comment;
import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Integer> {

    List<Comments> findByPlaceId(int placeId);

    Comments findByUserId(int userId);
}
