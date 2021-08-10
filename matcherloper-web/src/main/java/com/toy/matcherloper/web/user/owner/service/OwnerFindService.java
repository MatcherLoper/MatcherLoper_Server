package com.toy.matcherloper.web.user.owner.service;

import com.toy.matcherloper.core.user.model.Owner;
import com.toy.matcherloper.core.user.repository.OwnerRepository;
import com.toy.matcherloper.web.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OwnerFindService {

    private final OwnerRepository ownerRepository;

    public Owner findById(Long ownerId) {
        return ownerRepository.findById(ownerId)
                .orElseThrow(() -> new UserNotFoundException("사용자가 존재하지 않습니다. Owner id = " + ownerId));
    }
}
