package maksym.kruhovykh.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maksym.kruhovykh.app.repository.OrderRepository;
import maksym.kruhovykh.app.service.mapper.OrderMapper;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Slf4j
@Service
public class OrderService {

    private OrderRepository orderRepository;
    private OrderMapper orderMapper;
}
