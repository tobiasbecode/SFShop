package de.tobiasbecode.sfshop.order.web.bootstrap;

import de.tobiasbecode.sfshop.order.data.domain.Address;
import de.tobiasbecode.sfshop.order.data.repository.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Bootstrap Data for Testing / First Startup
 */


@Component
public class BootstrapData implements CommandLineRunner {

    final OrderRepository orderRepository;


    public BootstrapData(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        Address bootstrapAddressAdmin = new Address();

        bootstrapAddressAdmin.setAdressName("Thorsten B.");
        bootstrapAddressAdmin.setAdressCity("Hamburg");
        bootstrapAddressAdmin.setAdressCountry("Deutschland");
        bootstrapAddressAdmin.setAdressPLZ("22097");
        bootstrapAddressAdmin.setAdressStreet("Osterstraße 54b");
        bootstrapAddressAdmin.setAdressMail("thorsten.b@mail.de");
        bootstrapAddressAdmin.setUsername("admin");


        orderRepository.save(bootstrapAddressAdmin);

        Address bootstrapAddressUser = new Address();

        bootstrapAddressUser.setAdressName("Monika A.");
        bootstrapAddressUser.setAdressCity("München");
        bootstrapAddressUser.setAdressCountry("Deutschland");
        bootstrapAddressUser.setAdressPLZ("80634");
        bootstrapAddressUser.setAdressStreet("Bothmerstraße 22a");
        bootstrapAddressUser.setAdressMail("monika.a@gmail.com");
        bootstrapAddressUser.setUsername("user");

        orderRepository.save(bootstrapAddressUser);
    }
}
