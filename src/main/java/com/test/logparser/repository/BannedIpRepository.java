package com.test.logparser.repository;

import com.test.logparser.entity.BannedIp;
import org.springframework.data.repository.CrudRepository;

public interface BannedIpRepository extends CrudRepository<BannedIp, Long> {
}
