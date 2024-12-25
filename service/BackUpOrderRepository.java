package cn.com.tcsl.fast.kds.server.service;

import cn.com.tcsl.fast.kds.server.model.mongo.KdsBackUpOrder;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface BackUpOrderRepository extends MongoRepository<KdsBackUpOrder, String> {


}
