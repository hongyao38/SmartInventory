// package com.smartinventory;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

// import java.util.Optional;

// import com.smartinventory.exceptions.inventory.FoodExistsException;
// import com.smartinventory.inventory.container.Container;
// import com.smartinventory.inventory.container.ContainerRepository;
// import com.smartinventory.inventory.container.ContainerService;
// import com.smartinventory.inventory.food.Food;
// import com.smartinventory.inventory.food.FoodRepository;
// import com.smartinventory.inventory.food.FoodService;

// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;



// @ExtendWith(MockitoExtension.class)
// public class ContainerServiceTest {
//     @Mock
//     private ContainerRepository containerRepo;

//     @InjectMocks
//     private ContainerService containerService;

//     @Test
//     void getContainer_containerFound_returnContainer() {
//         Container container = new Container((long)1, 7.0, 1, 2, 5.0, 50.0, null, null);

//         //mock optional container
//         Optional<Container> containerOptional = Optional.of(container);

//         //mock findByRowIndexAndColIndex
//         when(containerRepo.findByRowIndexAndColIndex(any(Integer.class), any(Integer.class))).thenReturn(containerOptional);

//         Container getContainer = containerService.getContainer(1,2);
//         assertEquals(container, getContainer);

//         //verify
//         verify(containerRepo).findByRowIndexAndColIndex(container.getRowIndex(), container.getColIndex());
//     }

//     @Test
//     void addContainer_newContainer_SavedContainer() {
//         Container container = new Container((long)1, 7.0, 1, 2, 5.0, 50.0, null, null);
//         Food food = new Food((long) 1, "milk", "dairy", 2.5, null, null, null);
//         container.setFood(food);

//         // when(container.getFood()).thenReturn(food);
//         when(containerRepo.findByRowIndexAndColIndex(container.getRowIndex(), container.getColIndex()));
//         when(containerRepo.save(any(Container.class))).thenReturn(container);

//         Container newContainer = containerService.addContainer(container);

//         assertEquals(container, newContainer);

//         verify(containerRepo).save(container);
//     }


// }
