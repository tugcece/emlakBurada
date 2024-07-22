package com.patika.emlakburadapackageservice.config;

import com.patika.emlakburadapackageservice.model.Package;
import com.patika.emlakburadapackageservice.model.enums.PackageTypes;
import com.patika.emlakburadapackageservice.repository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DefaultPackageInitializer implements CommandLineRunner {

    @Autowired
    private PackageRepository packageRepository;

    @Override
    public void run(String... args) throws Exception {
        if (packageRepository.count() == 0) {
            Package defaultPackage = new Package();
            defaultPackage.setPackageName(PackageTypes.BASIC);
            defaultPackage.setValidityPeriod(30); // 1 month validity
            defaultPackage.setListingRights(10);
            defaultPackage.setPrice(300.00);
            packageRepository.save(defaultPackage);
        }
    }
}
