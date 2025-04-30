package com.atozdev.uptimemoniter.repository;

import com.atozdev.uptimemoniter.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}