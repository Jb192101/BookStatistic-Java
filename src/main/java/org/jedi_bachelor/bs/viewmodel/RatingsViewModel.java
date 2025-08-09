package org.jedi_bachelor.bs.viewmodel;

import org.jedi_bachelor.bs.model.Book;
import org.jedi_bachelor.bs.model.Rating;
import org.jedi_bachelor.bs.view.RatingsWindow;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RatingsViewModel extends LocalViewModel {
    public RatingsViewModel(MainViewModel mainViewModel) {
        super(mainViewModel);

        this.window = new RatingsWindow(this);
    }

    public Map<Rating, Integer> getData() {
        Map<Rating, Integer> countOfRatings = new HashMap<>();

        for(Rating r : Rating.values()) {
            countOfRatings.put(r, 0);
        }

        Map<Long, Book> books = mainViewModel.getBooks();
        for(Long id : books.keySet()) {
            countOfRatings.put(books.get(id).getRating(), countOfRatings.get(books.get(id).getRating()) + 1);
        }

        return countOfRatings;
    }
}
