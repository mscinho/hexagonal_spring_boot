package com.soares.hexagonal.adapters.in.consumer;

import com.soares.hexagonal.adapters.in.consumer.mapper.CustomerMessageMapper;
import com.soares.hexagonal.adapters.in.consumer.message.CustomerMessage;
import com.soares.hexagonal.application.ports.in.UpdateCustomerInputPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ReceiveValidatedCpfConsumer {

    private static final Logger log = LoggerFactory.getLogger(ReceiveValidatedCpfConsumer.class);

    @Autowired
    private UpdateCustomerInputPort updateCustomerInputPort;

    @Autowired
    private CustomerMessageMapper customerMessageMapper;

    @KafkaListener(topics = "tp-cpf-validated", groupId = "soares")
    public void receive(CustomerMessage customerMessage) {
        try {
            var customer = customerMessageMapper.toCustomer(customerMessage);
            updateCustomerInputPort.update(customer, customerMessage.getZipCode());
            log.info("✅ CPF validado recebido e atualizado: {}", customer.getCpf());
        } catch (Exception e) {
            log.error("❌ Erro ao processar mensagem CPF validado: {}", e.getMessage(), e);
        }
    }

}
