package com.jinanlongen.sparrow.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.jinanlongen.sparrow.domain.Image;

public interface ImageRep extends JpaRepository<Image, Long> {
  List<Image> findByAlbumId(long id);
}
