package com.bonav.caura.cars;

import com.bonav.caura.users.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    //Car findByBrandAndPrice (String Brand, int Price);
}
