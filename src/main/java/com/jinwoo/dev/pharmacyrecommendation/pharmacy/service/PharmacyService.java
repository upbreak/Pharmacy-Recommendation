package com.jinwoo.dev.pharmacyrecommendation.pharmacy.service;

import com.jinwoo.dev.pharmacyrecommendation.pharmacy.entity.Pharmacy;
import com.jinwoo.dev.pharmacyrecommendation.pharmacy.repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class PharmacyService {

    private final PharmacyRepository pharmacyRepository;

    // self invocation test
    public void bar(List<Pharmacy> pharmacyList) {
        log.info("bar CurrentTransactionName: "+ TransactionSynchronizationManager.getCurrentTransactionName());
        foo(pharmacyList);
    }

    // self invocation test
    @Transactional
    public void foo(List<Pharmacy> pharmacyList) {
        log.info("foo CurrentTransactionName: "+ TransactionSynchronizationManager.getCurrentTransactionName());
        pharmacyList.forEach(pharmacy -> {
            pharmacyRepository.save(pharmacy);
            throw new RuntimeException("error");
        });
    }

    @Transactional
    public void updateAddress(Long id, String address){
        Pharmacy entity = pharmacyRepository.findById(id).orElse(null);

        if(Objects.isNull(entity)){
            log.error("[PharmacyService updateAddress] not found id: {}", id);
            return;
        }

        entity.changePharmacyAddress(address);
    }

    @Transactional(readOnly = true)
    public List<Pharmacy> findAll(){
        return pharmacyRepository.findAll();
    }
}
