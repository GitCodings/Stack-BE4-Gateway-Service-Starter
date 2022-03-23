package com.github.klefstad_teaching.cs122b.gateway;

import com.github.klefstad_teaching.cs122b.core.result.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GatewayServiceTest
{
    private final MockMvc    mockMvc;

    @Autowired
    public GatewayServiceTest(MockMvc mockMvc)
    {
        this.mockMvc = mockMvc;
    }

    private ResultMatcher[] isResult(Result result)
    {
        return new ResultMatcher[]{
            status().is(result.status().value()),
            jsonPath("result.code").value(result.code()),
            jsonPath("result.message").value(result.message())
        };
    }

    @Test
    public void applicationLoads()
    {
    }
}
