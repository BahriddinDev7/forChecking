package uz.pdp.springexam.service;

import org.springframework.http.HttpEntity;
import uz.pdp.springexam.dto.customer.CustomerCreateDto;
import uz.pdp.springexam.dto.customer.CustomerResponseDto;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    HttpEntity<CustomerResponseDto> getCustomerById(UUID id);

    HttpEntity<List<CustomerResponseDto>> getAll();

    HttpEntity<?> deleteById(UUID id);

    HttpEntity<CustomerResponseDto> create(CustomerCreateDto createDto);

    HttpEntity<CustomerResponseDto> update(UUID id, CustomerCreateDto createDto);

}
