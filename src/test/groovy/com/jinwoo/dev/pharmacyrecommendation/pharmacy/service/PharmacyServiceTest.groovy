package com.jinwoo.dev.pharmacyrecommendation.pharmacy.service

import com.jinwoo.dev.pharmacyrecommendation.AbstractIntegrationContainerBaseTest
import com.jinwoo.dev.pharmacyrecommendation.pharmacy.entity.Pharmacy
import com.jinwoo.dev.pharmacyrecommendation.pharmacy.repository.PharmacyRepository
import org.springframework.beans.factory.annotation.Autowired

class PharmacyServiceTest extends AbstractIntegrationContainerBaseTest{

    @Autowired
    private PharmacyService pharmacyService

    @Autowired
    private PharmacyRepository pharmacyRepository

    def setup(){
        pharmacyRepository.deleteAll()
    }

    def "pharmacyRepository update - dirty checking success" (){
        given:
        String address = "서울 특별시 성북구 종암동"
        String modifyAddress = "서울 광진구 구의동"
        String name = "은혜 약국"
        double latitude = 36.11
        double longitude = 128.11

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(address)
                .pharmacyName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build()

        when:
        def entity = pharmacyRepository.save(pharmacy)
        pharmacyService.updateAddress(entity.id, modifyAddress)

        def result = pharmacyRepository.findAll()

        then:
        result.get(0).pharmacyAddress == modifyAddress

    }

    def "pharmacyRepository updateWithoutTransaction - dirty checking fail" (){
        given:
        String address = "서울 특별시 성북구 종암동"
        String modifyAddress = "서울 광진구 구의동"
        String name = "은혜 약국"
        double latitude = 36.11
        double longitude = 128.11

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(address)
                .pharmacyName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build()

        when:
        def entity = pharmacyRepository.save(pharmacy)
        pharmacyService.updateAddressWithoutTransaction(entity.id, modifyAddress)

        def result = pharmacyRepository.findAll()

        then:
        result.get(0).pharmacyAddress == address

    }
}
