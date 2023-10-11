package uz.pdp.springexam.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.springexam.dto.customer.CustomerCreateDto;
import uz.pdp.springexam.dto.customer.CustomerResponseDto;
import uz.pdp.springexam.entity.Customer;
import uz.pdp.springexam.repository.CustomerRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImp implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public HttpEntity<CustomerResponseDto> getCustomerById(UUID id) {
        Customer customer = customerRepository.findById(id).get();
        return ResponseEntity.ok(map(customer));
    }

    @Override
    public HttpEntity<List<CustomerResponseDto>> getAll() {
        List<Customer> all = customerRepository.findAll();
        return ResponseEntity.ok(all.stream().map(this::map).toList());
    }

    @Override
    public HttpEntity<?> deleteById(UUID id) {
        customerRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public HttpEntity<CustomerResponseDto> create(CustomerCreateDto createDto) {
        Customer customer = Customer.builder()
                .id(createDto.getId())
                .name(createDto.getName())
                .username(createDto.getUsername())
                .phoneNumber(createDto.getPhoneNumber())
                .password(createDto.getPassword())
                .email(createDto.getEmail())
                .birthDate(createDto.getBirthDate())
                .build();
        customerRepository.save(customer);

        return ResponseEntity.ok(map(customer));

    }

    @Override
    public HttpEntity<CustomerResponseDto> update(UUID id, CustomerCreateDto createDto) {
        Customer customer= customerRepository.findById(id).get();


        customer.setName(createDto.getName());
        customer.setEmail(createDto.getEmail());
        customer.setPassword(createDto.getPassword());
        customer.setUsername(createDto.getUsername());
        customer.setPhoneNumber(createDto.getPhoneNumber());


        customerRepository.save(customer);

        CustomerResponseDto res = CustomerResponseDto.builder()
                .id(customer.getId())
                .username(customer.getUsername())
                .phoneNumber(customer.getPhoneNumber())
                .email(customer.getEmail())
                .build();
        return ResponseEntity.ok(res);

    }

    private CustomerResponseDto map(Customer customer){
        return  CustomerResponseDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .username(customer.getUsername())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .birthDate(customer.getBirthDate())
                .build();
    }
}
