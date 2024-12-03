package com.system.announcement.controllers;

import com.system.announcement.services.FavoriteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("favorite")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<Object> saveFavorite(@Valid @PathVariable UUID id){
        favoriteService.saveFavorite(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Boolean> isFavorite(@Valid @PathVariable UUID id){
        if(favoriteService.isFavorite(id)) return ResponseEntity.status(HttpStatus.OK).body(true);
        return ResponseEntity.status(HttpStatus.OK).body(false);
    }

    @GetMapping
    public ResponseEntity<Object> getAllFavorites(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(favoriteService.getMyAllFavorite(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFavorite(@Valid @PathVariable UUID id){
        favoriteService.removeFavorite(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
