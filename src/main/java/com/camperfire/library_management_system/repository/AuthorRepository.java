package com.camperfire.library_management_system.repository;

import com.camperfire.library_management_system.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {}