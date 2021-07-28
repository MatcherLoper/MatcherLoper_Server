package com.toy.matcherloper.web.user.service;

import com.toy.matcherloper.core.user.model.Owner;
import org.springframework.stereotype.Service;

@Service
public class OwnerFindService {

    public Owner findById(Long ownerId) {
        return new Owner();
    }
}
