package com.mohammad.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohammad.Entities.Category;

public interface CategoryRepo  extends JpaRepository<Category, Integer>{

}
