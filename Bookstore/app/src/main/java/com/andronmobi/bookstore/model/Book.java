package com.andronmobi.bookstore.model;

import java.util.List;

public interface Book {

    int getId();
    String getIsbn();
    String getTitle();
    String getCover();
    int getPrice();
    List<String> getSynopsis();
}
