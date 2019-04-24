package com.netcracker.edu.varabey.dao;

import com.netcracker.edu.varabey.entity.Category;
import com.netcracker.edu.varabey.entity.Offer;
import com.netcracker.edu.varabey.entity.Price;
import com.netcracker.edu.varabey.entity.Tag;

import java.util.Collection;
import java.util.List;

public interface OfferDAO {
    Offer create(Offer offer);
    Offer read(Long id);
    Offer update(Offer offer);
    void delete(Long id);

    List<Offer> readAll();
    List<Offer> findAllByCategory(Category category);
    List<Offer> findAllWithTags(Collection<Tag> tags);
    List<Offer> findAllWithPriceInRange(Double lowerBound, Double upperBound);
}