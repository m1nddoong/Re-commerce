package com.example.market.domain.shop.repository;

import com.example.market.domain.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom{
}
