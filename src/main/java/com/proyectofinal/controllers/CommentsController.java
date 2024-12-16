package com.proyectofinal.controllers;

import com.proyectofinal.persistence.entities.Comments;
import com.proyectofinal.persistence.entities.Place;
import com.proyectofinal.persistence.entities.User;
import com.proyectofinal.persistence.repositories.CommentsRepository;
import com.proyectofinal.persistence.repositories.PlaceRepository;
import com.proyectofinal.persistence.repositories.UserRepository;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin
public class CommentsController {

    private final CommentsRepository commentsRepository;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentsController(CommentsRepository commentsRepository, PlaceRepository placeRepository, UserRepository userRepository) {
        this.commentsRepository = commentsRepository;
        this.placeRepository = placeRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/{placeId}/{userId}")
    public ResponseEntity<Comments> addComment(@PathVariable("placeId") int placeId,
                                               @PathVariable("userId") int userId,
                                               @RequestParam("message") String message) {  // Se mantiene el uso de @RequestParam
        // Busca el lugar y el usuario
        Place place = placeRepository.findById(placeId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (place == null || user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Si no encuentra el lugar o el usuario
        }

        // Crear y guardar el comentario
        Comments newComment = new Comments();
        newComment.setMensaje(message);
        newComment.setPlace(place);
        newComment.setUser(user);

        Comments savedComment = commentsRepository.save(newComment);

        // Retornar el comentario guardado
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }

    @GetMapping("/place/{placeId}")
    public ResponseEntity<Iterable<Comments>> getCommentsByPlaceId(@PathVariable("placeId") int placeId) {
        Iterable<Comments> comments = commentsRepository.findByPlaceId(placeId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
    
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable int commentId, Principal principal) {
        // Find the comment by ID
        Comments comment = commentsRepository.findById(commentId).orElse(null);

        if (comment == null) {
            return new ResponseEntity<>("Comment not found", HttpStatus.NOT_FOUND);
        }

        // Get the logged-in user (from Basic Auth)
        String loggedInUsername = principal.getName();

        // Check if the logged-in user is the owner of the comment
        if (!comment.getUser().getUsername().equals(loggedInUsername)) {
            return new ResponseEntity<>("You can only delete your own comments", HttpStatus.FORBIDDEN);
        }

        // Delete the comment
        commentsRepository.delete(comment);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }

}