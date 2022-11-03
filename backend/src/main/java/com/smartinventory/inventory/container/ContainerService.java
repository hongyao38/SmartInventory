package com.smartinventory.inventory.container;

import java.util.Optional;

import com.smartinventory.exceptions.inventory.ContainerNotFoundException;

import org.hibernate.validator.cfg.context.ContainerElementConstraintMappingContext;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContainerService {

    private final ContainerRepository containerRepo;

    public Container addContainer(ContainerDTO containerRequest) {
        Container newContainer = new Container(containerRequest.getCapacity(), 
                                                containerRequest.getI(), 
                                                containerRequest.getJ());

        return containerRepo.save(newContainer);
    }
    

    public Container getContainer(Integer i, Integer j) {
        Optional<Container> container = containerRepo.findByIAndJ(i, j);

        if (container.isEmpty()) {
            throw new ContainerNotFoundException();
        }

        return container.get();
    }
}
