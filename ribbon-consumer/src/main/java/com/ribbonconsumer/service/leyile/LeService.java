package com.ribbonconsumer.service.leyile;

import com.ribbonconsumer.mapper.leyile.LeMapper;
import com.ribbonconsumer.service.leyile.abstra.LeAbstraService;
import org.springframework.stereotype.Service;

@Service
public class LeService extends LeAbstraService {

    public LeService(LeMapper leMapper) {
        super(leMapper);
    }

}
