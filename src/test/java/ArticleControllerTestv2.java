import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.exam.rbac_jwt_mfa_library.domain.Article;
import com.exam.rbac_jwt_mfa_library.service.ArticleService;
import com.exam.rbac_jwt_mfa_library.web.ArticleController;

@ExtendWith(MockitoExtension.class)
class ArticleControllerTestv2 {

    @Mock
    private ArticleService articleService;  // mock, not real object

    @InjectMocks
    private ArticleController articleController;  // real controller

    @Test
    void testGetAllArticles() {
        Article article = new Article(1L, "Title 1", "Content 1", 1L, Instant.now(), Instant.now(), true);

        when(articleService.listPublic()).thenReturn(List.of(article)); // works fine

        var response = articleController.getAllPublicArticles();
        assertEquals(1, ((List<?>) response.getBody()).size());
    }
    
}