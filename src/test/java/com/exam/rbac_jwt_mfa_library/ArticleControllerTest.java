// package com.exam.rbac_jwt_mfa_library;

// import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.context.TestConfiguration;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.context.annotation.Bean;
// import org.springframework.test.web.servlet.MockMvc;

// import java.time.Instant;
// import java.util.List;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
// import com.exam.rbac_jwt_mfa_library.domain.Article;
// import com.exam.rbac_jwt_mfa_library.service.ArticleService;
// //import com.exam.rbac_jwt_mfa_library.web.ArticleController;


// @SpringBootTest
// @AutoConfigureMockMvc
// //@WebMvcTest(ArticleController.class)
// public class ArticleControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @Autowired
//     // @SuppressWarnings("removal")
//     // @MockBean
//     private ArticleService articleService;

//     @TestConfiguration
//     static class TestConfig {
//         @Bean
//         public ArticleService articleService() {
//             return Mockito.mock(ArticleService.class);
//         }
//     }

//     @Test
//     void testGetAllArticles() throws Exception {
//         Instant fixedTime = Instant.parse("2025-08-25T06:10:30Z");
//         Article article = new Article(1L, "Title 1", "Content 1", 1L, fixedTime, fixedTime, true);

//         Mockito.when(articleService.listPublic()).thenReturn(List.of(article));

//         mockMvc.perform(get("/api/articles/public"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$[0].id").value(1L))
//                 .andExpect(jsonPath("$[0].title").value("Title 1"))
//                 .andExpect(jsonPath("$[0].content").value("Content 1"))
//                 .andExpect(jsonPath("$[0].authorId").value(1L))
//                 //.andExpect(jsonPath("$[0].createdAt").value(fixedTime))
//                 //.andExpect(jsonPath("$[0].updatedAt").value(fixedTime))
//                 .andExpect(jsonPath("$[0].public").value(true));

//         Mockito.verify(articleService).listPublic();
//     }

//     // @Test
//     // void testGetAllArticles() throws Exception {
//     //         Instant now = Instant.now();
//     //         Article article = new Article(1L, "Title 1", "Content 1", 1L, now, now, true);

//     //     Mockito.when(articleService.listPublic()).thenReturn(List.of(article));

//     //     mockMvc.perform(get("/api/articles/public"))
//     //             .andExpect(status().isOk())
//     //             .andExpect(jsonPath("$[0].id").value(1L))
//     //             .andExpect(jsonPath("$[0].title").value("Title 1"))
//     //             .andExpect(jsonPath("$[0].content").value("Content 1"))
//     //             .andExpect(jsonPath("$[0].authorId").value(1L))
//     //             //andExpect(jsonPath("$[0].createdAt").value(now))
//     //             //.andExpect(jsonPath("$[0].updatedAt").value(now))
//     //             .andExpect(jsonPath("$[0].public").value(true));

//     //     Mockito.verify(articleService).listPublic();

//     // }

// }

