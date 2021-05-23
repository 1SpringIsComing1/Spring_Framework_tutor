package maksym.kruhovykh.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maksym.kruhovykh.app.Utils;
import maksym.kruhovykh.app.dto.OrderDto;
import maksym.kruhovykh.app.repository.OrderRepository;
import maksym.kruhovykh.app.repository.entity.Order;
import maksym.kruhovykh.app.service.mapper.AddressMapper;
import maksym.kruhovykh.app.service.mapper.ClientMapper;
import maksym.kruhovykh.app.service.mapper.DriverMapper;
import maksym.kruhovykh.app.service.mapper.OrderMapper;
import maksym.kruhovykh.app.utils.Status;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.function.Function;


@RequiredArgsConstructor
@Slf4j
@Service
public class OrderService {

    private static final String ORDER_IS_EMPTY = "order is empty";
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final AddressMapper addressMapper;
    private final ClientMapper clientMapper;
    private final DriverMapper driverMapper;

    public OrderDto findOrderById(Integer id) {
        return orderRepository.findById(id)
                .map(orderMapper::orderToOrderDto)
                .orElseThrow(() -> new EntityNotFoundException("Order  [" + id + "] doesn't exist"));
    }

    public OrderDto createOrder(OrderDto orderDto) {
        Utils.isNull(orderDto, ORDER_IS_EMPTY);
        Order order = orderMapper.orderDtoToOrder(orderDto);
        checkPossibilityOfCreatingOrder(order);

        return orderMapper.orderToOrderDto(orderRepository.save(order));

    }

    public OrderDto update(OrderDto orderDto) {
        Utils.isNull(orderDto, ORDER_IS_EMPTY);
        return orderRepository
                .findById(orderDto.getId())
                .map(updateOrder(orderDto))
                .map(orderMapper::orderToOrderDto)
                .orElseThrow(() -> new EntityNotFoundException("Order with Id [" + orderDto.getId() + "] doesn't exist"));

    }

    public void delete(OrderDto orderDto) {
        Utils.isNull(orderDto, ORDER_IS_EMPTY);
        orderRepository
                .findById(orderDto.getId())
                .map(deleteOrder(orderDto))
                .map(orderMapper::orderDtoToOrder)
                .orElseThrow(() -> new EntityNotFoundException("Order with Id [" + orderDto.getId() + "] doesn't exist"));

    }

    private Function<Order, OrderDto> deleteOrder(OrderDto orderDto) {
        return orderDto1 -> {
            orderRepository.delete(orderDto1);
            return orderDto;
        };
    }

    private Function<Order, Order> updateOrder(OrderDto orderDto) {
        return order -> {
            order.setTitle(orderDto.getTitle());
            order.setDescription(orderDto.getDescription());
            order.setArrivals(addressMapper.addressDtoToAddress(orderDto.getArrivals()));
            order.setDepartures(addressMapper.addressDtoToAddress(orderDto.getDepartures()));
            order.setClient(clientMapper.clientDtoToClient(orderDto.getClientDto()));
            order.setDriver(driverMapper.driverDtoToDriver(orderDto.getDriverDto()));
            order.setDateCreation(orderDto.getDateCreation());
            order.setDistance(orderDto.getDistance());
            order.setStatus(orderDto.getStatus());
            order.setPrice(orderDto.getPrice());

            return orderRepository.save(order);

        };
    }

    private void checkPossibilityOfCreatingOrder(Order order) {
        if (orderRepository.existsByStatusAndClient(Status.PENDING, order.getClient())
                && orderRepository.existsByStatusAndClient(Status.APPROVED, order.getClient())) {
            log.warn("Client " + order.getClient() +
                    " can't create Order.He has not closed order");
            throw new RuntimeException("Client " + order.getClient() +
                    " can't create Order.He has not closed order");
        }

    }
}
