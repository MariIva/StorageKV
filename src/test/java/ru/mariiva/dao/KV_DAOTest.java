package ru.mariiva.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.mariiva.entities.KV;
import ru.mariiva.entities.Value;

@DataJpaTest
class KV_DAOTest {

    @Autowired
    private KV_DAO kvDao;

    @Test
    void addKV(){
        KV kvS = generateKV("-1", "test", 44);
        kvDao.save(kvS);
        KV kvL = kvDao.getById("-1");
        assertEquals(kvS, kvL);
    }

    @Test
    void updateKV(){
        KV kvS = generateKV("-1", "test", 44);
        kvDao.save(kvS);
        KV kv = kvDao.getById("-1");
        KV kvOld = generateKV(kv.getKey(),kv.getValue().getData(),kv.getValue().getTtl());

        KV kvS2 = generateKV("-1", "TEST", 10);
        kvDao.save(kvS2);
        KV kvNew = generateKV(kv.getKey(),kv.getValue().getData(),kv.getValue().getTtl());;

        assertEquals(kvS, kvOld);
        assertEquals(kvS2, kvNew);
        assertNotEquals(kvOld, kvNew);
    }
    @Test
    void deleteKV(){
        KV kvS = generateKV("-1", "test", 44);
        kvDao.save(kvS);
        KV kvL = kvDao.getById("-1");
        assertEquals(kvS, kvL);
        kvDao.deleteById("-1");
        assertThrows(org.springframework.orm.jpa.JpaObjectRetrievalFailureException.class, ()->kvDao.getById("-1") );
    }
    private KV generateKV(String key, String data, int ttl){
        return KV.builder().
                key(key).
                value(Value.builder().
                        data(data).
                        ttl(ttl).
                        build()).
                build();
    }
}