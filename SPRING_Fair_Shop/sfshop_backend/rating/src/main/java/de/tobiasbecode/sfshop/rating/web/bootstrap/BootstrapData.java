package de.tobiasbecode.sfshop.rating.web.bootstrap;

import de.tobiasbecode.sfshop.rating.data.domain.Rating;
import de.tobiasbecode.sfshop.rating.data.repository.RatingRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 * Bootstrap Data for Testing / First Startup
 */


@Component
public class BootstrapData implements CommandLineRunner {

    final RatingRepository repository;


    public BootstrapData(RatingRepository repository) {
        this.repository = repository;
    }


    @Override
    @Transactional
    public void run(String... args) throws Exception {

        Rating ratingPozuzo = new Rating();

        ratingPozuzo.setRatingName("Thomas B.");
        ratingPozuzo.setRatingText("Super Produkt. Gerne wieder!");
        ratingPozuzo.setRatingLevel(4);
        ratingPozuzo.setIdproduct("T7fRh65KRryAlE34XJd0yA==");

        repository.save(ratingPozuzo);

    }


}
