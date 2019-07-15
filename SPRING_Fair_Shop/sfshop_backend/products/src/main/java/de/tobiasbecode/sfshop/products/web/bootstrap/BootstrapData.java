package de.tobiasbecode.sfshop.products.web.bootstrap;

/*
* Bootstrap Data for testing/ first startup
* */


import de.tobiasbecode.sfshop.products.data.domain.Products;
import de.tobiasbecode.sfshop.products.data.repository.ProductsRepository;
import de.tobiasbecode.sfshop.products.data.service.UuidService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BootstrapData implements CommandLineRunner {

    final ProductsRepository productsRepository;
    final UuidService uuidService;

    public BootstrapData(ProductsRepository productsRepository, UuidService uuidService) {
        this.productsRepository = productsRepository;
        this.uuidService = uuidService;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {


        //Coffee
        Products coffeeArabica = new Products();

        coffeeArabica.setDescription("Aromatischer Arabica Kaffee aus der Bergregion Perus. " +
                "Der Kaffeeabau ist garantiert nachhaltig und bietet den Kleinbauern eine faire Entlohnung."+ "\n" +
                "Menge 250g / mild"

                );

        coffeeArabica.setCategory("coffee");
        coffeeArabica.setInventory(2000);
        coffeeArabica.setName("Pozuzo Delight");
        coffeeArabica.setOffer(false);
        coffeeArabica.setPrice(18.99);
        coffeeArabica.setShippingCosts(2.95);
        coffeeArabica.setUuid(uuidService.getUuidBytes());
        coffeeArabica.setImagePath("https://spring5webshop.s3.eu-central-1.amazonaws.com/spring5webshop/products/coffee-pozuzo.jpg");


        productsRepository.save(coffeeArabica);





        Products coffeePinchicha = new Products();
        coffeePinchicha.setDescription("Kaffe aus den peruanischen Anden. Vulkanische Erde sorgt für eine reichhaltige aromatische Vielfalt." +
                "Der Familienbetrieb Bernando lebt dort seit Generationen und achtet auf einen intakten Naturkreislauf." + "\n" +
                "Menge 250 g / mild  ");

        coffeePinchicha.setCategory("coffee");
        coffeePinchicha.setInventory(1000);
        coffeePinchicha.setName("Bernando Esprit");
        coffeePinchicha.setOffer(true);
        coffeePinchicha.setPrice(15.99);
        coffeePinchicha.setShippingCosts(2.95);
        coffeePinchicha.setUuid(uuidService.getUuidBytes());
        coffeePinchicha.setImagePath("https://spring5webshop.s3.eu-central-1.amazonaws.com/spring5webshop/products/coffee-bernando.jpg");

        productsRepository.save(coffeePinchicha);

        // Honey
        Products honeyBluetenhonig = new Products();

        honeyBluetenhonig.setDescription("Dieser Honig aus dem tropischen Regenwald in Argentinien überzeugt durch seinen cremigen Geschmack mit einem leichten Blütenaroma und einer " +
                "Note von Holz, Walnuß und Haselnuß. Außerdem hat der Honig bemerkenswerte antioxidantische Eigenschaften. Die Honigherstellung geschieht in Kooperation mit indianischen Imkern." + "\n" +
                "500g");

        honeyBluetenhonig.setCategory("honey");
        honeyBluetenhonig.setInventory(4000);
        honeyBluetenhonig.setName("Misiones Gold");
        honeyBluetenhonig.setOffer(false);
        honeyBluetenhonig.setPrice(6.99);
        honeyBluetenhonig.setShippingCosts(2.95);
        honeyBluetenhonig.setUuid(uuidService.getUuidBytes());
        honeyBluetenhonig.setImagePath("https://spring5webshop.s3.eu-central-1.amazonaws.com/spring5webshop/products/honey-misiones.jpg");


        productsRepository.save(honeyBluetenhonig);

        //chocolate
        Products chocolateVollmilch = new Products();

        chocolateVollmilch.setDescription("Mit einem Kakaoanteil von 37% und echter Kakaobutter bietet diese Schokolade aus Paraguay ein besonderes Geschmackserlebnis." +
                "Die Kleinbauern erhalten durch die Kakaoproduktion für dieses Produkt ein faires Grundeinkommen. Die Milch kommt aus der Alpenregion und sorgt mit dem schonenden Conchieren für einen cremigen Schmelz."+ "\n" +
                "80g");

        chocolateVollmilch.setCategory("chocolate");
        chocolateVollmilch.setInventory(10000);
        chocolateVollmilch.setName("Villarica Vollmilch");
        chocolateVollmilch.setOffer(false);
        chocolateVollmilch.setPrice(2.49);
        chocolateVollmilch.setShippingCosts(0.99);
        chocolateVollmilch.setUuid(uuidService.getUuidBytes());
        chocolateVollmilch.setImagePath("https://spring5webshop.s3.eu-central-1.amazonaws.com/spring5webshop/products/chocolate-villarica.jpg");

        productsRepository.save(chocolateVollmilch);

       //tea
        Products teaDarjeeling = new Products();

        teaDarjeeling.setDescription("Ein Schwarztee aus der Bergregion Himalaya. Der handgepflückte First Flush hat einen blumigen und vollen Körper und unterstützt Arbeiter mit einer fairen Entlohnung." + "\n" +
                "75g");
        teaDarjeeling.setCategory("tea");
        teaDarjeeling.setInventory(5000);
        teaDarjeeling.setName("Darjeeling Ladakh Flush");
        teaDarjeeling.setOffer(true);
        teaDarjeeling.setPrice(5.99);
        teaDarjeeling.setShippingCosts(0.99);
        teaDarjeeling.setUuid(uuidService.getUuidBytes());
        teaDarjeeling.setImagePath("https://spring5webshop.s3.eu-central-1.amazonaws.com/spring5webshop/products/tea-darjeeling.jpg");


        productsRepository.save(teaDarjeeling);
    }
}
