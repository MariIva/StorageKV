package ru.mariiva.rest.contriller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.mariiva.entities.KV;
import ru.mariiva.entities.Value;
import ru.mariiva.service.KVService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
@WebMvcTest(KVController.class)
class KVControllerTest {
    @MockBean
    private KVService service;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new KVController(service)).build();
    }
    @Test
    void set3Param() throws Exception {
        KV kv = getListKV().get(0);
        Mockito.when(this.service.insert(kv)).thenReturn(true);
        MvcResult test1 = mockMvc.perform(
                post("/kv")
                        .param("key", kv.getKey())
                        .param("value", kv.getValue().getData())
                        .param("ttl", kv.getValue().getTtl()+"")
        ).andReturn();
        String contentAsString = test1.getResponse().getContentAsString();
        assertTrue(Boolean.parseBoolean(contentAsString));
    }
    @Test
    void getAll() throws Exception {
        Mockito.when(this.service.getAll()).thenReturn(getListKV());
        mockMvc.perform(get("/kv"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5));
    }

    @Test
    void getById() throws Exception {
        KV kv = getListKV().get(0);
        Mockito.when(this.service.getByKey(kv.getKey())).thenReturn(getListKV().get(0).getValue().getData());
        MvcResult mvcResult = mockMvc.perform(get("/kv").param("key", kv.getKey())).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        assertEquals(kv.getValue().getData(), contentAsString);
    }

    @Test
    void remove() throws Exception {
        KV kv = getListKV().get(0);
        Mockito.when(this.service.deleteByKey(kv.getKey())).thenReturn(getListKV().get(0).getValue().getData());
        MvcResult mvcResult = mockMvc.perform(delete("/kv").param("key", kv.getKey())).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        assertEquals(kv.getValue().getData(), contentAsString);
    }


    private List<KV> getListKV(){
        List<KV> list = new ArrayList<>();
        for (int i=0; i<5;i++){
            list.add(generateKV(""+i, "test"+i, 10*(i+1)));
        }
        return list;
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